package bookstore.api.automation.reports;

import java.util.Objects;

import com.aventstack.extentreports.ExtentTest;

public class ExtentFactory {
	private ExtentFactory() {
		
	}
	
	private static volatile ExtentFactory factoryInstance;

	public static ExtentFactory getInstance() {
		if (Objects.isNull(factoryInstance)) {
			synchronized (ExtentFactory.class) {
				if (Objects.isNull(factoryInstance)) {
					factoryInstance = new ExtentFactory();
				}
			}
		}

		return factoryInstance;
	}
	
	private static ThreadLocal<ExtentTest> threadSafeExtentTest = new ThreadLocal<ExtentTest>();
	
	public ExtentTest getExtentTest() {
		return threadSafeExtentTest.get();
	}
	
	public void setExtentTest(ExtentTest test) {
		threadSafeExtentTest.set(test);
	}
	
	public void removeExtentTest() {
		threadSafeExtentTest.remove();
	}
}
