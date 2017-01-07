package net.dallascricket.scheduler;

public class Team {
	private String name;
	private int matches;
	private int umpirings;
	
	public Team(String team){
		this.name = team;
	}

	public void incrementMatches(){
		matches++;
	}
	
	public void incrementUmpirings(){
		umpirings++;
	}

	public String getName() {
		return name;
	}

	public int getMatches() {
		return matches;
	}

	public int getUmpirings() {
		return umpirings;
	}
	
	
}
