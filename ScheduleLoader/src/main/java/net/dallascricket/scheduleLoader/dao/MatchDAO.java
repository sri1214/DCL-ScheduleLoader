package net.dallascricket.scheduleLoader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import net.dallascricket.scheduleLoader.db.domain.Match;

public class MatchDAO extends GenericDAO<Match> {
	private static final String TABLENAME = "Match";

	public MatchDAO(Connection con) {
		super(con, TABLENAME);
	}

	public int getLatestMatchId() throws SQLException {
		int matchId = 0;
		System.out.println("Getting latest matchid from Match Table...");
		Statement state = con.createStatement();
		String sql = "select match_id from Match order by match_id DESC";
		ResultSet result = state.executeQuery(sql);
		if (result.next()) {
			matchId = result.getInt(1);
		}
		System.out.println("Latest matchid - " + matchId);
		return matchId;
	}
	
	public Timestamp getLatestMatchDt(double... tournamentIds) throws SQLException {
		Timestamp matchDt = null;
		StringBuilder sb = new StringBuilder();
		for (double tournamentId : tournamentIds) {
			sb.append("tournament_id=");
			sb.append(tournamentId);
			sb.append(" OR ");
		}
		sb.length();
		sb.delete(sb.length() - 4, sb.length() - 1);
		System.out.println("Connected to the database!!! Getting latest matchdate from Match Table...");
		Statement state = con.createStatement();
		String sql = "select match_dt from Match where " + sb.toString().trim()
				+ " order by match_dt DESC";
		System.out.println(sql);
		ResultSet result = state.executeQuery(sql);
		if (result.next()) {
			matchDt = result.getTimestamp(1);
		}
		System.out.println("Latest match date - " + matchDt);

		return matchDt;
	}

	public void loadMatches(List<Match> matchList) throws SQLException {
		System.out.println("Connected to the database!!! writing into Match Table...");
		PreparedStatement statement = con
				.prepareStatement("INSERT INTO dbo.match (match_id, tournament_id, team1_id, team2_id, umpiring_team_id, type_cd, match_dt, ground_id, start_time, end_time, umpiring_team2_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

		int totalRows = 0;
		for (Match match : matchList) {
			statement.setInt(1, match.getMatch_id());
			statement.setDouble(2, match.getTournamentId());
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
			totalRows += val;
		}
		System.out.println("Total matches loaded into match table: "+ totalRows);
	}

}
