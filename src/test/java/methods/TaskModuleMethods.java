package methods;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import driver.DriverScript;

public class TaskModuleMethods extends DriverScript{
	/***************************************
	 * Method Name	: importTask
	 * Purpose		: to import the tasks from the file
	 * 
	 * 
	 * *************************************
	 */
	public boolean importTask(WebDriver oDriver, String strLogicalName)
	{
		String strStatus = null;
		Map<String, String> oData = null;
		try {
			oData = datatable.getDataFromExcel("importTask", strLogicalName);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_Tasks_Tab"));
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_AddNewTask_Btn"));
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_ImportTaskFromCSV_Link"));
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_DropZone_Ele"));
			Thread.sleep(3000);
			strStatus+=String.valueOf(appInd.handleUploadWindow(oData.get("TD_FileName")));
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_CompleteImport_Btn"));
			Thread.sleep(2000);
			if(appInd.elementPresent(oDriver, By.xpath("(//div[contains(@class,'successfully')]/div/div)[1]")))
			{
				strStatus+=String.valueOf(true);
			}else {
				strStatus+=String.valueOf(false);
			}
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_CloseImportWindow_Btn"));
			
			if(strStatus.contains("false"))
			{
				datatable.writeResult("Fail", "Executing 'importTask' method", "Failed to import the task");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'importTask' method", "Import task was successful");
				return true;
			}
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'createTask' method", "Exception while executing 'createTask' method. "+e.getMessage());
			return false;
		}
	}
	
	
	
	/***************************************
	 * Method Name	: deleteTask
	 * Purpose		: to delete the tasks
	 * 
	 * 
	 * *************************************
	 */
	public boolean deleteTask(WebDriver oDriver)
	{
		String strStatus = null;
		try {
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_DeleteTask_Btn"));
			Thread.sleep(1000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_ACTIONS_Btn"));
			Thread.sleep(1000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_Delete_Btn"));
			Thread.sleep(1000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_DeletePermanently_Btn"));
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_Delete_Cust_Btn"));
			Thread.sleep(1000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_ACTIONS_Cust_Btn"));
			Thread.sleep(1000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_Delete_Cust_Link"));
			Thread.sleep(1000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_DeletePermanently_Btn"));
			
			if(strStatus.contains("false"))
			{
				datatable.writeResult("Fail", "Executing 'deleteTask' method", "Failed to delete the task");
				return false;
			}else {
				datatable.writeResult("Pass", "Executing 'deleteTask' method", "Delete task was successful");
				return true;
			}
			
		}catch(Exception e)
		{
			datatable.writeResult("Fail", "Executing 'deleteTask' method", "Exception while executing 'deleteTask' method. "+e.getMessage());
			return false;
		}
	}
}
