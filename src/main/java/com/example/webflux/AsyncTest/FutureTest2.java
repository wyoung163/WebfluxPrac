package com.example.webflux.AsyncTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Executor - submit **/
public class FutureTest2 {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(() -> {
            Thread.sleep(2000);

            System.out.println("Async");
            return "Inside";
        });
        System.out.println("Outside");
    }
}

/**
 * 실행 결과
 * > Task :FutureTest2.main()
 * Outside
 * Async
 * **/