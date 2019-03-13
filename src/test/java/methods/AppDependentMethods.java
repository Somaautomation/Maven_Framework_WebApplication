package methods;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import driver.DriverScript;

public class AppDependentMethods extends DriverScript{
	/***************************************
	 * Method Name	: launchApp
	 * Purpose		: to launch the browser
	 * 
	 * 
	 * *************************************
	 */
	public WebDriver launchApp(String strBrowser)
	{
		WebDriver oDriver = null;
		try {
			switch(strBrowser.toLowerCase())
			{
				case "chrome":
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Library\\drivers\\chromedriver.exe");
					oDriver = new ChromeDriver();
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Library\\drivers\\IEDriverServer.exe");
					oDriver = new InternetExplorerDriver();
					break;
				case "ff":
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\Library\\drivers\\geckodriver.exe");
					oDriver = new FirefoxDriver();
					break;
				default:
					datatable.writeResult("Fail", "Executing 'launchApp' method", "Invalid browser name '"+strBrowser+"'");
			}
			
			if(oDriver!=null)
			{
				oDriver.manage().window().maximize();
				return oDriver;
			}else {
				return null;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'launchApp' method", "Exception while executing 'launchApp' method. "+e.getMessage());
			return null;
		}
	}
	
	
	/***************************************
	 * Method Name	: navigateURL
	 * Purpose		: to navigate the URL
	 * 
	 * 
	 * *************************************
	 */
	public boolean navigateURL(WebDriver oDriver)
	{
		try {
			oDriver.navigate().to(appInd.getPropData("URL"));
			Thread.sleep(2000);
			if(oDriver.getTitle().equals("actiTIME - Login"))
			{
				datatable.writeResult("Pass", "Executing 'navigateURL' method", "URL is navigate successful");
				return true;
			}
			else {
				datatable.writeResult("Fail", "Executing 'navigateURL' method", "Failed to navigate the URL");
				return false;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'navigateURL' method", "Exception while executing 'navigateURL' method. "+e.getMessage());
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: loginToApp
	 * Purpose		: to navigate the URL
	 * 
	 * 
	 * *************************************
	 */
	public boolean loginToApp(WebDriver oDriver, String strLogicalName)
	{
		String strStatus = null;
		Map<String, String> oData = null;
		try {
			oData = datatable.getDataFromExcel("loginToApp", strLogicalName);
			strStatus+=String.valueOf(appInd.setObject(oDriver, "obj_UserName_Edit", oData.get("TD_UserName")));
			strStatus+=String.valueOf(appInd.setObject(oDriver, "obj_Password_Edit", oData.get("TD_Password")));
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_Login_Link"));
			Thread.sleep(4000);
			
			//validate optional short cut element
			if(appInd.elementPresent(oDriver, By.id("gettingStartedShortcutsMenuCloseId")))
			{
				strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_ShortCut_Icon"));
			}
			
			
			//Validate login is successful
			strStatus+=String.valueOf(appInd.verifyText(oDriver, "obj_TimeTrack_Page", "Text", "Enter Time-Track"));
			
			if(strStatus.contains("false"))
			{
				datatable.writeResult("Fail", "Executing 'loginToApp' method", "Login failed");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'loginToApp' method", "Login is successful");
				return true;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'loginToApp' method", "Exception while executing 'loginToApp' method. "+e.getMessage());
			return false;
		}
	}
	
	
	/***************************************
	 * Method Name	: logoutFromApp
	 * Purpose		: to logout from the app
	 * 
	 * 
	 * *************************************
	 */
	public boolean logoutFromApp(WebDriver oDriver)
	{
		String strStatus = null;
		try {
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_Logout_Link"));
			Thread.sleep(2000);
			
			if(appInd.createAndGetObject(oDriver, "obj_Logo_Image").isDisplayed())
			{
				strStatus+=String.valueOf(true);
			}else {
				strStatus+=String.valueOf(false);
			}
			
			if(strStatus.contains("false"))
			{
				datatable.writeResult("Fail", "Executing 'logoutFromApp' method", "Failed to logout");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'logoutFromApp' method", "Logout is successful");
				return true;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'logoutFromApp' method", "Exception while executing 'logoutFromApp' method. "+e.getMessage());
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: closeBrowser
	 * Purpose		: to close the browser
	 * 
	 * 
	 * *************************************
	 */
	public boolean closeBrowser(WebDriver oDriver)
	{
		try {
			oDriver.close();
			return true;
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'closeBrowser' method", "Exception while executing 'closeBrowser' method. "+e.getMessage());
			return false;
		}
		finally {
			oDriver = null;
		}
	}
}
