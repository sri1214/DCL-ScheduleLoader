package net.dallascricket.scheduleLoader.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dallascricket.scheduleLoader.ExcelProcessor.ScheduleExcelLoader;
import net.dallascricket.scheduleLoader.dao.GroundDAO;
import net.dallascricket.scheduleLoader.dao.MatchDAO;
import net.dallascricket.scheduleLoader.dao.MatchStatDAO;
import net.dallascricket.scheduleLoader.dao.TeamDAO;
import net.dallascricket.scheduleLoader.dao.manager.DAOManager;
import net.dallascricket.scheduleLoader.dao.manager.DAOManager.Table;
import net.dallascricket.scheduleLoader.dao.manager.LeatherBallDBDAOManager;
import net.dallascricket.scheduleLoader.dao.manager.TapeBallDBDAOManager;
import net.dallascricket.scheduleLoader.dao.manager.TestDBDAOManager;
import net.dallascricket.scheduleLoader.db.adapter.AbstractAdapter;
import net.dallascricket.scheduleLoader.db.adapter.LeatherBallAdapter;
import net.dallascricket.scheduleLoader.db.adapter.TapeBallAdapter;
import net.dallascricket.scheduleLoader.db.domain.Match;
import net.dallascricket.scheduleLoader.domain.MatchData;
import net.dallascricket.scheduleLoader.util.ServiceProperties;

import org.apache.log4j.Logger;

public class ScheduleLoaderController {
	private DAOManager testDAOManager;
	private DAOManager tapeDAOManager;
	private DAOManager leatherDAOManager;
	private ScheduleExcelLoader scheduleLoader;
	private final static Logger logger = Logger.getLogger(ScheduleLoaderController.class);

	public ScheduleLoaderController(InputStream excelSheetInputStream)
			throws IOException {
		scheduleLoader = new ScheduleExcelLoader(excelSheetInputStream);
		testDAOManager = TestDBDAOManager.getInstance();
		tapeDAOManager = TapeBallDBDAOManager.getInstance();
		leatherDAOManager = LeatherBallDBDAOManager.getInstance();
	}

	public void loadTapeBall() throws Exception {
		logger.info("\n\nProcessing TapeBall Schedule...");
		try {
			List<MatchData> matchDataList = scheduleLoader.readTapeBallSheet();
			processMatchData(matchDataList, new TapeBallAdapter(), tapeDAOManager);
		} catch (Exception e) {
			logger.error("Tapeball schedule upload failed\n", e);
			throw e;
		}
		logger.info("Tapeball schedule loaded!!!\n\n");

	}

	public void loadLeatherBall() throws Exception {
		logger.info("\n\nProcessing LeatherBall Schedule...");
		try {
			List<MatchData> matchDataList = scheduleLoader
					.readLeatherBallBallSheet();
			processMatchData(matchDataList, new LeatherBallAdapter(),
					leatherDAOManager);
		} catch (Exception e) {
			logger.error("LeatherBall schedule upload failed\n", e);
			throw e;
		}
		logger.info("LeatherBall schedule loaded!!!\n\n");
	}

	protected void processMatchData(List<MatchData> matchDataList,
			AbstractAdapter adapter, DAOManager daoManager) throws Exception {
		if (matchDataList == null || matchDataList.isEmpty()) {
			logger.error("No entries found in the excel sheet");
			throw new Exception("No entries found in the excel sheet");
		}
		try {
			TeamDAO teamDAO = (TeamDAO) daoManager.getDAO(Table.TEAM);
			GroundDAO groundDAO = (GroundDAO) daoManager.getDAO(Table.GROUND);
			HashMap<String, Integer> teamList = teamDAO.readTeamTable();
			HashMap<String, Integer> groundList = groundDAO.readGroundTable();
			MatchDAO matchDAO = (MatchDAO) daoManager.getDAO(Table.MATCH);
			int latestMatchId = matchDAO.getLatestMatchId();
			double[] tournamentIds = getTournamentIds(matchDataList);
			Timestamp latestMatchDt = matchDAO.getLatestMatchDt(tournamentIds);

			List<Match> matchList = adapter.buildMatchObj(matchDataList,
					teamList, groundList, latestMatchId, latestMatchDt);
			Boolean prodEnv = Boolean.parseBoolean(ServiceProperties.getServiceProperty("ProdEnvironment"));
			if(!prodEnv)
				WriteToTestDB(matchList);
			else
				WriteToDB(matchList, daoManager);
		} catch (SQLException e) {
			logger.error("error processing match data-"+ e.getMessage());
			throw e;
		}
	}

	private void WriteToDB(List<Match> matchList, DAOManager daoManager) throws SQLException, ClassNotFoundException {
		MatchDAO matchDAO = (MatchDAO) daoManager.getDAO(Table.MATCH);
		MatchStatDAO matchStatDAO = (MatchStatDAO) daoManager.getDAO(Table.MATCHSTAT);
		matchDAO.loadMatches(matchList);
		matchStatDAO.writeIntoMatchStatsTable(matchList);
		
	}

	private double[] getTournamentIds(List<MatchData> matchDataList)
			throws Exception {
		Set<Double> tournamentIdSet = new HashSet<>();
		double[] tournamentIds;
		for (MatchData matchData : matchDataList) {
			tournamentIdSet.add(matchData.getTournamentId());
		}
		tournamentIds = new double[tournamentIdSet.size()];
		int i = 0;
		for (Double tournamentId : tournamentIdSet) {
			double tourId = tournamentId;
			tournamentIds[i++] = tourId;
			// TODO: validate
		}
		return tournamentIds;
	}

	private void WriteToTestDB(List<Match> matchList) throws SQLException, ClassNotFoundException {
		MatchDAO localMatchDAO = (MatchDAO) testDAOManager.getDAO(Table.MATCH);
		MatchStatDAO localMatchStatDAO = (MatchStatDAO) testDAOManager.getDAO(Table.MATCHSTAT);
		localMatchDAO.loadMatches(matchList);
		localMatchStatDAO.writeIntoMatchStatsTable(matchList);
	}

}
