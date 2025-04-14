package bookstore.api.automation.constants;

import java.io.File;

public final class PathsAndDirectories {
	
	private PathsAndDirectories() {
		
	}
	
	private static final String PROJECT_PATH = System.getProperty("user.dir");
	
	private static final String EXTENT_REPORT_PATH = PROJECT_PATH + File.separator + "ExtentReports" + File.separator + "BookstoreAPITestingReport.html";
	
	public static String getExtentReportPath() {
		return EXTENT_REPORT_PATH;
	}

}
