package net.dallascricket.scheduleLoader.db.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import net.dallascricket.scheduleLoader.db.domain.Match;

public class DbConnector {
/*	String url = "jdbc:jtds:sqlserver://sql2k802.discountasp.net;instance=SQLEXPRESS;DatabaseName=SQL2008_739130_dallascrick";
	String userName = "user2";
	String password = "dcl123";*/
	private String driver = "net.sourceforge.jtds.jdbc.Driver";
	private String url = null;
	private String username = null;
	private String password = null;
	
	public DbConnector(String server, String db, String username, String password){
		this.username  = username;
		this.password = password;
		url = "jdbc:jtds:sqlserver://"+server+";instance=SQLEXPRESS;DatabaseName="+db;
		System.out.println("*****Connection properties******\nserver name:"+server+"\ndatabase:"+db+"\nurl:"+url+"\nuser:"+username+"\npasword:"+password+"\n**************************");
	}
	
	

	public int getLatestMatchId() throws SQLException {
		Connection conn = null;
		ResultSet result = null;
		int matchId = 0;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to the database!!! Getting latest matchid from Match Table...");
			Statement state = conn.createStatement();
			String sql = "select match_id from Match order by match_id DESC";
			result = state.executeQuery(sql);
			if (result.next()) {
				matchId = result.getInt(1);
			}
			System.out.println("Latest matchid - "+matchId);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
			result.close();
		}
		return matchId;
	}
	
	public Timestamp getLatestMatchDt(int... tournamentIds) throws SQLException {
		Connection conn = null;
		ResultSet result = null;
		Timestamp matchDt = null;
		StringBuilder sb = new StringBuilder();
		for(int tournamentId:tournamentIds){
			sb.append("tournament_id=");
			sb.append(tournamentId);
			sb.append(" OR ");
		}
		sb.length();
		sb.delete(sb.length()-4, sb.length()-1);
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to the database!!! Getting latest matchdate from Match Table...");
			Statement state = conn.createStatement();
			String sql = "select match_dt from Match where "+sb.toString().trim()+" order by match_dt DESC";
			System.out.println(sql);
			result = state.executeQuery(sql);
			if (result.next()) {
				matchDt = result.getTimestamp(1);
			}
			System.out.println("Latest match date - "+matchDt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
			result.close();
		}
		return matchDt;
	}

	public HashMap<String, Integer> readTeamTable() throws SQLException{
		Connection conn = null;
		ResultSet result = null;
		HashMap<String, Integer> teamMap = new HashMap<String, Integer>();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			System.out
					.println("Connected to the database!!! Getting team Table...");
			Statement state = conn.createStatement();
			String sql = "select * from dbo.team";
			result = state.executeQuery(sql);

			teamMap = convertResultSetToList(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
			result.close();
		}
		return teamMap;
	}

	public HashMap<String, Integer> convertResultSetToList(ResultSet rs)
			throws SQLException {
		HashMap<String, Integer> row = new HashMap<String, Integer>();
		while (rs.next()) {		
			row.put(rs.getString(2), rs.getInt(1));
		}
		return row;
	}

	public HashMap<String, Integer> readGroundTable() throws SQLException {
		Connection conn = null;
		ResultSet result = null;
		HashMap<String, Integer> groundMap = new HashMap<String, Integer>();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			System.out
					.println("Connected to the database!!! Getting Ground Table...");
			Statement state = conn.createStatement();
			String sql = "select * from dbo.ground";
			result = state.executeQuery(sql);

			groundMap = convertResultSetToList(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
			result.close();
		}
		return groundMap;
	}
	
	public void writeIntoMatchTable(List<Match> matchList) throws SQLException {

		Connection conn = null;
/*		String url = "jdbc:jtds:sqlserver://sql2k802.discountasp.net;instance=SQLEXPRESS;DatabaseName=SQL2008_739130_dallascrick";
		String driver = "net.sourceforge.jtds.jdbc.Driver";
		String userName = "user2";
		String password = "dcl123";*/
        //String url = "jdbc:jtds:sqlserver://localhost;integratedSecurity=true;instance=SQLEXPRESS;DatabaseName=TestDB1";
        
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			//conn = DriverManager.getConnection(url);
			System.out
					.println("Connected to the database!!! writing into Match Table...");
			int totalRows = 0;
			for(Match match:matchList){
				PreparedStatement statement=conn.prepareStatement("INSERT INTO dbo.match (match_id, tournament_id, team1_id, team2_id, umpiring_team_id, type_cd, match_dt, ground_id, start_time, end_time, umpiring_team2_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				statement.setInt(1, match.getMatch_id());
				statement.setInt(2, match.getTournamentId());
				statement.setInt(3, match.getTeam1Id());
				statement.setInt(4, match.getTeam2Id());
				statement.setInt(5, match.getUmpiringTeamId());
				statement.setInt(6, match.getTypeCd());
				statement.setTimestamp(7, match.getMatchDt());
				statement.setInt(8, match.getGroundId());
				statement.setString(9, match.getStartTime());
				statement.setString(10, match.getEndTime());
				statement.setInt(11, match.getUmpiringTeam2Id());
				int val = statement.executeUpdate();
				totalRows +=val;

			}
			System.out.println("Total matches loaded into match table: "+totalRows);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	
	
	
	public void writeIntoMatchStatsTable(List<Match> matchList) throws SQLException {

		Connection conn = null;
/*		String url = "jdbc:jtds:sqlserver://sql2k802.discountasp.net;instance=SQLEXPRESS;DatabaseName=SQL2008_739130_dallascrick";
		String driver = "net.sourceforge.jtds.jdbc.Driver";
		String userName = "user2";
		String password = "dcl123";*/
		//String url = "jdbc:jtds:sqlserver://localhost;integratedSecurity=true;instance=SQLEXPRESS;DatabaseName=TestDB1";
        
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			//conn = DriverManager.getConnection(url);
			System.out
					.println("Connected to the database!!! writing into match_stats Table...");
			int totalRows = 0;
			for(Match match:matchList){
				PreparedStatement statement=conn.prepareStatement("INSERT INTO dbo.match_stats (match_id, team_id, inning_sw, runs_scored, total_runs, wickets, overs, extras, nos, wides, byes, over_throw) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
				statement.setInt(1, match.getMatch_id());
				statement.setInt(2, match.getTeam1Id());
				statement.setInt(3, 1);
				statement.setInt(4, 0);
				statement.setInt(5, 0);
				statement.setInt(6, 0);
				statement.setInt(7, 0);
				statement.setInt(8, 0);
				statement.setInt(9, 0);
				statement.setInt(10, 0);
				statement.setInt(11, 0);
				statement.setInt(12, 0);
				int val = statement.executeUpdate();
				totalRows +=val;
				
				statement=conn.prepareStatement("INSERT INTO dbo.match_stats (match_id, team_id, inning_sw, runs_scored, total_runs, wickets, overs, extras, nos, wides, byes, over_throw) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
				statement.setInt(1, match.getMatch_id());
				statement.setInt(2, match.getTeam2Id());
				statement.setInt(3, 2);
				statement.setInt(4, 0);
				statement.setInt(5, 0);
				statement.setInt(6, 0);
				statement.setInt(7, 0);
				statement.setInt(8, 0);
				statement.setInt(9, 0);
				statement.setInt(10, 0);
				statement.setInt(11, 0);
				statement.setInt(12, 0);
				val = statement.executeUpdate();
				totalRows +=val;

			}
			System.out.println("Total rows loaded into match_stats table: "+totalRows);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

}
