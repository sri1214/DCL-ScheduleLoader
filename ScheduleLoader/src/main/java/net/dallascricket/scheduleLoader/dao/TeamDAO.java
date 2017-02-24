package net.dallascricket.scheduleLoader.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.log4j.Logger;

import net.dallascricket.scheduleLoader.db.domain.Team;

public class TeamDAO extends GenericDAO<Team> {
	private static final String TABLENAME = "team";
	private final static Logger logger = Logger.getLogger(TeamDAO.class);

	public TeamDAO(Connection con) {
		super(con, TABLENAME);
	}
	
	
	public HashMap<String, Integer> readTeamTable() throws SQLException{
		ResultSet result = null;
		HashMap<String, Integer> teamMap = new HashMap<String, Integer>();
		logger.debug("Getting team Table...");
		Statement state = con.createStatement();
		String sql = "select * from dbo.team";
		result = state.executeQuery(sql);
		teamMap = convertResultSetToList(result);
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

}
