package it.codingjam.testingrxjava.rxjava1;

import it.codingjam.testingrxjava.UserStats;
import it.codingjam.testingrxjava.gson.Badge;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import rx.observers.TestSubscriber;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

public class UserServiceTest_1 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserService userService;

    private TestSubscriber<List<UserStats>> testSubscriber = new TestSubscriber<>();

    @Test public void testSubscribe() {
        when(stackOverflowService.getTopUsers())
                .thenReturn(just(UserResponse.create(singletonList(User.create(1, 10, "user 1", null)))));
        when(stackOverflowService.getBadges(anyInt()))
                .thenReturn(just(BadgeResponse.create(singletonList(Badge.create("badge")))));

        userService.loadUsers().subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        assertThat(testSubscriber.getOnNextEvents().get(0)).hasSize(1);
        testSubscriber.assertCompleted();
    }
}