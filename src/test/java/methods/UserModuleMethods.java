package methods;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import driver.DriverScript;

public class UserModuleMethods extends DriverScript{
	/***************************************
	 * Method Name	: createUser
	 * Purpose		: to create the new user
	 * 
	 * 
	 * *************************************
	 */
	public boolean createUser(WebDriver oDriver, String strLogicalName)
	{
		String strStatus = null;
		Map<String, String> oData = null;
		try {
			
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_User_Menu"));
			Thread.sleep(2000);
			
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_AddUser_Btn"));
			Thread.sleep(2000);
			
			//Enter user details
			oData = datatable.getDataFromExcel("createUser", strLogicalName);
			
			strStatus+=String.valueOf(appInd.setObject(oDriver, "obj_FirstName_Edit", oData.get("TD_FirstName")));
			strStatus+=String.valueOf(appInd.setObject(oDriver, "obj_LastName_Edit", oData.get("TD_LastName")));
			strStatus+=String.valueOf(appInd.setObject(oDriver, "obj_Email_Edit", oData.get("TD_Email")));
			strStatus+=String.valueOf(appInd.setObject(oDriver, "obj_User_UN_Edit", oData.get("TD_User_Name")));
			strStatus+=String.valueOf(appInd.setObject(oDriver, "obj_User_PWd_Edit", oData.get("TD_User_Password")));
			strStatus+=String.valueOf(appInd.setObject(oDriver, "obj_User_RetypePWd_Edit", oData.get("TD_Retype_Pwd")));
			
			//Click on CreateUser button
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_CreateUser_Btn"));
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.verifyText(oDriver, By.xpath("//span[text()="+"'"+oData.get("TD_LastName")+", "+oData.get("TD_FirstName")+"'"+"]"), "Text", oData.get("TD_LastName")+", "+oData.get("TD_FirstName")));
			
			System.setProperty("RT_User", oData.get("TD_LastName")+", "+oData.get("TD_FirstName"));
			if(strStatus.contains("false"))
			{
				datatable.writeResult("Fail", "Executing 'createUser' method", "FAiled to create the user");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'createUser' method", "User created successful");
				return true;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'createUser' method", "Exception while executing 'createUser' method. "+e.getMessage());
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: deleteUser
	 * Purpose		: to delete the user
	 * 
	 * 
	 * *************************************
	 */
	public boolean deleteUser(WebDriver oDriver, String strUserName)
	{
		String strStatus= null;
		try {
			if(oDriver.findElement(By.xpath("//span[text()="+"'"+strUserName+"'"+"]")).isDisplayed())
			{
				datatable.writeResult("Pass", "Executing 'deleteUser' method", "User is created and searched");
				strStatus+=String.valueOf(appInd.clickObject(oDriver, By.xpath("//span[text()="+"'"+strUserName+"'"+"]")));
				Thread.sleep(2000);
				strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_DeleteUser_Btn"));
				Thread.sleep(2000);
				oDriver.switchTo().alert().accept();
				Thread.sleep(2000);
				
				if(!appInd.elementPresent(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+strUserName+"'"+"]")))
				{
					strStatus+=String.valueOf(true);
				}else {
					strStatus+=String.valueOf(false);
				}
				
			}else {
				datatable.writeResult("Fail", "Executing 'deleteUser' method", "Failed to find the user");
				return false;
			}
			
			if(strStatus.contains("false")){
				datatable.writeResult("Fail", "Executing 'deleteUser' method", "Failed to delete the user");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'deleteUser' method", "User deleted successful");
				return true;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Exception", "Executing 'deleteUser' method", "Exception while executing 'deleteUser' method. "+e.getMessage());
			return false;
		}
	}
}
