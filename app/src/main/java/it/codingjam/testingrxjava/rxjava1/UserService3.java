package it.codingjam.testingrxjava.rxjava1;

import it.codingjam.testingrxjava.UserStats;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

public class UserService3 {
    private StackOverflowService service;

    public UserService3(StackOverflowService service) {
        this.service = service;
    }

    public Observable<List<UserStats>> loadUsers() {
        return service.getTopUsers()
                .flatMapIterable(UserResponse::items)
                .limit(5)
                .flatMap(this::loadUserStats)
                .toSortedList((u1, u2) ->
                        u2.reputation() - u1.reputation())
                .retry(1);
    }

    private Observable<UserStats> loadUserStats(User user) {
        return service.getBadges(user.id())
                .subscribeOn(Schedulers.io())
                .map(BadgeResponse::items)
                .map(badges -> UserStats.create(user, badges));
    }
}