package it.codingjam.testingrxjava.rxjava1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.codingjam.testingrxjava.gson.MyAdapterFactory;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StackOverflowServiceFactory {
    public static StackOverflowService createService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("site", "stackoverflow")
                            .addQueryParameter("key", "fruiv4j48P0HjSJ8t7a8Gg((")
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.stackexchange.com/2.2/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(StackOverflowService.class);
    }
}
