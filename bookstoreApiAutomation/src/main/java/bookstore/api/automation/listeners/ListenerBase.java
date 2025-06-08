package bookstore.api.automation.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import bookstore.api.automation.reports.ExtentFactory;
import bookstore.api.automation.reports.ExtentReportManager;

public class ListenerBase implements ITestListener {
	
	ExtentReports report;
	ExtentTest test;
	

	@Override
	public void onTestStart(ITestResult result) {
		ITestListener.super.onTestStart(result);
		String testTitle = result.getMethod().getDescription();
		ExtentFactory.getInstance().setExtentTest(report.createTest(testTitle));
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentFactory.getInstance().getExtentTest().pass(result.getName() + " test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentFactory.getInstance().getExtentTest().fail(result.getName() + " test failed");		
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
		report = ExtentReportManager.setUpExtentReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		ITestListener.super.onFinish(context);
		report.flush();
	}
	
}
