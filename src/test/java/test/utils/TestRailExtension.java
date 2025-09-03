package test.utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.lang.reflect.Method;
import java.util.Optional;

public class TestRailExtension implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        sendResult(context, 1, "Test passed successfully");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        sendResult(context, 5, "Test failed: " + cause.getMessage());
    }

    private void sendResult(ExtensionContext context, int statusId, String comment) {
        Optional<Method> testMethod = context.getTestMethod();
        if (testMethod.isPresent()) {
            TestRailCaseId annotation = testMethod.get().getAnnotation(TestRailCaseId.class);
            if (annotation != null) {
                String caseId = annotation.value().replace("C", "");
                TestRailReporter.updateResult(TestRailReporter.RUN_ID, caseId, statusId, comment);
            }
        }
    }
}
