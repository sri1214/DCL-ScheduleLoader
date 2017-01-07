package net.dallascricket.scheduleLoader.connector;

import java.util.List;

import net.dallascricket.scheduleLoader.domain.MatchData;

public interface ExcelLoader {
	
	public List<MatchData> readScheduleExcel(String sheetName);

}
