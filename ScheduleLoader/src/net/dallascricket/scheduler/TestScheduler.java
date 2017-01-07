package net.dallascricket.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestScheduler {

/*	public static void main(String[] args) {

		List<Division> divisions = buildDivisionList();
		Map<String, List<Slot>> weeks = new HashMap<>();
		List<Slot> matches = new ArrayList<>();
		
		
		Set<String> weekSet = weeks.keySet();
		
		for(int i=0;i<divisions.size();i++){
			//for(int j=0;)
		}
		
		
		
		
		for(String week:weekSet){
			List<Slot> slotList = weeks.get(week);
			int i=0;int divLength = divisions.size();
			for (Slot slot:slotList){
				if(i==divLength)
					i=0;
				Division playingDiv = divisions.get(i);
				Division umpiringDiv;
				if(i==divLength-1)
					umpiringDiv = divisions.get(0);
				else
					umpiringDiv = divisions.get(i+1);
				List<Team> playingTeams = getPlayingTeams(playingDiv, matches);
				
					
			}
		}

	}*/

	/**
	 * Need to Test this Logic
	 * @param playingDiv
	 * @param matches
	 * @return
	 */
	private static List<Team> getPlayingTeams(Division playingDiv,
			List<Slot> matches) {
		List<Team> playingTeams = new ArrayList<>();
		List<Team> teamsInDiv = playingDiv.getTeams();
		for(int i=0;i<teamsInDiv.size();i++){
			Team teamA = teamsInDiv.get(i);
			if(teamA.getMatches()==8)
				continue;
			for(int j=i+1;j<teamsInDiv.size();j++){
				Team teamB = teamsInDiv.get(j);
				if(teamB.getMatches()==8)
					continue;
				if(!matchAlreadyExist(teamA, teamB, matches)){
					playingTeams.add(teamA);
					playingTeams.add(teamA);
					return playingTeams;
				}
			}
		}
		return null;
	}

	private static boolean matchAlreadyExist(Team teamA, Team teamB,
			List<Slot> matches) {
		// TODO Auto-generated method stub
		return false;
	}

	private static List<Division> buildDivisionList() {
		List<Division> divList = new ArrayList<>();
		List<Team> teamDivAList = buildTeamDivAList();
		List<Team> teamDivBList = buildTeamDivBList();
		List<Team> teamDivCList = buildTeamDivCList();
		List<Team> teamDivDList = buildTeamDivDList();
		divList.add(new Division("A", teamDivAList));
		divList.add(new Division("B", teamDivBList));
		divList.add(new Division("C", teamDivCList));
		divList.add(new Division("D", teamDivDList));
		
		return divList;
	}

	private static List<Team> buildTeamDivDList() {
		List<Team> divD = new ArrayList<>();
		divD.add(new Team("Team26"));
		divD.add(new Team("Team27"));
		divD.add(new Team("Team28"));
		divD.add(new Team("Team29"));
		divD.add(new Team("Team30"));
		divD.add(new Team("Team31"));
		divD.add(new Team("Team32"));
		divD.add(new Team("Team33"));
		return divD;
	}

	private static List<Team> buildTeamDivCList() {
		List<Team> divC = new ArrayList<>();
		divC.add(new Team("Team18"));
		divC.add(new Team("Team19"));
		divC.add(new Team("Team20"));
		divC.add(new Team("Team21"));
		divC.add(new Team("Team22"));
		divC.add(new Team("Team23"));
		divC.add(new Team("Team24"));
		divC.add(new Team("Team25"));
		return divC;
	}

	private static List<Team> buildTeamDivBList() {
		List<Team> divB = new ArrayList<>();
		divB.add(new Team("Team9"));
		divB.add(new Team("Team10"));
		divB.add(new Team("Team11"));
		divB.add(new Team("Team12"));
		divB.add(new Team("Team13"));
		divB.add(new Team("Team15"));
		divB.add(new Team("Team16"));
		divB.add(new Team("Team17"));
		return divB;
	}

	private static List<String> buildGroundLIst() {
		return null;
	}

	private static List<Team> buildTeamDivAList() {
		List<Team> divA = new ArrayList<>();
		divA.add(new Team("Team1"));
		divA.add(new Team("Team2"));
		divA.add(new Team("Team3"));
		divA.add(new Team("Team4"));
		divA.add(new Team("Team5"));
		divA.add(new Team("Team6"));
		divA.add(new Team("Team7"));
		divA.add(new Team("Team8"));
		return divA;
	}

}
