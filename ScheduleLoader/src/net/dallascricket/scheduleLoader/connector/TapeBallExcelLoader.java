package net.dallascricket.scheduleLoader.connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.dallascricket.scheduleLoader.domain.MatchData;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TapeBallExcelLoader implements ExcelLoader {

	private final String fileLoc;
	private static int firstNonEmptyCellIndex;
	private static boolean isFirstNonEmptyCellIndexInitialized;

	public TapeBallExcelLoader(String fileLoc) {
		this.fileLoc = fileLoc;
		System.out.println("Schedule excel sheet - "+fileLoc);
	}

	@Override
	public List<MatchData> readScheduleExcel(String sheetName) {
		FileInputStream file;
		List<MatchData> matchDataList = new ArrayList<MatchData>();
		try {
			file = new FileInputStream(new File(fileLoc));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			System.out.println("Reading excel file sheet - "+sheet.getSheetName());
			Iterator<Row> rowIterator = sheet.iterator();
			int j = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (isRowEmpty(row))
					continue;
				j++;
				if (j != 1) {
					if (!isFirstNonEmptyCellIndexInitialized) {
						firstNonEmptyCellIndex = getFirstNonEmptyCellIndex(row);
						isFirstNonEmptyCellIndexInitialized = true;
					}

					if (row.getCell(firstNonEmptyCellIndex).getCellType() != Cell.CELL_TYPE_NUMERIC)
						break;
					else {
						MatchData matchData = loadMatchData(row,
								firstNonEmptyCellIndex);

						matchDataList.add(matchData);
					}
				}
			}

			file.close();
			System.out.println("No. of entries - " + matchDataList.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return matchDataList;
	}

	private MatchData loadMatchData(Row row, int i) {
		MatchData matchData = new MatchData();
		matchData.setWeek(""+row.getCell(i).getNumericCellValue());
		matchData.setDate(row.getCell(++i).getDateCellValue());
		matchData.setDivision(row.getCell(++i).getStringCellValue());
		matchData.setTeam1(row.getCell(++i).getStringCellValue());
		matchData.setTeam2(row.getCell(++i).getStringCellValue());
		matchData.setVenue(row.getCell(++i).getStringCellValue());
		matchData.setUmpire1(row.getCell(++i).getStringCellValue());
		matchData.setUmpire2(row.getCell(++i).getStringCellValue());
		//++i;//skipping one column as spring 2016 schedule has unwanted unpiring column
		matchData.setDay(row.getCell(++i).getStringCellValue());
		matchData.setTime(row.getCell(++i).getStringCellValue());
		return matchData;
	}

	private int getFirstNonEmptyCellIndex(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return c;
		}
		return 0;
	}

	public boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}

}
