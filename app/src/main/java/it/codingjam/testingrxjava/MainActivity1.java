package it.codingjam.testingrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import it.codingjam.testingrxjava.rxjava1.StackOverflowService;
import it.codingjam.testingrxjava.rxjava1.StackOverflowServiceFactory;
import it.codingjam.testingrxjava.rxjava1.UserService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StackOverflowService service = StackOverflowServiceFactory.createService();

        new UserService(service)
                .loadUsers()
                .flatMapIterable(l -> l)
                .reduce("", (s, userStats) -> s + "\n\n" + userStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> ((TextView) findViewById(R.id.text)).setText(s),
                        Throwable::printStackTrace
                );
    }
}
