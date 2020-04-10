package novel.api.types.annotations;

import novel.internal.reflective.ReflectiveObjectReader;
import novel.internal.reflective.ReflectiveObjectWriter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * This annotation is used to bypass the null check in
 * {@link ReflectiveObjectReader} and {@link ReflectiveObjectWriter}
 * This will save 1 boolean of data per field!
 * However, if an instance of the annotated {@link java.lang.reflect.Field} is null,
 * serialization will <b>fail</b>.
 * Primitives do not need this annotation and are inherently treated as {@link Inked}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, METHOD, PARAMETER, LOCAL_VARIABLE, RECORD_COMPONENT})
public @interface Inked {
    String description() default "";
}
