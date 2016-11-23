package it.codingjam.testingrxjava;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ImmediateSchedulerRule implements TestRule {

    private final Scheduler immediate = Schedulers.trampoline();
    //        new Scheduler() {
    //    @Override public Worker createWorker() {
    //        return new ExecutorScheduler.ExecutorWorker(Runnable::run);
    //    }
    //};

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> immediate);
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> immediate);
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> immediate);
                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> immediate);

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}