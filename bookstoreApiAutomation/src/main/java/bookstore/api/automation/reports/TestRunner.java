package bookstore.api.automation.reports;

public class TestRunner {
    public static void main(String[] args) {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();

            // Get singleton ExtentFactory
            ExtentFactory1 factory = ExtentFactory1.getInstance();

            // Set a unique ExtentTest object for each thread
            factory.setExtentTest("Test for " + threadName);

            // Retrieve and print the thread's own ExtentTest object
            System.out.println(threadName + ": " + factory.getExtentTest());
        };

        // Start 3 threads
        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");
        Thread t3 = new Thread(task, "Thread-C");

        t1.start();
        t2.start();
        t3.start();
    }
}

class ExtentTest {
    private final String testName;

    public ExtentTest(String testName) {
        this.testName = testName;
    }

    @Override
    public String toString() {
        return "ExtentTest{" + "testName='" + testName + '\'' + ", hash=" + this.hashCode() + '}';
    }
}

class ExtentFactory1 {
    // Singleton pattern
    private static volatile ExtentFactory1 factoryInstance;

    public static ExtentFactory1 getInstance() {
        if (factoryInstance == null) {
            synchronized (ExtentFactory1.class) {
                if (factoryInstance == null) {
                    factoryInstance = new ExtentFactory1();
                }
            }
        }
        return factoryInstance;
    }

    // ThreadLocal for per-thread test instance
//    private ThreadLocal<ExtentTest> threadSafeExtentTest = new ThreadLocal<>();
//
//    public void setExtentTest(ExtentTest test) {
//        threadSafeExtentTest.set(test);
//    }
//
//    public ExtentTest getExtentTest() {
//        return threadSafeExtentTest.get();
//    }
    
    ExtentTest test;
    
    public void setExtentTest(String name) {
    	test = new ExtentTest(name);
    }
    
    public ExtentTest getExtentTest() {
    	return test;
    }
}

