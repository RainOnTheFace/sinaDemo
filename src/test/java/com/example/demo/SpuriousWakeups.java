package com.example.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SpuriousWakeups {

    private Object object  = new Object();
    private ReentrantLock lock =new ReentrantLock();
    private Condition condition=lock.newCondition();


    public int count = 0;

    public void get(int cnt) {
        synchronized (object) {
            if (count <= 0) {
                try {
                    object.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count = count - cnt;
            System.out.println(Thread.currentThread() + ": final " + count);
        }
    }

    public  void put(int cnt) {
        synchronized (object) {
            count = count + cnt;
            object.notify();
        }


    }


    public static void main(String[] args) throws InterruptedException {

        SpuriousWakeups spuriousWakeups = new SpuriousWakeups();

        Thread put1 = new Thread(new Runnable() {
            @Override public void run() {
                spuriousWakeups.put(1);
            }
        });

        Thread get1 = new Thread(new Runnable() {
            @Override public void run() {
                spuriousWakeups.get(1);
            }
        });

        Thread get2 = new Thread(new Runnable() {
            @Override public void run() {
                spuriousWakeups.get(1);
            }
        });
        put1.setPriority(9);

        // get1先获取, 让object.wait 释放object的monitor
        get1.start();
        Thread.sleep(100);
        // 放入数据
        put1.start();
        get2.start();



    }
}