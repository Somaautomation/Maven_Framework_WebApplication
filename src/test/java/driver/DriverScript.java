package driver;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import datatable.Datatable;
import methods.AppDependentMethods;
import methods.AppIndependentMethods;
import methods.TaskModuleMethods;
import methods.UserModuleMethods;
import reportUtils.ReportsUtility;


public class DriverScript {
	public static WebDriver oBrowser = null;
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static UserModuleMethods users = null;
	public static TaskModuleMethods tasks = null;
	public static Datatable datatable = null;
	public static String strController = null;
	public static String strModule = null;
	public static String strProject = null;
	public static String strTestID = null;
	public static String strTestScript = null;
	public static String strBrowser = null;
	public static Map<String, String> objLocator = null;
	public static ReportsUtility reports = null;
	
	@BeforeSuite
	public void loadClassFile()
	{
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			users = new UserModuleMethods();
			tasks = new TaskModuleMethods();
			datatable = new Datatable();
			strController = System.getProperty("user.dir")+"\\Controller\\ExecutionController.xlsx";
			reports = new ReportsUtility();
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'loadClassFile' method. "+e.getMessage());
		}
	}
	
	@BeforeClass
	public void preRequisite()
	{
		try {
			datatable.createResultFile();
			objLocator = datatable.getLocatorsAndConvertToMap();
			String strFile = System.getProperty("user.dir")+"\\Results\\HTMLResults\\ActiTime_Reports.html";
			reports.CreateReport(strFile, appInd.getPropData("APPNAME"), appInd.getPropData("URL"), appInd.getDateTime("dd-MM-YYYY hh:mm:ss"));
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'preRequisite' method. "+e.getMessage());
		}
	}
	
	@Test
	public void runTestScripts()
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		String strExecute = null;
		Class cls = null;
		Object obj = null;
		Method meth = null;
		reports.startSuite();
		String status = null;
		try {
			fin = new FileInputStream(strController);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet("Project");
			int intP = datatable.getRowCount(strController, "Project");
			for(int i=0;i<intP-1;i++)
			{
				strExecute = datatable.getCellData(strController, "Project", "ExecuteTest", i+1);
				if(strExecute.equalsIgnoreCase("Yes"))
				{
					strProject = datatable.getCellData(strController, "Project", "ProjectName", i+1);
					int intM = datatable.getRowCount(strController, strProject);
					for(int j=0;j<intM-1;j++)
					{
						strExecute = datatable.getCellData(strController, strProject, "ExecuteTest", j+1);
						if(strExecute.equalsIgnoreCase("Yes"))
						{
							String strStart = appInd.getDateTime("dd-MM-YYYY hh:mm:ss");
							strModule = datatable.getCellData(strController, strProject, "ModuleName", j+1);
							int intTC = datatable.getRowCount(strController, strModule);
							for(int k=0;k<intTC-1;k++)
							{
								strExecute = datatable.getCellData(strController, strModule, "ExecuteTest", k+1);
								if(strExecute.equalsIgnoreCase("Yes"))
								{
									strTestID = datatable.getCellData(strController, strModule, "TestCaseID", k+1);
									strTestScript = datatable.getCellData(strController, strModule, "TestScriptName", k+1);
									String strClsName = datatable.getCellData(strController, strModule, "ClassName", k+1);
									strBrowser = datatable.getCellData(strController, strModule, "Browser", k+1);
									
									cls = Class.forName(strClsName);
									obj = cls.newInstance();
									meth = obj.getClass().getMethod(strTestScript);
									String sStatus = String.valueOf(meth.invoke(obj));
									if(sStatus.equalsIgnoreCase("true"))
									{
										datatable.setCellData(strController, strModule, "Status", k+1, "Pass");
										status = "Pass";
									}else {
										datatable.setCellData(strController, strModule, "Status", k+1, "Fail");
										status = "Fail";
									}
									String strEnd = appInd.getDateTime("dd-MM-YYYY hh:mm:ss");
									String strTotal = appInd.dateDifference(strStart, strEnd);
									reports.addTestCase(strTestID, strModule, strTestScript, appInd.getPropData("BROWSER"), status, strTotal);
								}
							}
						}
					}
				}
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		finally {
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
				cls = null;
				obj = null;
				meth = null;
			}catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
	
	@AfterSuite
	public void postRequisite()
	{
		reports.endSuite();
		reports.updateEndTime(appInd.getDateTime("dd-MM-YYYY hh:mm:ss"));
	}
}
