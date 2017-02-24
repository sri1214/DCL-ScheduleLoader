package net.dallascricket.scheduleLoader.dao.manager;

import net.dallascricket.scheduleLoader.util.ServiceProperties;

public class LeatherBallDBDAOManager extends DAOManager {
	
	private static String server, db, username, password;
	static{
		try {
			server = ServiceProperties.getServiceProperty("db.leatherball.address");
			 db = ServiceProperties.getServiceProperty("db.leatherball.dbname");
			 username = ServiceProperties.getServiceProperty("db.leatherball.uid");
			 password = ServiceProperties.getServiceProperty("db.leatherball.pwd");
			
		} catch (Exception e) {
			System.err.println("Error reading leatherball db properties");
		}
	}
	
	private LeatherBallDBDAOManager() throws Exception{
		super(buildUrl(server, db), username, password);
	}
	
	private static String buildUrl(String server, String db) {
		String url = "jdbc:sqlserver://" + server
				+ ";instance=SQLEXPRESS;DatabaseName=" + db;
		return url;
	}
	
	public static DAOManager getInstance() {
		return DAOManagerSingleton.INSTANCE;
	}
	
	private static class DAOManagerSingleton {

		public static final LeatherBallDBDAOManager INSTANCE;
		static {
			LeatherBallDBDAOManager dm;
			try {
				dm = new LeatherBallDBDAOManager();
			} catch (Exception e) {
				dm = null;
			}
			INSTANCE = dm;
		}

	}


}
