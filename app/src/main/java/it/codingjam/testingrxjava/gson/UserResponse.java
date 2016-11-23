package it.codingjam.testingrxjava.gson;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.Arrays;
import java.util.List;

@AutoValue
public abstract class UserResponse {
    public static UserResponse create(List<User> items) {
        return new AutoValue_UserResponse(items);
    }

    public static UserResponse create(User... items) {
        return new AutoValue_UserResponse(Arrays.asList(items));
    }

    public static TypeAdapter<UserResponse> typeAdapter(Gson gson) {
        return new AutoValue_UserResponse.GsonTypeAdapter(gson);
    }

    public abstract List<User> items();
}
