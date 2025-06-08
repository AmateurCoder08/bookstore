package bookstore.api.automation.reports;

public final class ReportLogging {
	private ReportLogging() {

	}

	public static void pass(String message) {
		ExtentFactory.getInstance().getExtentTest().pass(message);
	}

	public static void fail(String message) {
		ExtentFactory.getInstance().getExtentTest().fail(message);
	}
	
	public static void info(String message) {
		ExtentFactory.getInstance().getExtentTest().info(message);
	}
}
