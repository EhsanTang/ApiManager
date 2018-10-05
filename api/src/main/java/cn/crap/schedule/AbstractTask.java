package cn.crap.schedule;

/**
 * 抽象任务类
 */
public abstract class AbstractTask implements Runnable {
    @Override
    public void run() {
        doTask();
    }

    abstract void doTask();
}