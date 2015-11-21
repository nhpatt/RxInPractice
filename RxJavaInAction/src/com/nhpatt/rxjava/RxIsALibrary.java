package com.nhpatt.rxjava;

import org.junit.Before;
import org.junit.Test;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

public class RxIsALibrary {

    private Retrofit retrofit;
    private GitHubService service;

    @Before
    public void setUp() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(GitHubService.class);
    }

    @Test
    public void anObservableEmitsThings() {
        Observable.just("Hi!")
                .subscribe(System.out::println, thr -> System.err.println(thr.getMessage()), () -> System.out.println("finished!"));
    }

    @Test
    public void anObservableEmitsSeveralThings() {
        List<String> severalThings = Arrays.asList("1", "2");

        Observable.from(severalThings)
                .subscribe(System.out::println, thr -> System.err.println(thr.getMessage()), () -> System.out.println("finished!"));

    }

    @Test
    public void aNetworkCallIsAnObservable() {

        service.listRepos("nhpatt")
                .subscribe(System.out::println);
    }

    @Test
    public void mapTransformsEachElement() {
        List<String> severalThings = Arrays.asList("1", "2");

        Observable.from(severalThings)
                .map(Integer::valueOf)
                .subscribe(System.out::println);

    }

    @Test
    public void mapDoesNotWorkWellWithLists() {

        service.listRepos("nhpatt")
                .map(Observable::from)
                .subscribe(System.out::println);
    }

    @Test
    public void flatmapCanReturnElementsFromAnObservable() {

        service.listRepos("nhpatt")
                .flatMap(Observable::from)
                .map(Repo::getName)
                .map((s) -> s.replace("-", " "))
                .subscribe(System.out::println);
    }

    @Test
    public void filteringResults() {

        service.listRepos("nhpatt")
                .flatMap(Observable::from)
                .map(Repo::getName)
                .map((s) -> s.replace("-", " "))
                .filter((s) -> s.startsWith("Android"))
                .take(2)
                .subscribe(System.out::println);
    }

    @Test
    public void accumulatingResults() {

        service.listRepos("nhpatt")
                .flatMap(Observable::from)
                .map(Repo::getName)
                .map((s) -> s.replace("-", " "))
                .filter((s) -> s.startsWith("Android"))
                .take(2)
                .map(String::length)
                .scan((x, y) -> x * y)
                .subscribe(System.out::println);
    }

}
