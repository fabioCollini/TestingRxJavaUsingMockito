package it.codingjam.testingrxjava.gson;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Badge {

    public static Badge create(String name) {
        return new AutoValue_Badge(name);
    }

    public static TypeAdapter<Badge> typeAdapter(Gson gson) {
        return new AutoValue_Badge.GsonTypeAdapter(gson);
    }

    public abstract String name();

    @Override public String toString() {
        return name();
    }
}
