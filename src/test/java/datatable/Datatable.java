package datatable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import driver.DriverScript;

public class Datatable extends DriverScript{
	/***************************************
	 * Method Name	: getRowCount
	 * Purpose		: to get rowCounts from the excel file
	 * 
	 * 
	 * *************************************
	 */
	public int getRowCount(String strFilePath, String strSheetName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		int rowCount = 0;
		try {
			fin = new FileInputStream(strFilePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(strSheetName);
			if(sh==null) {
				datatable.writeResult("Fail", "Executing 'getRowCount' method", "Failed to find the sheet '"+strSheetName+"'");
				return -1;
			}
			rowCount = sh.getPhysicalNumberOfRows();
			return rowCount;
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'getRowCount' method", "Exception while executing 'getRowCount' method. "+e.getMessage());
			return -1;
		}
		finally {
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				datatable.writeResult("Fail", "Executing 'getRowCount' method", "Exception while executing 'getRowCount' method. "+e.getMessage());
				return -1;
			}
		}
	}
	
	
	/***************************************
	 * Method Name	: createResultFile
	 * Purpose		: to create a result excel File with required columns
	 * 
	 * 
	 * *************************************
	 */
	public void createResultFile()
	{
		FileOutputStream fout = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		String strResFile = null;
		CellStyle style = null;
		Font font = null;
		try {
			strResFile = System.getProperty("user.dir")
					+"\\Results\\DetailedResults\\Results_"+
					appInd.getDateTime("ddMMYYYY_hhmmss")+".xlsx";
			wb = new XSSFWorkbook();
			
			//CellStyle for applying styles to the cells
			style = wb.createCellStyle();
			style.setFillBackgroundColor(IndexedColors
					.LIGHT_YELLOW.getIndex());
			style.setFillPattern(FillPatternType.FINE_DOTS);
			
			font = wb.createFont();
			font.setBold(true);
			style.setFont(font);
			
			sh = wb.createSheet("Results");
			row = sh.createRow(0);
			if(cell == null)
			{
				cell = row.createCell(0);
				cell.setCellValue("ProjectName");
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue("ModuleName");
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue("TestScriptName");
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue("TestCaseID");
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue("ExpectedRes");
				cell.setCellStyle(style);
				
				cell = row.createCell(5);
				cell.setCellValue("ActualRes");
				cell.setCellStyle(style);
				
				cell = row.createCell(6);
				cell.setCellValue("Status");
				cell.setCellStyle(style);
				
				cell = row.createCell(7);
				cell.setCellValue("ScreenShots");
				cell.setCellStyle(style);
			}
			
			fout = new FileOutputStream(strResFile);
			wb.write(fout);
			System.setProperty("RT_ResFile", strResFile);
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'createResultFile' method. "+e.getMessage());
		}
		finally {
			try {
				fout.flush();
				fout.close();
				fout = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				System.out.println("Exception while executing 'createResultFile' method. "+e.getMessage());
			}
		}
	}
	
	
	/***************************************
	 * Method Name	: writeResult
	 * Purpose		: to write the excel results
	 * 
	 * 
	 * *************************************
	 */
	public boolean writeResult(String strStatus, String strExpected, String strAcutal)
	{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = null;
		Font font = null;
		CreationHelper cHelper = null;
		Hyperlink hLink = null;
		String strFile = null;
		try {
			fin = new FileInputStream(System.getProperty("RT_ResFile"));
			wb = new XSSFWorkbook(fin);
			
			//Create a style for hyperlink
			cHelper = wb.getCreationHelper();
			style = wb.createCellStyle();
			font = wb.createFont();
			font.setUnderline(Font.U_SINGLE);
			font.setColor(IndexedColors.BLUE.getIndex());
			style.setFont(font);
			
			sh = wb.getSheet("Results");
			
			if(sh==null) {
				System.out.println("Failed to Find the sheet 'Results' for writing results");
				return false;
			}
			int rowNum = sh.getPhysicalNumberOfRows();
			row = sh.createRow(rowNum+1);
			if(strStatus.equalsIgnoreCase("Fail"))
			{
				if(oBrowser!=null) {
					strFile = appInd.captureScreenshot(oBrowser);
				}
				
				cell=row.createCell(7);
				cell.setCellValue("Error ScreenShot");
				
				strFile = strFile.replace("\\", "/");
				hLink = cHelper.createHyperlink(HyperlinkType.FILE);
				hLink.setAddress(strFile);
				cell.setHyperlink(hLink);
				cell.setCellStyle(style);
				
				style = wb.createCellStyle();
				font = wb.createFont();
				style.setFillBackgroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(FillPatternType.FINE_DOTS);
				font.setBold(true);
				style.setFont(font);
				cell = row.createCell(6);
				cell.setCellValue("Fail");
				cell.setCellStyle(style);
			}else {
				style = wb.createCellStyle();
				font = wb.createFont();
				style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
				style.setFillPattern(FillPatternType.FINE_DOTS);
				font.setBold(true);
				style.setFont(font);
				cell = row.createCell(6);
				cell.setCellValue("Pass");
				cell.setCellStyle(style);
			}
			
			cell = row.createCell(0);
			cell.setCellValue(strProject);
			
			cell = row.createCell(1);
			cell.setCellValue(strModule);
			
			cell = row.createCell(2);
			cell.setCellValue(strTestScript);
			
			cell = row.createCell(3);
			cell.setCellValue(strTestID);
			
			cell = row.createCell(4);
			cell.setCellValue(strExpected);
			
			cell = row.createCell(5);
			cell.setCellValue(strAcutal);
			
			fout = new FileOutputStream(System.getProperty("RT_ResFile"));
			wb.write(fout);
			return true;
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'writeResult' method. "+e.getMessage());
			return false;
		}
		finally {
			try {
				fout.flush();
				fout.close();
				fout = null;
				fin.close();
				fin = null;
				cell = null;
				row = null;
				cHelper = null;
				style = null;
				hLink = null;
				font = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				System.out.println("Exception while executing 'writeResult' method. "+e.getMessage());
				return false;
			}
		}
	}
	
	
	
	/***************************************
	 * Method Name	: getCellData
	 * Purpose		: to get the cell values based on rowNum and column name
	 * 
	 * 
	 * *************************************
	 */
	public String getCellData(String strFilePath, String strSheetName, String strColName, int rowNum)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		String strData = null;
		try {
			fin = new FileInputStream(strFilePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(strSheetName);
			if(sh==null) {
				datatable.writeResult("Fail", "Executing 'getCellData' method", "Failed to find the sheet '"+strSheetName+"'");
				return null;
			}
			
			//find column Number using column name
			row = sh.getRow(0);
			for(int r=0;r<row.getPhysicalNumberOfCells();r++)
			{
				cell = row.getCell(r);
				if(cell.getStringCellValue().trim().equals(strColName))
				{
					colNum = r;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			if(cell==null||cell.getCellTypeEnum()==cell.getCellTypeEnum().BLANK)
			{
				strData = "";
			}else if(cell.getCellTypeEnum()==cell.getCellTypeEnum().BOOLEAN)
			{
				strData = String.valueOf(cell.getBooleanCellValue());
			}
			else if(cell.getCellTypeEnum()==cell.getCellTypeEnum().STRING)
			{
				strData = cell.getStringCellValue();
			}
			else if(cell.getCellTypeEnum()==cell.getCellTypeEnum().NUMERIC)
			{
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(dt));
					String day;
					String month;
					String year;
					if(cal.get(Calendar.DAY_OF_MONTH)<10)
					{
						day="0"+cal.get(Calendar.DAY_OF_MONTH);
					}else {
						day=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}
					
					if((cal.get(Calendar.MONTH)+1)<10)
					{
						month="0"+(cal.get(Calendar.MONTH)+1);
					}else {
						month=String.valueOf(cal.get(Calendar.MONTH)+1);
					}
					
					year = String.valueOf(cal.get(Calendar.YEAR));
					
					strData = day+"/"+month+"/"+year;
						
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
			
			return strData;
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'getCellData' method", "Exception while executing 'getCellData' method. "+e.getMessage());
			return null;
		}
		finally {
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				datatable.writeResult("Fail", "Executing 'getCellData' method", "Exception while executing 'getCellData' method. "+e.getMessage());
				return null;
			}
		}
	}
	
	
	/***************************************
	 * Method Name	: getDataFromExcel
	 * Purpose		: to read the test data from the excel sheet
	 *              : based on the LogicalName
	 * 
	 * *************************************
	 */
	public Map<String, String> getDataFromExcel(String strSheetName, String strLogicalName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		String sKey = null;
		String sValue = null;
		int rowNum = 0;
		Map<String, String> oDataMap = null;
		try {
			oDataMap = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\"+strModule+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(strSheetName);
			if(sh==null) {
				datatable.writeResult("Fail", "Executing 'getDataFromExcel' method", "The sheetname '"+strSheetName+"' was not found in the testdata file");
				return null;
			}
			
			//Find rownumber for the given logicalName
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0;r<rows;r++)
			{
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().trim().equalsIgnoreCase(strLogicalName))
				{
					rowNum = r;
					break;
				}
			}
			
			if(rowNum>0)
			{
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				for(int c=0;c<row1.getLastCellNum();c++)
				{
					cell1 = row1.getCell(c);
					sKey = cell1.getStringCellValue();
					cell2 = row2.getCell(c);
					if(cell2==null||cell2.getCellTypeEnum()==cell2.getCellTypeEnum().BLANK)
					{
						sValue = "";
					}else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().BOOLEAN)
					{
						sValue = String.valueOf(cell2.getBooleanCellValue());
					}
					else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().STRING)
					{
						sValue = cell2.getStringCellValue();
					}
					else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().NUMERIC)
					{
						if(HSSFDateUtil.isCellDateFormatted(cell2))
						{
							double dt = cell2.getNumericCellValue();
							Calendar cal = Calendar.getInstance();
							cal.setTime(HSSFDateUtil.getJavaDate(dt));
							String day;
							String month;
							String year;
							if(cal.get(Calendar.DAY_OF_MONTH)<10)
							{
								day="0"+cal.get(Calendar.DAY_OF_MONTH);
							}else {
								day=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							if((cal.get(Calendar.MONTH)+1)<10)
							{
								month="0"+(cal.get(Calendar.MONTH)+1);
							}else {
								month=String.valueOf(cal.get(Calendar.MONTH)+1);
							}
							
							year = String.valueOf(cal.get(Calendar.YEAR));
							
							sValue = day+"/"+month+"/"+year;
								
						}else {
							sValue = String.valueOf(cell2.getNumericCellValue());
						}
					}
					oDataMap.put(sKey, sValue);
				}
			}else {
				datatable.writeResult("Fail", "Executing 'getDataFromExcel' method", "The logicalName '"+strLogicalName+"' doesnot exist in test data");
				return null;
			}
			
			return oDataMap;
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'getDataFromExcel' method", "Exception while executing 'getDataFromExcel' method. "+e.getMessage());
			return null;
		}
		finally {
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				datatable.writeResult("Fail", "Executing 'getDataFromExcel' method", "Exception while executing 'getDataFromExcel' method. "+e.getMessage());
				return null;
			}
		}
	}
	
	
	/***************************************
	 * Method Name	: getLocatorsAndConvertToMap
	 * Purpose		: to read all the locators from objectMapping
	 *              : and convert to Map.
	 * 
	 * *************************************
	 */
	public Map<String, String> getLocatorsAndConvertToMap()
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		String sKey = null;
		String sValue = null;
		Map<String, String> oLocatorsMap = null;
		try {
			oLocatorsMap = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir")+"\\ObjectMap\\ObjectMap.xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet("ActiTime");
			if(sh==null) {
				datatable.writeResult("Fail", "Executing 'getLocatorsAndConvertToMap' method", "The sheetName 'ActiTime' was not found in objectMap excel");
				return null;
			}
			
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0;r<rows;r++)
			{
				row = sh.getRow(r);
				cell = row.getCell(0);
				sKey = cell.getStringCellValue();
				sValue = row.getCell(1)+"#"+row.getCell(2);
				oLocatorsMap.put(sKey, sValue);
			}
			return oLocatorsMap;
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'getLocatorsAndConvertToMap' method", "Exception while executing 'getLocatorsAndConvertToMap' method. "+e.getMessage());
			return null;
		}
		finally {
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				datatable.writeResult("Fail", "Executing 'getLocatorsAndConvertToMap' method", "Exception while executing 'getLocatorsAndConvertToMap' method. "+e.getMessage());
				return null;
			}
		}
	}
	
	
	/***************************************
	 * Method Name	: setCellData
	 * Purpose		: to write the value to the excel cell
	 * 
	 * 
	 * *************************************
	 */
	public boolean setCellData(String strFile, String strSheetName, String strColName, int rowNum, String strData)
	{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		CellStyle style = null;
		Font font = null;
		try {
			fin = new FileInputStream(strFile);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(strModule);
			if(sh==null) {
				datatable.writeResult("Fail", "Executing 'setCellData' method", "The sheetName '"+strModule+"' was not found in the ExecutionController.xlsx file");
				return false;
			}
			
			//find column Number using column name
			row = sh.getRow(0);
			for(int r=0;r<row.getPhysicalNumberOfCells();r++)
			{
				cell = row.getCell(r);
				if(cell.getStringCellValue().trim().equals(strColName))
				{
					colNum = r;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			if(cell==null) cell = row.createCell(colNum);
			if(strData.equalsIgnoreCase("Pass"))
			{
				style = wb.createCellStyle();
				font = wb.createFont();
				style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
				style.setFillPattern(FillPatternType.FINE_DOTS);
				font.setBold(true);
				style.setFont(font);
				
			}else {
				style = wb.createCellStyle();
				font = wb.createFont();
				style.setFillBackgroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(FillPatternType.FINE_DOTS);
				font.setBold(true);
				style.setFont(font);
			}
			cell.setCellValue(strData);
			cell.setCellStyle(style);
			fout = new FileOutputStream(strFile);
			wb.write(fout);
			return true;
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'setCellData' method", "Exception while executing 'setCellData' method. "+e.getMessage());
			return false;
		}
		finally {
			try {
				fout.flush();
				fout.close();
				fout = null;
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				datatable.writeResult("Fail", "Executing 'setCellData' method", "Exception while executing 'setCellData' method. "+e.getMessage());
				return false;
			}
		}
	}
}
