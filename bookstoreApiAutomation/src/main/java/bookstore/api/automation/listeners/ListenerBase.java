package bookstore.api.automation.listeners;

import java.io.File;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ListenerBase implements ITestListener {
	
	ExtentReports report;
	ExtentSparkReporter sparkReporter;
	ExtentTest test;
	

	@Override
	public void onTestStart(ITestResult result) {
		ITestListener.super.onTestStart(result);
		test = report.createTest(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.pass(result.getName() + " test Passed");		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.fail(result.getName() + " test failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		ITestListener.super.onStart(context);
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "ExtentReports" + File.separator + "BookstoreAPITestingReport.html");
		sparkReporter.config().setDocumentTitle("Bookstore API Test Report");
		sparkReporter.config().setReportName("API automation test report");
		report = new ExtentReports();
		report.attachReporter(sparkReporter);
	}

	@Override
	public void onFinish(ITestContext context) {
		ITestListener.super.onFinish(context);
		report.flush();
	}
	
}
