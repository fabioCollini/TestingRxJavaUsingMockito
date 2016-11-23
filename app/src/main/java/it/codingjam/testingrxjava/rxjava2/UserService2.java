package it.codingjam.testingrxjava.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import it.codingjam.testingrxjava.UserStats;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import java.util.List;

public class UserService2 {
    private StackOverflowService service;

    public UserService2(StackOverflowService service) {
        this.service = service;
    }

    public Single<List<UserStats>> loadUsers() {
        return service.getTopUsers()
                .flattenAsObservable(UserResponse::items)
                .take(5)
                .concatMap(this::loadUserStats)
                .toList();
    }

    private Observable<UserStats> loadUserStats(User user) {
        return service.getBadges(user.id())
                .subscribeOn(Schedulers.io())
                .map(BadgeResponse::items)
                .map(badges -> UserStats.create(user, badges))
                .toObservable();
    }
}