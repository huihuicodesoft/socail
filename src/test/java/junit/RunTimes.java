package junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Hui on 2017/6/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface RunTimes {
    /**
     * 测试用例执行次数，默认为1
     *
     * @return
     */
    int value() default 1;
}