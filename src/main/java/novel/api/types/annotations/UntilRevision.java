package novel.api.types.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;

@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, RECORD_COMPONENT})
public @interface UntilRevision {
    int value() default 0;
}