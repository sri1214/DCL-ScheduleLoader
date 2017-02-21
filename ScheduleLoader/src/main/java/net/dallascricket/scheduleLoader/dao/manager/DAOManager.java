package net.dallascricket.scheduleLoader.dao.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.dallascricket.scheduleLoader.dao.GenericDAO;
import net.dallascricket.scheduleLoader.dao.GroundDAO;
import net.dallascricket.scheduleLoader.dao.MatchDAO;
import net.dallascricket.scheduleLoader.dao.MatchStatDAO;
import net.dallascricket.scheduleLoader.dao.TeamDAO;

public class DAOManager {

	private String url, username, password;
	private Connection con;
	public enum Table {
		MATCH, GROUND, TEAM, MATCHSTAT;
	};



	public void open() throws SQLException {
		try {
			if (this.con == null || this.con.isClosed())
				this.con = getConnection();
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void close() throws SQLException {
		try {
			if (this.con != null && !this.con.isClosed())
				this.con.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	public GenericDAO<?> getDAO(Table t) throws SQLException {
		try {
			if (this.con == null || this.con.isClosed()) // Let's ensure our
															// connection is
															// open
				this.open();
		} catch (SQLException e) {
			throw e;
		}

		switch (t) {
		case MATCH:
			return new MatchDAO(this.con);
		case TEAM:
			return new TeamDAO(this.con);
		case GROUND:
			return new GroundDAO(this.con);
		case MATCHSTAT:
			return new MatchStatDAO(this.con);
		default:
			throw new SQLException("Trying to link to an unexistant table.");
		}

	}

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		StringBuilder sb = new StringBuilder();
		sb.append("Connecting to db...\n");
		sb.append("connection url: "+url+"\n");
		sb.append("username: "+username);
		System.out.println(sb);
		Connection connection;

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		if(username==null || password == null)
			connection = DriverManager.getConnection(url);
		else
			connection = DriverManager.getConnection(url, username, password);
		System.out.println("connected!!");
		return connection;
	}



	protected DAOManager(String url, String user, String pw){
			this.url = url;
			this.username = user;
			this.password = pw;

	}



}
