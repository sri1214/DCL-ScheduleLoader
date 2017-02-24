package net.dallascricket.scheduleLoader.dao.manager;

import net.dallascricket.scheduleLoader.util.ServiceProperties;

import org.apache.log4j.Logger;

public class LeatherBallDBDAOManager extends DAOManager {
	
	private static String server, db, username, password;
	private final static Logger logger = Logger.getLogger(LeatherBallDBDAOManager.class);
	static{
		try {
			server = ServiceProperties.getServiceProperty("db.leatherball.address");
			 db = ServiceProperties.getServiceProperty("db.leatherball.dbname");
			 username = ServiceProperties.getServiceProperty("db.leatherball.uid");
			 password = ServiceProperties.getServiceProperty("db.leatherball.pwd");
			
		} catch (Exception e) {
			logger.error("Error reading leatherball db properties");
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
