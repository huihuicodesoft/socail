package junit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 自定义Runner，主要处理RunTimes注解的逻辑
 * Created by Hui on 2017/6/23.
 */
public class CustomRunner extends SpringJUnit4ClassRunner {

    public CustomRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        RunTimes annotation = method.getAnnotation(RunTimes.class);
        if (annotation == null) {
            super.runChild(method, notifier);
        } else {
            int runTimes = annotation.value();
            for (int i = 0; i < runTimes; i++) {
                super.runChild(method, notifier);
            }
        }
    }
}
