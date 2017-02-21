package net.dallascricket.scheduleLoader.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import net.dallascricket.scheduleLoader.db.domain.Ground;

public class GroundDAO extends GenericDAO<Ground> {
	
	private static final String TABLENAME = "ground";

	public GroundDAO(Connection con) {
		super(con, TABLENAME);
	}

	
	public HashMap<String, Integer> readGroundTable() throws SQLException {
		ResultSet result = null;
		HashMap<String, Integer> groundMap = new HashMap<String, Integer>();
		System.out.println("Connected to the database!!! Getting Ground Table...");
		Statement state = con.createStatement();
		String sql = "select * from dbo.ground";
		result = state.executeQuery(sql);
		groundMap = convertResultSetToList(result);
		return groundMap;
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
