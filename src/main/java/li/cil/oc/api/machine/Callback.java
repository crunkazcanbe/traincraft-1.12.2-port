package li.cil.oc.api.machine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Callback {
    String value() default "";
    boolean direct() default false;
    boolean getter() default false;
    boolean setter() default false;
    String doc() default "";
}
