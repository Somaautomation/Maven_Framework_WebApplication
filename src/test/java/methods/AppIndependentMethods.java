package methods;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/***************************************
	 * Method Name	: getDateTime
	 * Purpose		: to get the current system date in required format
	 * 
	 * 
	 * *************************************
	 */
	public String getDateTime(String strDateFormat)
	{
		Calendar cal = null;
		SimpleDateFormat sdf = null;
		try {
			cal = Calendar.getInstance();
			sdf = new SimpleDateFormat(strDateFormat);
			return sdf.format(cal.getTime());
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'getDateTime' method", "Exception while executing 'getDateTime' method. "+e.getMessage());
			return null;
		}
		finally {
			cal = null;
			sdf = null;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: captureScreenshot
	 * Purpose		: to get the current system date in required format
	 * 
	 * 
	 * *************************************
	 */
	public String captureScreenshot(WebDriver oDriver)
	{
		File objSrcFile = null;
		String strDestPath = null;
		try {
			strDestPath = System.getProperty("user.dir")+""
					+ "\\Results\\ErrorScreenshots"
					+ "\\"+strTestID+"\\Error_ScreenShot_"+appInd.getDateTime("ddMMYYYY_hhmmss")+".png";
			
			objSrcFile = ((TakesScreenshot)oDriver)
					.getScreenshotAs(OutputType.FILE);
			
			FileUtils.copyFile(objSrcFile, new File(strDestPath));
			
			return strDestPath;
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'captureScreenshot' method", "Exception while executing 'captureScreenshot' method. "+e.getMessage());
			return null;
		}
		finally {
			objSrcFile = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: clickObject
	 * Purpose		: to click the element
	 * 
	 * 
	 * *************************************
	 */
	public boolean clickObject(WebDriver oDriver, By objBy)
	{
		WebElement oEle = null;
		try {
			oEle = oDriver.findElement(objBy);
			if(oEle.isDisplayed())
			{
				oEle.click();
				datatable.writeResult("Pass", "Executing 'clickObject' method", "The element '"+String.valueOf(objBy)+"' was clicked successful");
				return true;
			}else {
				datatable.writeResult("Fail", "Executing 'clickObject' method", "The element '"+String.valueOf(objBy)+"' was unable to find");
				return false;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'clickObject' method", "Exception while executing 'clickObject' method. "+e.getMessage());
			return false;
		}
		finally {
			oEle = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: clickObject
	 * Purpose		: to click the element
	 * 
	 * 
	 * *************************************
	 */
	public boolean clickObject(WebDriver oDriver, String strObjectName)
	{
		WebElement oEle = null;
		try {
			oEle = appInd.createAndGetObject(oDriver, strObjectName);
			if(oEle.isDisplayed())
			{
				oEle.click();
				datatable.writeResult("Pass", "Executing 'clickObject' method", "The element '"+strObjectName+"' was clicked successful");
				return true;
			}else {
				datatable.writeResult("Fail", "Executing 'clickObject' method", "The element '"+strObjectName+"' was unable to find");
				return false;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'clickObject' method", "Exception while executing 'clickObject' method. "+e.getMessage());
			return false;
		}
		finally {
			oEle = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: setObject
	 * Purpose		: to enter the value in the element
	 * 
	 * 
	 * *************************************
	 */
	public boolean setObject(WebDriver oDriver, By objBy, String strData)
	{
		WebElement oEle = null;
		try {
			oEle = oDriver.findElement(objBy);
			if(oEle.isDisplayed())
			{
				oEle.sendKeys(strData);
				datatable.writeResult("Pass", "Executing 'setObject' method", "The data '"+strData+"' was entered in the element '"+String.valueOf(objBy)+"' successful");
				return true;
			}else {
				datatable.writeResult("Fail", "Executing 'setObject' method", "Failed to enter the data '"+strData+"' in the element '"+String.valueOf(objBy)+"'");
				return false;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'setObject' method", "Exception while executing 'setObject' method. "+e.getMessage());
			return false;
		}finally {
			oEle = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: setObject
	 * Purpose		: to enter the value in the element
	 * 
	 * 
	 * *************************************
	 */
	public boolean setObject(WebDriver oDriver, String strObjectName, String strData)
	{
		WebElement oEle = null;
		try {
			oEle = appInd.createAndGetObject(oDriver, strObjectName);
			if(oEle.isDisplayed())
			{
				oEle.sendKeys(strData);
				datatable.writeResult("Pass", "Executing 'setObject' method", "The data '"+strData+"' was entered in the element '"+strObjectName+"' successful");
				return true;
			}else {
				datatable.writeResult("Fail", "Executing 'setObject' method", "Failed to enter the data '"+strData+"' in the element '"+strObjectName+"'");
				return false;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'setObject' method", "Exception while executing 'setObject' method. "+e.getMessage());
			return false;
		}finally {
			oEle = null;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: verifyText
	 * Purpose		: to verify the value in the element
	 * 
	 * 
	 * *************************************
	 */
	public boolean verifyText(WebDriver oDriver, By objBy, String strType, String strExpected)
	{
		WebElement oEle = null;
		String strActual = null;
		Select oSel = null;
		try
		{
			oEle = oDriver.findElement(objBy);
			if(oEle.isDisplayed())
			{
				switch(strType.toLowerCase())
				{
					case "text":
						strActual = oEle.getText();
						break;
					case "value":
						strActual = oEle.getAttribute("value");
						break;
					case "list":
						oSel = new Select(oEle);
						strActual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						datatable.writeResult("Fail", "Executing 'verifyText' method", "Invalid object type '"+strType+"' was specified");
				}
				
				if(strActual.equals(strExpected))
				{
					datatable.writeResult("Pass", "Executing 'verifyText' method", "Both actual: '"+strActual+"' & expected: '"+strExpected+"' are same");
					return true;
				}else {
					datatable.writeResult("Fail", "Executing 'verifyText' method", "Both actual: '"+strActual+"' & expected: '"+strExpected+"' are NOT matching");
					return false;
				}
			}else {
				datatable.writeResult("Fail", "Executing 'verifyText' method", "Failed to read the '"+strType+"' from the element '"+String.valueOf(objBy)+"'");
				return false;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'verifyText' method", "Exception while executing 'verifyText' method. "+e.getMessage());
			return false;
		}finally {
			oEle = null;
			oSel = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: verifyText
	 * Purpose		: to verify the value in the element
	 * 
	 * 
	 * *************************************
	 */
	public boolean verifyText(WebDriver oDriver, String strObjectName, String strType, String strExpected)
	{
		WebElement oEle = null;
		String strActual = null;
		Select oSel = null;
		try
		{
			oEle = appInd.createAndGetObject(oDriver, strObjectName);
			if(oEle.isDisplayed())
			{
				switch(strType.toLowerCase())
				{
					case "text":
						strActual = oEle.getText();
						break;
					case "value":
						strActual = oEle.getAttribute("value");
						break;
					case "list":
						oSel = new Select(oEle);
						strActual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						datatable.writeResult("Fail", "Executing 'verifyText' method", "Invalid object type '"+strType+"' was specified");
				}
				
				if(strActual.equals(strExpected))
				{
					datatable.writeResult("Pass", "Executing 'verifyText' method", "Both actual: '"+strActual+"' & expected: '"+strExpected+"' are same");
					return true;
				}else {
					datatable.writeResult("Fail", "Executing 'verifyText' method", "Both actual: '"+strActual+"' & expected: '"+strExpected+"' are NOT matching");
					return false;
				}
			}else {
				datatable.writeResult("Fail", "Executing 'verifyText' method", "Failed to read the '"+strType+"' from the element '"+strObjectName+"'");
				return false;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'verifyText' method", "Exception while executing 'verifyText' method. "+e.getMessage());
			return false;
		}finally {
			oEle = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: getPropData
	 * Purpose		: to get the value from the Config.properties file
	 * 
	 * 
	 * *************************************
	 */
	public String getPropData(String strKeyName)
	{
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir")+"\\Configuration\\Config.properties");
			prop = new Properties();
			prop.load(fin);
			return prop.getProperty(strKeyName);
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'getPropData' method", "Exception while executing 'getPropData' method. "+e.getMessage());
			return null;
		}
		finally {
			try 
			{
				fin.close();
				fin = null;
				prop = null;
			}catch(Exception e)
			{
				datatable.writeResult("Fail", "Executing 'getPropData' method", "Exception while executing 'getPropData' method. "+e.getMessage());
				return null;
			}
		}
	}
	
	
	/***************************************
	 * Method Name	: elementPresent
	 * Purpose		: to verify the presence of the element
	 * 
	 * 
	 * *************************************
	 */
	public boolean elementPresent(WebDriver oDriver, By objBy)
	{
		try {
			oDriver.findElement(objBy);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: getDateTime
	 * Purpose		: to get the current system date in required format
	 * 
	 * 
	 * *************************************
	 */
	public WebElement createAndGetObject(WebDriver oDriver, String strObjectName)
	{
		WebElement oEle = null;
		String strObjDesc = null;
		try {
			strObjDesc = objLocator.get(strObjectName);
			String arr[] = strObjDesc.split("#");
			switch(arr[0].toLowerCase())
			{
				case "id":
					oEle = oDriver.findElement(By.id(arr[1]));
					break;
				case "xpath":
					oEle = oDriver.findElement(By.xpath(arr[1]));
					break;
				case "cssselector":
					oEle = oDriver.findElement(By.cssSelector(arr[1]));
					break;
				case "linktext":
					oEle = oDriver.findElement(By.linkText(arr[1]));
					break;
				case "name":
					oEle = oDriver.findElement(By.name(arr[1]));
					break;
				case "classname":
					oEle = oDriver.findElement(By.className(arr[1]));
					break;
				default:
					datatable.writeResult("Fail", "Executing 'createAndGetObject' method", "Invalid locator name '"+arr[0]+"'");
			}
			return oEle;
			
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'createAndGetObject' method", "Exception while executing 'createAndGetObject' method. "+e.getMessage());
			return null;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: handleUploadWindow
	 * Purpose		: to handle the upload window using autoIT
	 * 
	 * 
	 * *************************************
	 */
	public boolean handleUploadWindow(String strFileName)
	{
		String strExeFile = null;
		String strFilePath = null;
		try {
			strExeFile = System.getProperty("user.dir")+"\\Library\\autoIT\\uploadAutoIT.exe";
			strFilePath = System.getProperty("user.dir")+"\\Library\\autoIT\\"+strFileName;
			Runtime.getRuntime().exec(strExeFile+" "+strFilePath);
			Thread.sleep(5000);
			return true;
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'handleUploadWindow' method", "Exception while executing 'handleUploadWindow' method. "+e.getMessage());
			return false;
		}
		finally {
			strExeFile = null;
			strFilePath = null;
		}
	}
	
	
	/***************************************
	 * Method Name	: dateDifference
	 * Purpose		: to find the total time taken to execute the testScript
	 * 
	 * 
	 * *************************************
	 */
	public String dateDifference(String date1, String date2)
	{
		String strDate=null;
		try
		{
		    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		    Date d1 =null;
		    Date d2 = null;
		    try {
		        d1 = format.parse(date1);
		        d2 = format.parse(date2);
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }

		    // Get msec from each, and subtract.
		    long diff = d2.getTime() - d1.getTime();
		    long diffSeconds = diff / 1000 % 60;
		    long diffMinutes = diff / (60 * 1000) % 60;
		    long diffHours = diff / (60 * 60 * 1000);
		    strDate=diffHours+":"+diffMinutes+":"+diffSeconds;
		    return strDate;
		}catch(Exception e)
		{
			return null;
		}
	}
}
