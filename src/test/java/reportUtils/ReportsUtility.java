package reportUtils;

import java.io.*;
import driver.DriverScript;

public class ReportsUtility extends DriverScript{
	public static String resultFileName;
	public static String resultDirectory;
	/*
	 * ********************************************************************
	 * Method Name					: CreateReport(String FileName,String ApplicationName,String URL,String teststarttime)
	 * Created By					: Somashekar
	 * Created Date					: 
	 * Modified Date				:
	 * Parameters					: String FileName,String ApplicationName,String URL,String teststarttime
	 * Purpose							: This method will writes the test cases level results into an html file
	 * ******************************************************************
	 */
	public void CreateReport(String FileName,String ApplicationName,String URL,String teststarttime)
	{
		resultFileName=FileName;
		resultDirectory=resultFileName.substring(0, resultFileName.lastIndexOf("\\"));
		try
		{
			FileWriter fw=new FileWriter(FileName);
			BufferedWriter bw=new BufferedWriter(fw);
			String DATE_START=appInd.getDateTime("dd.MMMM.yyyy hh:mm:ss");
			String ENVIRONMENT="Test Environment";
			bw.write("<html>");
			bw.write("<head>");
			bw.write("<title>Automation Test Results</title>");
			bw.write("</head>");
			bw.write("<body");
			bw.write("<h1 align=center>Automation Test Results</h1>");
			bw.write("<table border=1 cellspacing=1 cellpadding=1>");
			bw.write("<tr>");
			bw.write("<h3><u>Automation Details</u><h3>");
			bw.write("<th width=150 align=center><b>Execution Items</b></th>");
			bw.write("<th width=200 align=center><b>Execution Values</b></th>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td width=150 align=left><b>Date of Execution</b></td>");
			bw.write("<td width=200 align=left><b>"+DATE_START+"</b></td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td width=150 align=left><b>Application Name</b></td>");
			bw.write("<td width=200 align=left><b>"+ApplicationName+"</b></td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td width=150 align=left><b>Application URL</b></td>");
			bw.write("<td width=200 align=left><b>"+URL+"</b></td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td width=150 align=left><b>Environment</b></td>");
			bw.write("<td width=200 align=left><b>"+ENVIRONMENT+"</b></td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td width=150 align=left><b>Test Start Time</b></td>");
			bw.write("<td width=200 align=left><b>"+teststarttime+"</b></td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td width=150 align=left><b>Test End Time</b></td>");
			bw.write("<td width=200 align=left><b>END_TIME</b></td>");
			bw.write("</tr>");
			bw.write("</table>");
			bw.write("</body>");
			bw.write("</html>");
			if (!(bw==null))
			{
				bw.flush();
				bw.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * ********************************************************************
	 * Method Name				: startSuite
	 * Created By				: Somashekar
	 * Created Date				:
	 * Modified Date			:
	 * Parameters				:
	 * Purpose					:
	 * ******************************************************************
	 */
	public void startSuite()
	{
		try
		{
		BufferedWriter bw=new BufferedWriter(new FileWriter(resultFileName,true));
		bw.write("<table border=1 cellspacing=1 cellpadding=1 width=120%>");
		bw.write("<tr>");
		bw.write("<h3><u>Automation Test Results Details</u><h3>");
		bw.write("<th width=3% align=center><b>TestCase_No</b></th>");
		bw.write("<th width=3% align=center><b>Module_Name</b></th>");
		bw.write("<th width=5% align=center><b>TC_MethodName</b></th>");
		bw.write("<th width=2% align=center><b>Status</b></th>");
		bw.write("<th width=2% align=center><b>BrowserName</b></th>");
		bw.write("<th width=2% align=center><b>Total_TimeTaken</b></th>");
		bw.write("</tr>");
		if (!(bw==null))
		{
			bw.flush();
			bw.close();
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * ********************************************************************
	 * Method Name				: endSuite
	 * Created By				: Gudi
	 * Created Date				:
	 * Modified Date			:
	 * Parameters				:
	 * Purpose					:
	 * ******************************************************************
	 */
	public void endSuite()
	{
		BufferedWriter bw = null;
		try
		{
			bw=new BufferedWriter(new FileWriter(resultFileName,true));
			bw.write("</table>");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			
			try {
				if(bw!=null){
					bw.flush();
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/*
	 * ********************************************************************
	 * Method Name				: addTestCase
	 * Created By				: Soamshekar
	 * Created Date				:
	 * Modified Date			:
	 * Parameters				:
	 * Purpose					:
	 * ******************************************************************
	 */
	public void addTestCase(String testcaseno,String modulename,String methodname,String browsername,String status,String strTotalTime)
	{
		try
		{
			BufferedWriter bw=new BufferedWriter(new FileWriter(resultFileName,true));
			bw.write("<tr>");
			bw.write("<td width=3% align=center><b>"+testcaseno+"</b></td>");
			bw.write("<td width=3% align=center><b>"+modulename+"</b></td>");
			bw.write("<td width=5% align=center><b>"+methodname+"</b></td>");
			if(status.equalsIgnoreCase("Fail"))
			{
				bw.write("<td width=2% align=center style=color:red><b>"+status+"</b></td>");
			}else{
				bw.write("<td width=2% align=center><b>"+status+"</b></td>");
			}
			bw.write("<td width=2% align=center><b>"+browsername+"</b></td>");
			bw.write("<td width=2% align=center><b>"+strTotalTime+"</b></td>");
			bw.write("</tr>");
			if (!(bw==null))
			{
				bw.flush();
				bw.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * ********************************************************************
	 * Method Name				: updateEndTime(String endtime)
	 * Created By				: Somashekar 
	 * Created Date				:
	 * Modified Date			:
	 * Parameters				:
	 * Purpose					:
	 * ******************************************************************
	 */
	public void updateEndTime(String endtime)
	{
		BufferedReader br = null;
		DataOutputStream out = null;
		try
		{
		StringBuffer buf=new StringBuffer();
		br =new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(resultFileName))));
		String strLine;
		while((strLine=br.readLine())!=null)
		{
			if(strLine.indexOf("END_TIME")!=-1)
			{
				strLine=strLine.replace("END_TIME", endtime);
				
			}
			buf.append(strLine);
		}
		out =new DataOutputStream(new FileOutputStream(resultFileName));
		out.writeBytes(buf.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			try{
			if(br!=null){
				br.close();
			}
			if(out!=null){
				out.close();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}