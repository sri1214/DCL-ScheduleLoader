package net.dallascricket.scheduler;

import java.util.List;

public class Division {
	
	private String name;
	private List<Team> teams;
	
	
	public Division(String name, List<Team> teams) {
		super();
		this.name = name;
		this.teams = teams;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	

}
