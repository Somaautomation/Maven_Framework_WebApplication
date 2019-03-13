package testScripts;

import driver.DriverScript;

public class TaskModuleScripts extends DriverScript{
	/*********************************
	 * Script Name	: TC_Create_DeleteTask
	 * 
	 * 
	 * *****************************
	 */
	public boolean TC_Import_DeleteTask()
	{
		String strStatus = null;
		try {
			oBrowser = appDep.launchApp(appInd.getPropData("BROWSER"));
			if(oBrowser!=null)
			{
				strStatus+=String.valueOf(appDep.navigateURL(oBrowser));
				strStatus+=String.valueOf(appDep.loginToApp(oBrowser, "Task_Login_101"));
				strStatus+=String.valueOf(tasks.importTask(oBrowser, "Task_Import_101"));
				strStatus+=String.valueOf(tasks.deleteTask(oBrowser));
				strStatus+=String.valueOf(appDep.logoutFromApp(oBrowser));
				strStatus+=String.valueOf(appDep.closeBrowser(oBrowser));
			}else {
				datatable.writeResult("Fail", "Executing 'TC_Create_DeleteTask' TestScript", "Failed to open the browser");
				return false;
			}
			
			if(strStatus.contains("false"))
			{
				datatable.writeResult("Fail", "Executing 'TC_Create_DeleteTask' TestScript", "The test case 'TC_Create_DeleteTask");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'TC_Create_DeleteTask' TestScript", "Test case 'TC_Create_DeleteTask' passed");
				return true;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Exception", "Executing 'TC_Create_DeleteTask' TestScript", "Exception while executing 'TC_Create_DeleteTask' method. "+e.getMessage());
			return false;
		}
		finally
		{
			oBrowser = null;
		}
	}
}
