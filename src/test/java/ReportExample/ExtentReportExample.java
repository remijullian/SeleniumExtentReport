package ReportExample;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportExample {
	ExtentSparkReporter reporter;
	ExtentReports reports;
	ExtentTest logger;
	WebDriver driver;
	
	@BeforeTest
	public void startTest() {
		
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-ms");
		
		//String path=System.getProperty("user.dir")+"/extent-reports/"+sdf.format(new Date())+".html";
		String path=System.getProperty("user.dir")+"/extent-reports/report.html";
		reporter = new ExtentSparkReporter(path);
		reports=new ExtentReports();
		reports.attachReporter(reporter);
		reports.setSystemInfo("Hostname", "Localhost");
		reports.setSystemInfo("Environment","Testing Environment");
		reports.setSystemInfo("Username","Remi");
		
		reporter.config().setDocumentTitle("Remi's HTML Report");
		reporter.config().setReportName("Testing Report");
		reporter.config().setTheme(Theme.DARK);
		
	}
	
	@AfterTest
	public void endTest() {
		reports.flush();// Used to save your extent report
	}
	
	@Test
	public void passTest() {
		logger = reports.createTest("Pass Test");
		Assert.assertTrue(true);
	}
	
	@Test
	public void failTest() {
		logger = reports.createTest("Fail Test");
		Assert.assertTrue(false);
	}
	
	@Test
	public void skipTest() {
		logger = reports.createTest("Skip Test");
		throw new SkipException("Some reason");
	}
	
	@AfterMethod
	public void captureStatus(ITestResult result){
		
		if(result.getStatus()==ITestResult.SUCCESS) {
			logger.log(Status.PASS, result.getMethod().getMethodName()+" is PASSED");
		}else if(result.getStatus()==ITestResult.FAILURE) {
			logger.log(Status.FAIL, result.getMethod().getMethodName()+" is FAILED");
		}else if (result.getStatus()==ITestResult.SKIP) {
			logger.log(Status.SKIP, result.getMethod().getMethodName()+" is SKIPPED");
		}
		
	}
}
