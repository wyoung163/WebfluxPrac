package com.example.webflux.AsyncTest;

import javax.crypto.spec.PSource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTest4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        FutureTask<String> f = new FutureTask<>(() -> {
            Thread.sleep(2000);
            System.out.println("Async");
            return "Inside";
        });
        es.execute(f);

        System.out.println(f.isDone());
        Thread.sleep(2100);
        System.out.println("Exit");
        System.out.println(f.isDone());
        System.out.println(f.get());
    }
}

/**
 * 실행 결과
 * > Task :FutureTest4.main()
 * false
 * Async
 * Exit
 * true
 * Inside
 * **/