package com.example.demo.timewheel;

/**
 * @description: 任务载体
 * @author: Jian Chen
 * @create: 2023-03
 **/
public abstract class TimeTask implements Runnable {

    protected Long delayMs;

    private TimeTaskEntry timeTaskEntry;

    public TimeTask(Long delayMs) {
        this.delayMs = delayMs;
    }

    public void setTimeTaskEntry(TimeTaskEntry entry){
        synchronized (this){
            if (entry != null && timeTaskEntry != entry && timeTaskEntry != null){
                timeTaskEntry.remove();
            }
            timeTaskEntry = entry;
        }
    }

    public void cancel(){
        synchronized (this){
            if (timeTaskEntry != null){
                timeTaskEntry.remove();
            }
            timeTaskEntry = null;
        }
    }

    public Long getDelayMs() {
        return delayMs;
    }

    public TimeTaskEntry getTimeTaskEntry() {
        return timeTaskEntry;
    }
}
