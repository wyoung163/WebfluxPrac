package com.example.webflux.AsyncTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** java future **/
public class FutureTest3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        Future<String> f = es.submit(() -> {
            Thread.sleep(2000); //interrupt 발생 시 exception 던질 수 있도록
            System.out.println("Async");
            return "Inside";
        });
        System.out.println(f.isDone()); // 즉시 완료되었는가
        Thread.sleep(2100);
        System.out.println("Outside");
        System.out.println(f.isDone());
        System.out.println(f.get());
    }
}

/**
 * 실행 결과
 * > Task :FutureTest3.main()
 * false
 * Async
 * Outside
 * true
 * Inside
 *
 * (f(future)가 es의 return 값 저장하는 객체)
 * Outside가 Async보다 더 늦게 출력되게 만든 이유는
 * f.get() 메서드가 결과값을 받을 때까지 대기하는 blocking 메소드이기 때문
 * **/