package bookstore.api.automation.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import bookstore.api.automation.constants.PathsAndDirectories;

public final class ExtentReportManager {
	
	private ExtentReportManager() {
		
	}
	
	public static ExtentReports setUpExtentReport() {
		ExtentSparkReporter reporter = new ExtentSparkReporter(PathsAndDirectories.getExtentReportPath());
		reporter.config().setDocumentTitle("Bookstore API Test Report");
		reporter.config().setReportName("API automation test report");
		ExtentReports report = new ExtentReports();
		report.attachReporter(reporter);
		
		return report;
	}

}
