package net.dallascricket.scheduleLoader.db.domain;

import java.sql.Timestamp;

public class Match {
	private int match_id;
	private int tournamentId;
	private int team1Id;
	private int team2Id;
	private int umpiringTeamId;
	private int winningTeamId;
	private short typeCd;
	private Timestamp matchDt;
	private int groundId;
	private String result;
	private int momPlayerId;
	private String startTime;
	private String endTime;
	private String umpiringText;
	private int umpiringTeam2Id;
	public int getMatch_id() {
		return match_id;
	}
	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}
	public int getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(int tournamentId) {
		this.tournamentId = tournamentId;
	}
	public int getTeam1Id() {
		return team1Id;
	}
	public void setTeam1Id(int team1Id) {
		this.team1Id = team1Id;
	}
	public int getTeam2Id() {
		return team2Id;
	}
	public void setTeam2Id(int team2Id) {
		this.team2Id = team2Id;
	}
	public int getUmpiringTeamId() {
		return umpiringTeamId;
	}
	public void setUmpiringTeamId(int umpiringTeamId) {
		this.umpiringTeamId = umpiringTeamId;
	}
	public int getWinningTeamId() {
		return winningTeamId;
	}
	public void setWinningTeamId(int winningTeamId) {
		this.winningTeamId = winningTeamId;
	}
	public short getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(short typeCd) {
		this.typeCd = typeCd;
	}

	public Timestamp getMatchDt() {
		return matchDt;
	}
	public void setMatchDt(Timestamp matchDt) {
		this.matchDt = matchDt;
	}
	public int getGroundId() {
		return groundId;
	}
	public void setGroundId(int groundId) {
		this.groundId = groundId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getMomPlayerId() {
		return momPlayerId;
	}
	public void setMomPlayerId(int momPlayerId) {
		this.momPlayerId = momPlayerId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUmpiringText() {
		return umpiringText;
	}
	public void setUmpiringText(String umpiringText) {
		this.umpiringText = umpiringText;
	}
	public int getUmpiringTeam2Id() {
		return umpiringTeam2Id;
	}
	public void setUmpiringTeam2Id(int umpiringTeam2Id) {
		this.umpiringTeam2Id = umpiringTeam2Id;
	}
	@Override
	public String toString() {
		return "Match [match_id=" + match_id + ", tournamentId=" + tournamentId
				+ ", team1Id=" + team1Id + ", team2Id=" + team2Id
				+ ", umpiringTeamId=" + umpiringTeamId + ", winningTeamId="
				+ winningTeamId + ", typeCd=" + typeCd + ", matchDt=" + matchDt
				+ ", groundId=" + groundId + ", result=" + result
				+ ", momPlayerId=" + momPlayerId + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", umpiringText=" + umpiringText
				+ ", umpiringTeam2Id=" + umpiringTeam2Id + "]";
	}
	

}
