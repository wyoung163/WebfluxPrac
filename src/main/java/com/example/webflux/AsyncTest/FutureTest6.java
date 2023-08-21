package com.example.webflux.AsyncTest;

import java.util.Objects;
import java.util.concurrent.*;

public class FutureTest6 {
    interface SuccessCallback {
        void onSuccess(String result);
    }
    interface ExceptionalCallback {
        void onError(Throwable t);
    }
    public static class CallBackFutureTask extends FutureTask<String> {
        SuccessCallback sc;
        ExceptionalCallback ec;

        public CallBackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionalCallback ec) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done() {
            try {
                sc.onSuccess(get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                ec.onError(e.getCause());
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException{
        ExecutorService es = Executors.newCachedThreadPool();

        CallBackFutureTask f = new CallBackFutureTask(() -> {
            Thread.sleep(2000);
            //if(1==1) throw new RuntimeException("Async error");
            System.out.println("Async");
            return "Inside";
        },
                s -> System.out.println(s),
                e -> System.out.println("error: " + e.getMessage()));
        System.out.println(f.isDone());
        es.execute(f);
        System.out.println(f.isDone());
        Thread.sleep(2100);
        System.out.println(f.isDone());
        es.shutdown();
    }
}

/**
 * 실행 결과
 * false
 * false
 * Async
 * Inside
 * true
 * **/