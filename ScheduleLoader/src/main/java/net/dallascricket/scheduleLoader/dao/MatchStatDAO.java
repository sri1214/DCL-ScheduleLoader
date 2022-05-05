package net.dallascricket.scheduleLoader.dao;

import net.dallascricket.scheduleLoader.db.domain.Match;
import net.dallascricket.scheduleLoader.db.domain.MatchStat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MatchStatDAO extends GenericDAO<MatchStat> {
	
	private static final String TABLENAME = "match_stats";
	private final static Logger logger = LogManager.getLogger(MatchStatDAO.class);

	public MatchStatDAO(Connection con) {
		super(con, TABLENAME);
	}
	
	public void writeIntoMatchStatsTable(List<Match> matchList)
			throws SQLException {
		logger.debug("writing into match_stats Table...");
		PreparedStatement statement = con.prepareStatement("INSERT INTO dbo.match_stats (match_id, team_id, inning_sw, runs_scored, total_runs, wickets, overs, extras, nos, wides, byes, over_throw) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
		int totalRows = 0;
		for (Match match : matchList) {
			statement.setInt(1, match.getMatch_id());
			statement.setInt(2, match.getTeam1Id());
			statement.setInt(3, 1);
			statement.setInt(4, 0);
			statement.setInt(5, 0);
			statement.setInt(6, 0);
			statement.setInt(7, 0);
			statement.setInt(8, 0);
			statement.setInt(9, 0);
			statement.setInt(10, 0);
			statement.setInt(11, 0);
			statement.setInt(12, 0);
			int val = statement.executeUpdate();
			totalRows += val;

			statement.setInt(1, match.getMatch_id());
			statement.setInt(2, match.getTeam2Id());
			statement.setInt(3, 2);
			statement.setInt(4, 0);
			statement.setInt(5, 0);
			statement.setInt(6, 0);
			statement.setInt(7, 0);
			statement.setInt(8, 0);
			statement.setInt(9, 0);
			statement.setInt(10, 0);
			statement.setInt(11, 0);
			statement.setInt(12, 0);
			val = statement.executeUpdate();
			totalRows += val;

		}
		logger.debug("Total rows loaded into match_stats table: "
				+ totalRows);
	}

}
