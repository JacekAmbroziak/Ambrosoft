package com.ambrosoft.exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by jacek on 8/22/16.
 */
public class FirstSecondThird {

    static class Foo {
        Semaphore sem1 = new Semaphore(1);
        Semaphore sem2 = new Semaphore(1);

        Foo() {
            try {
                sem1.acquire();
                sem2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void first() {
            System.out.println("first");
            sem1.release();
        }

        void second() {
            try {
                sem1.acquire();
                System.out.println("second");
                sem1.release();
                sem2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void third() {
            try {
                sem2.acquire();
                System.out.println("third");
                sem2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final Foo foo = new Foo();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                foo.third();
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                foo.second();
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                foo.first();
            }
        });


        executorService.shutdown();
    }

}
