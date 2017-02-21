package net.dallascricket.scheduleLoader.domain;

import java.util.Date;

public class MatchData {
	
	private String week;
	private Date date;
	private String division;
	private double tournamentId;
	private String team1;
	private String team2;
	private String venue;
	private String umpire1;
	private String umpire2;
	private String day;
	private String time;
	
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getUmpire1() {
		return umpire1;
	}
	public void setUmpire1(String umpire1) {
		this.umpire1 = umpire1;
	}
	public String getUmpire2() {
		return umpire2;
	}
	public void setUmpire2(String umpire2) {
		this.umpire2 = umpire2;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	

	public double getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(double tournamentId) {
		this.tournamentId = tournamentId;
	}
	@Override
	public String toString() {
		return "MatchData [week=" + week + ", date=" + date + ", division="
				+ division + ", team1=" + team1 + ", team2=" + team2
				+ ", venue=" + venue + ", umpire1=" + umpire1 + ", umpire2="
				+ umpire2 + ", day=" + day + ", time=" + time + "]";
	}

	
	
	
}
