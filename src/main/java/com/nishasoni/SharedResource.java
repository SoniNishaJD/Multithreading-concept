package com.nishasoni;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class SharedResource {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean available = false;

    public void produce() throws InterruptedException {
        lock.lock();

        try {
            while (available) {
                condition.await();
            }
            available = true;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (!available) {
                condition.await();
            }
            available = false;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

// Locks and Conditions
//Java provides more advanced synchronization mechanisms like Lock and Condition from java.util.concurrent.locks package.
//
//ReentrantLock: A reentrant mutual exclusion lock.
//
//Condition: Provides a means for a thread to suspend execution until it is notified.