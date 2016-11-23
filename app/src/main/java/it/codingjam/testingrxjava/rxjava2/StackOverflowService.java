package it.codingjam.testingrxjava.rxjava2;

import io.reactivex.Single;
import it.codingjam.testingrxjava.gson.BadgeResponse;
import it.codingjam.testingrxjava.gson.UserResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StackOverflowService {

    @GET("/users") Single<UserResponse> getTopUsers();

    @GET("/users/{userId}/badges") Single<BadgeResponse> getBadges(@Path("userId") int userId);
}
