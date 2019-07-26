package com.nowcoder.answer;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class MyThread extends Thread{
    private int tid;
    public MyThread(int tid){
        this.tid=tid;
    }
    @Override
    public void run() {
        try{
            for(int i=0;i<10;i++){
                Thread.sleep(1000);
                System.out.println(String.format("%d:%d",tid,i));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class Consumer implements Runnable{
    private BlockingQueue<String>q;
    public Consumer(BlockingQueue<String>q){
        this.q=q;
    }
    @Override
    public void run(){
        try{
            while(true){
                System.out.println(Thread.currentThread().getName()+":"+q.take());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class Producer implements Runnable{
    private BlockingQueue<String>q;
    public Producer(BlockingQueue<String>q){
        this.q=q;
    }
    @Override
    public void run() {
        try{
            for(int i=0;i<100;++i){
                Thread.sleep(1000);
                q.put(String.valueOf(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

public class MultiThreadTests {
    public static void testThread(){
        for(int i=0;i<10;++i){
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try{
                        for(int i=0;i<10;i++){
                            Thread.sleep(1000);
                            System.out.println(String.format("T2:%d",i));
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testExecutor(){
        //ExecutorService service= Executors.newSingleThreadExecutor();
        ExecutorService service= Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;++i){
                    try{
                        Thread.sleep(1000);
                        System.out.println("Executor1:"+i);

                    }catch(Exception e ){
                        e.printStackTrace();
                    }
                }
            }
        });
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;++i){
                    try{
                        Thread.sleep(1000);
                        System.out.println("Executor2:"+i);

                    }catch(Exception e ){
                        e.printStackTrace();
                    }
                }
            }
        });

        service.shutdown();
//        while(!service.isTerminated()){
//            try{
//                Thread.sleep(1000);
//                service.shutdownNow();
//                System.out.println("wait for termination!");
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
    }
    public static void testBlockQueue(){
        BlockingQueue<String>q=new ArrayBlockingQueue<>(10);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q),"Consumer1").start();
        new Thread(new Consumer(q),"Consumer2").start();
    }

    private static ThreadLocal<Integer>threadLocalUserIds=new ThreadLocal<>();
    private static int userId;
    public static void testThreadLocal(){
        for(int i=0;i<10;++i){
            final int finalI=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        threadLocalUserIds.set(finalI);
                        Thread.sleep(1000);
                        System.out.println("ThreadLocal:"+threadLocalUserIds.get());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        for(int i=0;i<10;++i){
            final  int fianlI=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        userId=fianlI;
                        Thread.sleep(1000);
                        System.out.println("UserId:"+userId);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static int counter=0;
    private static AtomicInteger atomicInteger=new AtomicInteger(0);
    public static void testWithoutAtomic(){
        for(int i=0;i<10;++i){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        synchronized (Thread.class) {
                            for (int j = 0; j < 10; ++j) {
                                counter++;
                                System.out.println(Thread.currentThread().getName()+":"+counter);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testWithAtomic(){
        for(int i=0;i<10;++i){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        for(int j=0;j<10;++j){
                            System.out.println(Thread.currentThread().getName()+":"+atomicInteger.incrementAndGet());
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testFuture(){
        ExecutorService service =Executors.newSingleThreadExecutor();
        Future<Integer>future=service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //throw new IllegalArgumentException("异常");
                for(int i=0;i<10;++i){
                    try{
                        Thread.sleep(1000);
                        System.out.println("Executor1:"+i);

                    }catch(Exception e ){
                        e.printStackTrace();
                    }
                }
                return 1;
            }
        });
        service.shutdown();
        try{
            //System.out.println(future.get());
            System.out.println(future.get(10000,TimeUnit.MILLISECONDS));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main (String[] args) {
        //testThread();
        //testExecutor();
        testBlockQueue();
        //testThreadLocal();
        //testWithoutAtomic();
        //testWithAtomic();
        //testFuture();
    }
}
