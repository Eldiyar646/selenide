package test.utils;

import org.junit.jupiter.api.Tag;
import test.Tags;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestRailCaseId {
    String value();
}
