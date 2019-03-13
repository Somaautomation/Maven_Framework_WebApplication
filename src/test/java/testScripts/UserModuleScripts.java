package testScripts;

import driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	/***************************************
	 * TestScript Name	: TC_LoginLogout
	 * TestCaseID		: User_101
	 * 
	 * 
	 * *************************************
	 */
	public boolean TC_LoginLogout()
	{
		String strStatus = null;
		try {
			oBrowser = appDep.launchApp(appInd.getPropData("BROWSER"));
			if(oBrowser!=null)
			{
				strStatus+=String.valueOf(appDep.navigateURL(oBrowser));
				strStatus+=String.valueOf(appDep.loginToApp(oBrowser, "User_Login_101"));
				strStatus+=String.valueOf(appDep.logoutFromApp(oBrowser));
				strStatus+=String.valueOf(appDep.closeBrowser(oBrowser));
			}else {
				datatable.writeResult("Fail", "Executing 'TC_LoginLogout' TestScript", "Failed to open the browser");
				return false;
			}
			
			if(strStatus.contains("false"))
			{
				datatable.writeResult("Fail", "Executing 'TC_LoginLogout' TestScript", "The test case 'TC_LoginLogout'failed");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'TC_LoginLogout' TestScript", "Test case 'TC_LoginLogout' passed");
				return true;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Exception", "Executing 'TC_LoginLogout' TestScript", "Exception while executing 'TC_LoginLogout' method. "+e.getMessage());
			return false;
		}
		finally
		{
			oBrowser = null;
		}
	}
	
	
	
	/*********************************
	 * Script Name	: TC_Create_DeleteUser
	 * 
	 * 
	 * *****************************
	 */
	public boolean TC_Create_DeleteUser()
	{
		String strStatus = null;
		try {
			oBrowser = appDep.launchApp(appInd.getPropData("BROWSER"));
			if(oBrowser!=null)
			{
				strStatus+=String.valueOf(appDep.navigateURL(oBrowser));
				strStatus+=String.valueOf(appDep.loginToApp(oBrowser, "User_Login_101"));
				strStatus+=String.valueOf(users.createUser(oBrowser, "User_Create_102"));
				strStatus+=String.valueOf(users.deleteUser(oBrowser, System.getProperty("RT_User")));
				strStatus+=String.valueOf(appDep.logoutFromApp(oBrowser));
				strStatus+=String.valueOf(appDep.closeBrowser(oBrowser));
				
			}else {
				datatable.writeResult("Fail", "Executing 'TC_Create_DeleteUser' TestScript", "Failed to open the browser");
				return false;
			}
			
			if(strStatus.contains("false"))
			{
				datatable.writeResult("Fail", "Executing 'TC_Create_DeleteUser' TestScript", "The test case failed");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'TC_Create_DeleteUser' TestScript", "Test case passed");
				return true;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Exception", "Executing 'TC_Create_DeleteUser' TestScript", "Exception while executing 'TC_Create_DeleteUser' method. "+e.getMessage());
			return false;
		}
		finally
		{
			oBrowser = null;
		}
	}
}
