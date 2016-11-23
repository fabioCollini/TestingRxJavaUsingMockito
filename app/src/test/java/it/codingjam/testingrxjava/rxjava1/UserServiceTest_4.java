package it.codingjam.testingrxjava.rxjava1;

import it.codingjam.testingrxjava.UserStats;
import it.codingjam.testingrxjava.gson.Badge;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

public class UserServiceTest_4 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserService userService;

    @Test public void testSubscribe() {
        when(stackOverflowService.getTopUsers())
                .thenReturn(just(UserResponse.create(
                        User.create(1, 100, "user 1"),
                        User.create(2, 100, "user 2")
                )));
        when(stackOverflowService.getBadges(eq(1))).thenReturn(
                just(BadgeResponse.create(Badge.create("badge1")))
                        .delay(2, TimeUnit.SECONDS));
        when(stackOverflowService.getBadges(eq(2))).thenReturn(
                just(BadgeResponse.create(Badge.create("badge2")))
                        .delay(1, TimeUnit.SECONDS));

        TestSubscriber<List<UserStats>> subscriber = new TestSubscriber<>();
        userService.loadUsers().subscribe(subscriber);

        subscriber.awaitTerminalEvent();

        Assertions.assertThat(subscriber.getOnNextEvents().get(0))
                .hasSize(2)
                .extracting(UserStats::id)
                .containsExactly(1, 2);
    }
}