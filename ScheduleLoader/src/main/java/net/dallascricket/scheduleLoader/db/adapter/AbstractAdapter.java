package net.dallascricket.scheduleLoader.db.adapter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.dallascricket.scheduleLoader.db.domain.Match;
import net.dallascricket.scheduleLoader.domain.MatchData;

import org.apache.log4j.Logger;

public abstract class AbstractAdapter {
	private final static Logger logger = Logger.getLogger(AbstractAdapter.class);
	
	public abstract List<Match> buildMatchObj(List<MatchData> matchDataList,
			HashMap<String, Integer> teamList,
			HashMap<String, Integer> groundList, int latestMatchId,
			Timestamp latestMatchDt) throws Exception ;
	
	protected Match buildMatchData(MatchData matchData, HashMap<String, Integer> teamList, HashMap<String, Integer> groundList, int latestMatchId, Timestamp latestMatchDt, Timestamp excelMatchDt) throws Exception {
		Match match = new Match();
		match.setMatch_id(latestMatchId);
		match.setTournamentId(matchData.getTournamentId());
		match.setTeam1Id(getTeamId(matchData.getTeam1().trim(),
				teamList));
		match.setTeam2Id(getTeamId(matchData.getTeam2().trim(),
				teamList));
		match.setUmpiringTeamId(getTeamId(matchData.getUmpire1()
				.trim(), teamList));
		match.setTypeCd((short) 1);
		match.setUmpiringTeam2Id(getTeamId(matchData.getUmpire2()
				.trim(), teamList));
		match.setMatchDt(excelMatchDt);
		match.setGroundId(getGroundId(matchData.getVenue().trim(),
				groundList));
		String time = matchData.getTime().trim();
		String[] timeArray = time.split("-");

		match.setStartTime(timeArray[0].trim());
		match.setEndTime(timeArray[1].trim());
		return match;
	}

	private int getGroundId(String venue, HashMap<String, Integer> groundList)
			throws Exception {
		if (groundList.containsKey(venue))
			return groundList.get(venue);
		else {
			logger.error("Couldn't find match for ground:" + venue);
			throw new Exception("couldn't find ground "+venue);
		}
	}

	private int getTeamId(String team, HashMap<String, Integer> teamList)
			throws Exception {
		
		Set<String> teamKeySet = teamList.keySet();
		for (String teamKeyFromDB : teamKeySet) {
			if (teamKeyFromDB.equalsIgnoreCase(team)) {
				return teamList.get(teamKeyFromDB);
			}				

		}
/*		if (teamList.containsKey(team))
			return teamList.get(team);
		else {
			System.err.println("Couldn't find match for team:" + team);
			throw new Exception("couldn't find team");
		}*/
		logger.error("Couldn't find match for team:" + team);
		throw new Exception("couldn't find team: "+team);
	}

	/**
	 * Got these values by referring dbo.tournament table. And strings need to match excel sheet div names
	 * @param division
	 * @return
	 * @throws Exception
	 */
	private int getTournamentId(String division) throws Exception {
		switch (division) {
		case "A":
			return 1086433352;
		case "B":
			return 1086433353;
		case "C":
			return 1086433354;
		case "D":
			return 1086433355;
		case "E":
			return 1086433356;
		case "LA":
			return 1086433334;
		case "LB":
			return 1086433335;
		case "LC":
			return 1086433336;
		default:
			logger.error("Couldn't find match for division:" + division);
			throw new Exception("invalid division");
		}
	}
	
	/**
	 * Got these values by referring dbo.tournament table. And strings need to match excel sheet div names
	 * @param division
	 * @return
	 * @throws Exception
	 */
/*	private int getTournamentId(String division) throws Exception {
		switch (division) {
		case "DCL A Division":
			return 1086433341;
		case "DCL B Division":
			return 1086433342;
		case "DCL C Division":
			return 1086433343;
		case "DCL D Division":
			return 1086433344;
		case "DLCL A Division":
			return 1086433327;
		case "DLCL B Division":
			return 1086433328;
		default:
			System.err.println("Couldn't find match for division:" + division);
			throw new Exception("invalid division");
		}
	}*/

}
