package net.dallascricket.scheduleLoader.dao.manager;

import java.sql.SQLException;

import net.dallascricket.scheduleLoader.dao.MatchDAO;
import net.dallascricket.scheduleLoader.util.ServiceProperties;

public class TestDBDAOManager extends DAOManager{
	private static String server, db, username, password;
	static{
		try {
			server = ServiceProperties.getServiceProperty("db.local.address");
			 db = ServiceProperties.getServiceProperty("db.local.dbname");
			 username = ServiceProperties.getServiceProperty("db.local.uid");
			 password = ServiceProperties.getServiceProperty("db.local.pwd");
			
		} catch (Exception e) {
			System.err.println("Error reading local db properties");
		}
	}
	
	private TestDBDAOManager() throws Exception{
		super(buildUrl(server, db), username, password);
	}
	
	private static String buildUrl(String server, String db) {
		String url = "jdbc:sqlserver://" + server
				+ ";instance=MSSQLSERVER2;DatabaseName=" + db;
		return url;
	}
	
	public static DAOManager getInstance() {
		return DAOManagerSingleton.INSTANCE;
	}
	
	public static void main(String[]args) throws SQLException
    {
       DAOManager daoManager =  TestDBDAOManager.getInstance();
       MatchDAO match = (MatchDAO) daoManager.getDAO(Table.MATCH);
       System.out.print(match.getLatestMatchId());
    }
	
	private static class DAOManagerSingleton {

		public static final TestDBDAOManager INSTANCE;
		static {
			TestDBDAOManager dm;
			try {
				dm = new TestDBDAOManager();
			} catch (Exception e) {
				dm = null;
			}
			INSTANCE = dm;
		}

	}

}
