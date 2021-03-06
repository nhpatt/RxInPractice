package com.nhpatt.rxjava.koans;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class RxSchedulerKoans {

    private static final Scheduler _____ = null;
    private GitHubService service;
    private TestObserver<Repo> testObserver;
    private String ____;

    @Before
    public void setUp() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(GitHubService.class);

        testObserver = new TestObserver();
    }

    @Test
    public void schedulersAllowControllingTheThread() {

        List<Repo> onNextEvents = service.listRepos(____)
                .subscribeOn(_____)
                .observeOn(Schedulers.io())
                .flatMap(Observable::fromIterable)
                .toList().blockingGet();

        assertThat(onNextEvents, is(not(empty())));
    }
}
