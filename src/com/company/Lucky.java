package com.company;

public class Lucky {
    static int x = 0;
    static int count = 0;
    public static int threadCount = 0;

    static class LuckyThread extends Thread {

        private static final Object lock = new Object();

        @Override
        public void run() {
            int temp;
            while (x <= 1_000_000 - threadCount) {
                synchronized (lock) {
                    x++;
                    temp = x;
                }
                if ((temp % 10) + (temp / 10) % 10 + (temp / 100) % 10 == //Проверка "счастливого билета"
                        (temp / 1000) % 10 + (temp / 10000) % 10 + (temp / 100000) % 10) {
                    System.out.println(temp);
                    synchronized (lock) {
                        count++;
                    }
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();

        Lucky.threadCount = Thread.activeCount() - 1;

        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);
    }
}