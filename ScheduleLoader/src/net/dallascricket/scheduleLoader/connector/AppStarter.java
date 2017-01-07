package net.dallascricket.scheduleLoader.connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import net.dallascricket.scheduleLoader.db.adapter.AbstractAdapter;
import net.dallascricket.scheduleLoader.db.adapter.LeatherBallAdapter;
import net.dallascricket.scheduleLoader.db.adapter.TapeBallAdapter;
import net.dallascricket.scheduleLoader.db.connector.DbConnector;
import net.dallascricket.scheduleLoader.db.domain.Match;
import net.dallascricket.scheduleLoader.domain.MatchData;

public class AppStarter {

	private static String fileLocation =null;
	private static String tapeBallUserName = null;
	private static String tapeBallPassword = null;
	private static String tapeBallServer = null;
	private static String tapeBallDb = null;
	private static String tapeBallSheetName = null;
	
	private static String leatherBallUserName = null;
	private static String leatherBallPassword = null;
	private static String leatherBallServer =null;
	private static String leatherBallDb = null;
	private static String leatherBallSheetName = null;
	
	
	
	public static void main(String[] args) throws Exception {
		String url = "jdbc:jtds:sqlserver://sql2k802.discountasp.net;instance=SQLEXPRESS;DatabaseName=SQL2008_739130_dallascrick";
		String fileLocation ="D:\\DCL\\Fall_Schedule4.xlsx";
		/*		String tapeBallUserName = "user2";
		String tapeBallPassword = "dcl123";*/
		String tapeBallUserName = "SQL2008_739130_dallascrick_user";
		String tapeBallPassword = "dcl650";
		String tapeBallServer ="sql2k802.discountasp.net";
		String tapeBallDb = "SQL2008_739130_dallascrick";
		String tapeBallSheetName = "DCL Tape Ball Schedule";
		//int tapeBallTournamentId = 1086433346;
		
		String leatherBallUserName = "SQL2012_881749_leatherball_user";
		String leatherBallPassword = "dcl950";
		String leatherBallServer ="sql2k1201.discountasp.net";
		String leatherBallDb = "SQL2012_881749_leatherball";
		String leatherBallSheetName = "DLCL Leather Ball Schedule";
		//int leatherBallTournamentId = 1086433329;
		
		//optional loading from properties file when u run from command line
		//loadFromPropertiesFile();
		ExcelLoader loader = new TapeBallExcelLoader(fileLocation);		
		
		DbConnector tapeBallConnector = new DbConnector(tapeBallServer, tapeBallDb, tapeBallUserName, tapeBallPassword);
		System.out.println("\n\nProcessing TapeBall Schedule...");
		processSchedule(tapeBallSheetName, loader, tapeBallConnector, new TapeBallAdapter(), 1086433352, 1086433353, 1086433354, 1086433355, 1086433356);
		System.out.println("Tapeball schedule loaded!!!\n\n");
		
		DbConnector leatherBallConnector = new DbConnector(leatherBallServer, leatherBallDb, leatherBallUserName, leatherBallPassword);
		System.out.println("Processing LeatherBall Schedule...");
		processSchedule(leatherBallSheetName, loader, leatherBallConnector, new LeatherBallAdapter(), 1086433334, 1086433335, 1086433336);
		System.out.println("Leatherball schedule loaded!!!\n\n");

	}

	private static void loadFromPropertiesFile() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
			 
			input = new FileInputStream("config.properties");
	 
			// load a properties file
			prop.load(input);
	 
			// get the property value and print it out
			fileLocation =prop.getProperty("fileLocation");
			tapeBallUserName = prop.getProperty("tapeBallUserName");
			tapeBallPassword = prop.getProperty("tapeBallPassword");
			tapeBallServer =prop.getProperty("tapeBallServer");
			tapeBallDb = prop.getProperty("tapeBallDb");
			tapeBallSheetName = prop.getProperty("tapeBallSheetName");
			
			leatherBallUserName = prop.getProperty("leatherBallUserName");
			leatherBallPassword = prop.getProperty("leatherBallPassword");
			leatherBallServer =prop.getProperty("leatherBallServer");
			leatherBallDb = prop.getProperty("leatherBallDb");
			leatherBallSheetName = prop.getProperty("leatherBallSheetName");
	 
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private static void processSchedule(String sheetName, ExcelLoader loader,
			DbConnector connector, AbstractAdapter adapter, int... tournamentIds) throws Exception {
		List<MatchData> matchDataList = loader.readScheduleExcel(sheetName);
		if(matchDataList==null||matchDataList.isEmpty()){
			System.err.print("No entries found in the excel sheet");
			return;
		}
		try {
			HashMap<String,Integer> teamList = connector.readTeamTable();
			HashMap<String,Integer> groundList = connector.readGroundTable();
			int latestMatchId = connector.getLatestMatchId();
			Timestamp latestMatchDt = connector.getLatestMatchDt(tournamentIds);
			
			List<Match> matchList = adapter.buildMatchObj(matchDataList, teamList, groundList, latestMatchId, latestMatchDt);
			connector.writeIntoMatchTable(matchList);
			connector.writeIntoMatchStatsTable(matchList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
