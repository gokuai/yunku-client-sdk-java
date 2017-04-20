package com.gokuai.library.net;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class NetRunnable implements Runnable {
    protected ReentrantLock mPauseLock = new ReentrantLock();
    protected Condition mUnpaused = mPauseLock.newCondition();
    protected boolean isPaused;

    public boolean mStop = false;

    public void setStop(boolean stop) {
        mStop = stop;
    }

    public boolean isStop() {
        return mStop;
    }

    public void pause() {
        mPauseLock.lock();
        try {
            isPaused = true;
        } finally {
            mPauseLock.unlock();
        }
    }

    public void resume() {
        mPauseLock.lock();
        try {
            isPaused = false;
            mUnpaused.signalAll();
        } finally {
            mPauseLock.unlock();
        }
    }

    public abstract long getRunnableId();
}
