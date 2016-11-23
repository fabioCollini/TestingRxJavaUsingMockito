package it.codingjam.testingrxjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import rx.plugins.RxJavaHooks;
import rx.schedulers.TestScheduler;

public class TestSchedulerRule1 implements TestRule {
    private final TestScheduler testScheduler = new TestScheduler();

    public TestScheduler getTestScheduler() {
        return testScheduler;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaHooks.setOnIOScheduler(scheduler -> testScheduler);
                RxJavaHooks.setOnComputationScheduler(scheduler -> testScheduler);
                RxJavaHooks.setOnNewThreadScheduler(scheduler -> testScheduler);

                try {
                    base.evaluate();
                } finally {
                    RxJavaHooks.reset();
                }
            }
        };
    }
}