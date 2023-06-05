package com.example.demo.timewheel;

/**
 * @description:
 * @author: Jian Chen
 * @create: 2023-03
 **/
public class TimeTaskEntry implements Comparable<TimeTaskEntry>{
    protected TimeTask timeTask;
    private Long expirationMs;
    protected volatile TimerTaskList list;
    protected TimeTaskEntry next;
    protected TimeTaskEntry prev;

    public TimeTaskEntry(TimeTask timeTask, Long expirationMs) {
        this.timeTask = timeTask;
        this.expirationMs = expirationMs;
        if (timeTask != null){
            timeTask.setTimeTaskEntry(this);
        }
    }

    public Boolean cancelled(){
        return timeTask.getTimeTaskEntry() != this;
    }

    public void remove() {
        TimerTaskList currentList = this.list;
        // 如果在另一个线程将条目从一个任务条目列表移动到另一个任务条目列表时调用remove，
        // 则由于list的值发生更改，该操作可能无法删除该条目。因此，重试，直到列表变为空。
        // 在极少数情况下，此线程会看到null并退出循环，但另一个线程稍后会将条目插入另一个列表。
        while (currentList != null) {
            currentList.remove(this);
            currentList = list;
        }
    }

    public Long getExpirationMs() {
        return expirationMs;
    }

    @Override
    public int compareTo(TimeTaskEntry o) {
        return Long.compare(this.expirationMs,o.expirationMs);
    }
}
