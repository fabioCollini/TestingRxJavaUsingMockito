package it.codingjam.testingrxjava.rxjava1;

import it.codingjam.testingrxjava.UserStats;
import it.codingjam.testingrxjava.gson.Badge;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import rx.observers.TestSubscriber;

import static br.ufs.github.rxassertions.RxAssertions.assertThat;
import static it.codingjam.testingrxjava.ConditionUtils.condition;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

public class UserServiceTest_3 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserService userService;

    @Test public void testSubscribe() {
        when(stackOverflowService.getTopUsers())
                .thenReturn(just(UserResponse.create(
                        User.create(1, 100, "user 1"),
                        User.create(2, 100, "user 2")
                )));
        when(stackOverflowService.getBadges(anyInt()))
                .thenReturn(just(BadgeResponse.create(Badge.create("badge"))));

        assertThat(userService.loadUsers())
                .withoutErrors()
                .emissionsCount(1)
                .eachItemMatches(condition(v -> v.size() == 2))
                .completes();

        TestSubscriber<List<UserStats>> subscriber = new TestSubscriber<>();
        userService.loadUsers().subscribe(subscriber);

        subscriber.awaitTerminalEvent();

        Assertions.assertThat(subscriber.getOnNextEvents().get(0))
                .hasSize(2)
                .extracting(UserStats::id)
                .containsExactly(1, 2);
    }
}