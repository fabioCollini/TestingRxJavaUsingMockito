package it.codingjam.testingrxjava.rxjava1;

import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.UserResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface StackOverflowService {

    @GET("/users") Observable<UserResponse> getTopUsers();

    @GET("/users/{userId}/badges") Observable<BadgeResponse> getBadges(@Path("userId") int userId);
}
