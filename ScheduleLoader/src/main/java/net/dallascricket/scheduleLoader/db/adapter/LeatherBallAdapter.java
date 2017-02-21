package net.dallascricket.scheduleLoader.db.adapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.dallascricket.scheduleLoader.db.domain.Match;
import net.dallascricket.scheduleLoader.domain.MatchData;

public class LeatherBallAdapter extends AbstractAdapter{
	
	public List<Match> buildMatchObj(List<MatchData> matchDataList,
			HashMap<String, Integer> teamList,
			HashMap<String, Integer> groundList, int latestMatchId,
			Timestamp latestMatchDt) throws Exception {
		List<Match> matchList = new ArrayList<Match>();
		if (matchDataList != null) {
			for (MatchData matchData : matchDataList) {
				if (matchData.getDivision().startsWith("L")||matchData.getDivision().startsWith("DLCL")) {
					Timestamp excelMatchDt = new Timestamp(matchData.getDate()
							.getTime());
					if (latestMatchDt==null||excelMatchDt.after(latestMatchDt)) {

						Match match = buildMatchData(matchData, teamList,
								groundList, ++latestMatchId, latestMatchDt,
								excelMatchDt);
						matchList.add(match);
					}
				}
			}
		}

		return matchList;
	}

}
