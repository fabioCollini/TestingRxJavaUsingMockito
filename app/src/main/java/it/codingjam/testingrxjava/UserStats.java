package it.codingjam.testingrxjava;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import it.codingjam.testingrxjava.gson.Badge;
import it.codingjam.testingrxjava.gson.User;
import java.text.MessageFormat;
import java.util.List;

@AutoValue
public abstract class UserStats {

    public static UserStats create(User user, List<Badge> badges) {
        return new AutoValue_UserStats(user, badges);
    }

    public static TypeAdapter<UserStats> typeAdapter(Gson gson) {
        return new AutoValue_UserStats.GsonTypeAdapter(gson);
    }

    public abstract User user();

    public abstract List<Badge> badges();

    public int id() {
        return user().id();
    }

    public int reputation() {
        return user().reputation();
    }

    @Override public String toString() {
        return MessageFormat.format("{0}\n{1}", user().toString(), listToString(badges()));
    }

    private String listToString(List<?> l) {
        String s = l.toString();
        return s.substring(1, s.length() - 1);
    }
}
