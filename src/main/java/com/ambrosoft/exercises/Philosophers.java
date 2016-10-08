package com.ambrosoft.exercises;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jacek on 8/22/16.
 */
public class Philosophers {

    static final int COUNT = 5;

    static class Fork {
        final int sn;
        final Lock lock = new ReentrantLock();

        Fork(int sn) {
            this.sn = sn;
        }

        void acquire() {
            lock.lock();
        }

        void release() {
            lock.unlock();
        }

        @Override
        public String toString() {
            return "fork " + sn;
        }
    }

    static class Philosopher implements Runnable {
        final int sn;
        final Fork lft;
        final Fork rgt;
        int beans = 10;

        Philosopher(int sn, Fork lft, Fork rgt) {
            this.sn = sn;
            this.lft = lft;
            this.rgt = rgt;
        }

        @Override
        public String toString() {
            return "Philo " + sn;
        }

        @Override
        public void run() {
            while (beans > 0) {
                lft.acquire();
                rgt.acquire();
                --beans;
                rgt.release();
                lft.release();
                System.out.println(sn + "\tbeans = " + beans);
                if (beans == 0) {
                    System.out.println("FIN");
                }
            }
        }
    }

    public static void main(String[] args) {
        Fork[] forks = new Fork[COUNT];
        Philosopher[] philos = new Philosopher[COUNT];

        for (int i = 0; i < COUNT; i++) {
            forks[i] = new Fork(i);
        }

        for (int i = 0; i < COUNT - 1; i++) {
            philos[i] = new Philosopher(i, forks[i], forks[i + 1]);
        }
        philos[COUNT - 1] = new Philosopher(COUNT - 1, forks[0], forks[COUNT - 1]);
        System.out.println("done");

        for (int i = 0; i < COUNT; i++) {

            new Thread(philos[i]).start();

        }

    }


}
