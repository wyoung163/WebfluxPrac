package com.example.webflux.AsyncTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Executor - execute **/
public class FutureTest{
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(() -> {
            try {
                Thread.sleep(2000); // interrupt 발생 시 execption 던질 수 있도록
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Inside");
        });
        System.out.println("Outside");
    }
}

/**
 * 실행 결과
 * > Task :FutureTest.main()
 * Outside
 * Inside
 * **/