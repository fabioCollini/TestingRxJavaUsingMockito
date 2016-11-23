package it.codingjam.testingrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.codingjam.testingrxjava.rxjava2.StackOverflowService;
import it.codingjam.testingrxjava.rxjava2.StackOverflowServiceFactory;
import it.codingjam.testingrxjava.rxjava2.UserService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StackOverflowService service = StackOverflowServiceFactory.createService();

        new UserService(service)
                .loadUsers()
                .flattenAsObservable(l -> l)
                .reduce("", (s, userStats) -> s + "\n\n" + userStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> ((TextView) findViewById(R.id.text)).setText(s),
                        Throwable::printStackTrace
                );
    }
}
