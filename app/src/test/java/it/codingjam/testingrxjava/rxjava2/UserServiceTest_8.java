package it.codingjam.testingrxjava.rxjava2;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import it.codingjam.testingrxjava.TestSchedulerRule;
import it.codingjam.testingrxjava.UserStats;
import it.codingjam.testingrxjava.gson.Badge;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import java.util.List;
import java.util.concurrent.Callable;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static io.reactivex.Single.just;
import static it.codingjam.testingrxjava.PredicateUtils.check;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class UserServiceTest_8 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Rule public TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserService3 userService;

    @Test public void testErrorOnBadge() {
        when(stackOverflowService.getTopUsers()).thenReturn(
                just(UserResponse.create(
                        User.create(1, 200, "user 1"),
                        User.create(2, 100, "user 2")
                ))
        );
        when(stackOverflowService.getBadges(eq(1)))
                .thenThrow(new RuntimeException(":("))
                .thenReturn(
                        just(BadgeResponse.create(Badge.create("badge1")))
                );
        when(stackOverflowService.getBadges(eq(2))).thenReturn(
                just(BadgeResponse.create(Badge.create("badge2")))
        );

        TestObserver<List<UserStats>> testObserver = userService.loadUsers().test();
        testSchedulerRule.getTestScheduler().triggerActions();

        testObserver
                .assertNoErrors()
                .assertValue(check(
                        l -> assertThat(l)
                                .extracting(UserStats::id)
                                .containsExactly(1, 2)
                ));
    }

    @Test public void testErrorOnTopUsers() {
        when(stackOverflowService.getTopUsers())
                .thenReturn(Single.fromCallable(new Callable<UserResponse>() {
                    private boolean firstEmitted;

                    @Override public UserResponse call() throws Exception {
                        if (!firstEmitted) {
                            firstEmitted = true;
                            throw new RuntimeException(":(");
                        } else {
                            return UserResponse.create(
                                    User.create(1, 200, "user 1"),
                                    User.create(2, 100, "user 2")
                            );
                        }
                    }
                }));
        when(stackOverflowService.getBadges(eq(1))).thenReturn(
                just(BadgeResponse.create(Badge.create("badge1")))
        );
        when(stackOverflowService.getBadges(eq(2))).thenReturn(
                just(BadgeResponse.create(Badge.create("badge2")))
        );

        TestObserver<List<UserStats>> testObserver = userService.loadUsers().test();
        testSchedulerRule.getTestScheduler().triggerActions();

        testObserver
                .assertNoErrors()
                .assertValue(check(
                        l -> assertThat(l)
                                .extracting(UserStats::id)
                                .containsExactly(1, 2)
                ));
    }
}