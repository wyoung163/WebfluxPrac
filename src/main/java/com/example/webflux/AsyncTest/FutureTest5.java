package com.example.webflux.AsyncTest;

import javax.crypto.spec.PSource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTest5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        FutureTask<String> f = new FutureTask<>(() -> {
            Thread.sleep(2000);
            System.out.println("Async");
            return "Inside";
        }) {
            @Override
            protected void done() {
                try {
                    System.out.println(get());
                } catch (InterruptedException e){
                    e.printStackTrace();
                } catch (ExecutionException e){
                    e.printStackTrace();
                }
            }
        };
        es.execute(f);
    }
}

/**
 * 실행 결과
 * Async
 * Inside
 *
 * (done(): 비동기 작업이 모두 완료되면 호출되는 hook같은 것)
 * **/
