package it.codingjam.testingrxjava.gson;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.text.MessageFormat;

@AutoValue
public abstract class User {

    public static User create(int id, int reputation, String name) {
        return new AutoValue_User(id, reputation, name, null);
    }

    public static User create(int id, int reputation, String name, String location) {
        return new AutoValue_User(id, reputation, name, location);
    }

    public static TypeAdapter<User> typeAdapter(Gson gson) {
        return new AutoValue_User.GsonTypeAdapter(gson);
    }

    @SerializedName("user_id")
    public abstract int id();

    public abstract int reputation();

    @SerializedName("display_name")
    public abstract String name();

    @Nullable
    public abstract String location();

    @Override public String toString() {
        if (location() == null) {
            return MessageFormat.format("{0} {1}", reputation(), name());
        } else {
            return MessageFormat.format("{0} {1} ({2})", reputation(), name(), location());
        }
    }
}
