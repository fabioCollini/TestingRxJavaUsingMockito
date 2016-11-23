package it.codingjam.testingrxjava.rxjava2;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import it.codingjam.testingrxjava.UserStats;
import it.codingjam.testingrxjava.gson.Badge;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static io.reactivex.Single.just;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class UserServiceTest_4 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserService userService;

    @Test public void testSubscribe() {
        when(stackOverflowService.getTopUsers()).thenReturn(
                just(UserResponse.create(
                        User.create(1, 100, "user 1"),
                        User.create(2, 100, "user 2")
                ))
        );
        when(stackOverflowService.getBadges(anyInt())).thenReturn(
                just(BadgeResponse.create(Badge.create("badge"))));

        TestObserver<List<UserStats>> testObserver = userService.loadUsers()
                .test();
        testObserver.awaitTerminalEvent();

        testObserver
                .assertNoErrors()
                .assertValue(l -> l.size() == 2)
                .assertValue(l ->
                        Observable.fromIterable(l)
                                .map(UserStats::id)
                                .toList()
                                .blockingGet()
                                .equals(Arrays.asList(1, 2))
                );
    }
}