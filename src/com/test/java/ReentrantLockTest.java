package com.test.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hanjiahu on 2017/6/27.
 */

public class ReentrantLockTest {
  private ReentrantLock lock = new ReentrantLock();
  public void doFirstLock() {
    lock.lock();
    try {
      System.out.println("doFirstLock---" + Thread.currentThread().getId());
      doSecondLock();
    } finally {
      lock.unlock();
    }
  }
  public void doSecondLock() {
    lock.lock();
    try {
      System.out.println("doSecondLock---" + Thread.currentThread().getId());
    } finally {
      lock.unlock();
    }
  }
  public static void main(String[] args) throws InterruptedException {



    //for (int i = 0; i < 10; i++) {
    //  new Thread() {
    //    @Override
    //    public void run() {
    //      new ReentrantLockTest().doFirstLock();
    //    }
    //  }.start();
    //}
    //TimeUnit.SECONDS.sleep(100 * 50);





    //信号量
    final Semaphore semp = new Semaphore(5);

    ExecutorService exec = Executors.newCachedThreadPool();

    for (int index = 0; index < 20; index++) {
      final int NO = index;
      Runnable run = new Runnable() {
        public void run() {
          try {
            // 获取许可
            semp.acquire();
            System.out.println("Accessing: " + NO);
            Thread.sleep((long) (Math.random() * 10000));
            // 访问完后，释放
            semp.release();
            System.out.println("-----------------" + semp.availablePermits());
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      };
      exec.execute(run);
    }
    exec.shutdown();
  }
}
