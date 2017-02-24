package net.dallascricket.scheduleLoader.db.adapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.dallascricket.scheduleLoader.db.domain.Match;
import net.dallascricket.scheduleLoader.domain.MatchData;

public class TapeBallAdapter extends AbstractAdapter{

	public List<Match> buildMatchObj(List<MatchData> matchDataList,
			HashMap<String, Integer> teamList,
			HashMap<String, Integer> groundList, int latestMatchId,
			Timestamp latestMatchDt) throws Exception {
		List<Match> matchList = new ArrayList<Match>();
		if (matchDataList != null) {
			for (MatchData matchData : matchDataList) {
				if (matchData.getDivision().equals("A")
						|| matchData.getDivision().equals("B")
						|| matchData.getDivision().equals("C")
						|| matchData.getDivision().equals("D")
						|| matchData.getDivision().equals("E")
						|| matchData.getDivision().equals("DCL")) {
					Timestamp excelMatchDt = new Timestamp(matchData.getDate()
							.getTime());
					//latest match dt can be null, if there is no tournament created yet 
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
