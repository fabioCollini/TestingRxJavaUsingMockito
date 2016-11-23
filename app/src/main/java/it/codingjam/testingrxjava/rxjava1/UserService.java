package it.codingjam.testingrxjava.rxjava1;

import it.codingjam.testingrxjava.UserStats;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.User;
import it.codingjam.testingrxjava.gson.UserResponse;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

public class UserService {
    private StackOverflowService service;

    public UserService(StackOverflowService service) {
        this.service = service;
    }

    public Observable<List<UserStats>> loadUsers() {
        return service.getTopUsers()
                .flatMapIterable(UserResponse::items)
                .limit(5)
                .flatMap(this::loadUserStats)
                .toList();
    }

    private Observable<UserStats> loadUserStats(User user) {
        return service.getBadges(user.id())
                .subscribeOn(Schedulers.io())
                .map(BadgeResponse::items)
                .map(badges -> UserStats.create(user, badges));
    }
}