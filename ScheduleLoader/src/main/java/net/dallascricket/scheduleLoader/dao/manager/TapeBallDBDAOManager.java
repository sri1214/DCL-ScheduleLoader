package net.dallascricket.scheduleLoader.dao.manager;

import java.sql.SQLException;

import net.dallascricket.scheduleLoader.dao.MatchDAO;
import net.dallascricket.scheduleLoader.util.ServiceProperties;

public class TapeBallDBDAOManager extends DAOManager{
	private static String server, db, username, password;
	static{
		try {
			server = ServiceProperties.getServiceProperty("db.tapeball.address");
			 db = ServiceProperties.getServiceProperty("db.tapeball.dbname");
			 username = ServiceProperties.getServiceProperty("db.tapeball.uid");
			 password = ServiceProperties.getServiceProperty("db.tapeball.pwd");
			
		} catch (Exception e) {
			System.err.println("Error reading tapeball db properties");
		}
	}
	
	private TapeBallDBDAOManager() throws Exception{
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
	
	public static void main(String[]args) throws SQLException, ClassNotFoundException
    {
       DAOManager daoManager =  TapeBallDBDAOManager.getInstance();
       MatchDAO match = (MatchDAO) daoManager.getDAO(Table.MATCH);
       System.out.print(match.getLatestMatchId());
    }
	
	private static class DAOManagerSingleton {

		public static final TapeBallDBDAOManager INSTANCE;
		static {
			TapeBallDBDAOManager dm;
			try {
				dm = new TapeBallDBDAOManager();
			} catch (Exception e) {
				dm = null;
			}
			INSTANCE = dm;
		}

	}

}
