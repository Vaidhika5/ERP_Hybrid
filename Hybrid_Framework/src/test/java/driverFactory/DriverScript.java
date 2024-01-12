package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
public WebDriver driver;
String inputpath = "./FileInput//Controller.xlsx";
String outputpath = "./FileOutput/HybridResults.xlsx";
ExtentReports reports;
ExtentTest logger;
String TestCases = "MasterTestCases";

public void startTest() throws Throwable
{
	String Module_Status = "";
	//create object for ExcelFileUtil class
	ExcelFileUtil xl= new ExcelFileUtil(inputpath);
	//iterate all rows in testcase sheet
	for(int i=1; i<=xl.rowCount(TestCases);i++)
	{
		//read cell
		String Execution_Status = xl.getCellData(TestCases, i, 2);
		
		if(Execution_Status.equalsIgnoreCase("Y"))
		{
			
		//store all modules into variable
			String TCModule = xl.getCellData(TestCases, i, 1);
			reports = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
					logger = reports.startTest(TCModule);
			//iterate all rows in TCModule
			for(int j=1; j<=xl.rowCount(TCModule);j++)
			{
				//read all cells from TCModule
				String Description = xl.getCellData(TCModule, j, 0);
				String Object_Type = xl.getCellData(TCModule, j, 1);
				String Locator_Type = xl.getCellData(TCModule, j, 2);
				String Locator_Value = xl.getCellData(TCModule, j, 3);
				String Test_Data = xl.getCellData(TCModule, j, 4);
				try {
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver = FunctionLibrary.startBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("openUrl"))
					{
						 FunctionLibrary.openUrl(driver);
						 logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						 FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
						 logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closebrowser(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("mouseClick"))
					{
						FunctionLibrary.mouseClick(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("categoryTable"))
					{
						FunctionLibrary.categoryTable(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureStock"))
					{
						FunctionLibrary.captureStock(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureSupplier"))
					{
						FunctionLibrary.captureSupplier(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable(driver);
						logger.log(LogStatus.INFO, Description);
					}
					//write as pass into TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					logger.log(LogStatus.PASS, Description);
					Module_Status= "True";
					
				}catch(Exception e)
				{
					//write as Fail into TCModule
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					logger.log(LogStatus.FAIL, Description);
					Module_Status = "False";
					
				}
				if(Module_Status.equalsIgnoreCase("true"))
				{
					//write as pass into testcases
					xl.setCellData(TestCases, i, 3, "Pass", outputpath);
				}
				if(Module_Status.equalsIgnoreCase("False"))
				{
				//write as fail into testcases
				xl.setCellData(TestCases, i, 3, "Fail", outputpath);
				}
				reports.endTest(logger);
				reports.flush();
			}
		}
		else
		{
			//write as blocked into TestCase sheet for flag N
			 xl.setCellData(TestCases, i, 3, "Blocked", outputpath);
			 
		}
	}
}

}
