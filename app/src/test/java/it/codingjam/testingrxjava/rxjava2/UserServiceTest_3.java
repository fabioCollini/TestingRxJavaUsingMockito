package it.codingjam.testingrxjava.rxjava2;

import it.codingjam.testingrxjava.ImmediateSchedulerRule;
import it.codingjam.testingrxjava.gson.Badge;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static io.reactivex.Single.just;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class UserServiceTest_3 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Rule public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserService userService;

    @Test public void testSubscribe() {
        when(stackOverflowService.getTopUsers()).thenReturn(
                just(UserResponse.create(User.create(1, 10, "user 1")))
        );
        when(stackOverflowService.getBadges(anyInt())).thenReturn(
                just(BadgeResponse.create(Badge.create("badge")))
        );

        //List<UserStats> l = userService.loadUsers().blockingGet();
        //assertThat(l).hasSize(1);

        userService.loadUsers().test()
                .assertNoErrors()
                .assertValue(l -> l.size() == 1);
    }
}