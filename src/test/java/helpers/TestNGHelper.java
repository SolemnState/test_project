package helpers;

import org.testng.ITest;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public interface TestNGHelper extends ITest {
    ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod
    public default void beforeMethod(Method method, Object[] testData){
        testName.set((String) testData[0]);
        //testName.set(method.getName() + "_" + testData[0]);
    }
    @Override
    public default String getTestName() {
        return testName.get();
    }
}
