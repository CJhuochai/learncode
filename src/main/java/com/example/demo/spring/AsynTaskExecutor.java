package com.example.demo.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chenjian
 * @date 2023/5/25 15:13
 * @description:
 */
@Slf4j
@Component
public class AsynTaskExecutor {
    private PlatformTransactionManager platform;

    private Executor executor;

    public AsynTaskExecutor(PlatformTransactionManager platform) {
        this.platform = platform;
    }

    public void doTask(List<Runnable> tasks) throws Exception {
        //控制所有子线程是不是有异常
        final AtomicBoolean error = new AtomicBoolean(false);
        final CountDownLatch twoStage = new CountDownLatch(1);
        final CountDownLatch oneStage = new CountDownLatch(tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            Runnable task = tasks.get(i);
            Task task1 = new Task(
                    task,
                    this.platform,
                    error,
                    twoStage,
                    oneStage);
            this.executor.execute(task1);
        }
        try {
            oneStage.await();
            twoStage.countDown();
            if (error.get()){
                throw new RuntimeException("子线程异常，抛出异常让主线程回滚");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @PostConstruct
    public void init() {
        this.executor = Executors.newScheduledThreadPool(4, r -> new Thread(r, "Asyn-Task-Executor-"));
    }

    @Slf4j
    public static class Task implements Runnable {

        private Runnable runnable;
        private PlatformTransactionManager platform;
        private AtomicBoolean error;
        private CountDownLatch twoStage;
        private CountDownLatch oneStage;

        public Task(Runnable runnable,
                    PlatformTransactionManager platform,
                    AtomicBoolean error,
                    CountDownLatch twoStage,
                    CountDownLatch oneStage) {
            this.runnable = runnable;
            this.platform = platform;
            this.error = error;
            this.twoStage = twoStage;
            this.oneStage = oneStage;
        }

        @Override
        public void run() {
            if (error.get()) {
                log.error("当前线程终止，因为其他线程中有子线程执行失败");
                this.oneStage.countDown();
                return;
            }
            //开启事务
            TransactionStatus transaction = this.platform.getTransaction(null);
            try {
                // 执行业务逻辑
                this.runnable.run();
            } catch (Exception e) {
                error.set(Boolean.TRUE);
                log.error("当前执行出错{}", Thread.currentThread().getName());
                throw new RuntimeException(e);
            }finally {
                oneStage.countDown();
            }
            //等待所有线程任务执行完毕，检测是否有异常
            try {
                this.twoStage.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!error.get()) {
                    this.platform.commit(transaction);
                    log.info("当前线程执行完毕{},执行体:{}", Thread.currentThread().getName(), runnable);
                } else {
                    this.platform.rollback(transaction);
                    log.error("当前线程回滚成功{},执行体:{}", Thread.currentThread().getName(), runnable);
                }
        }
    }

}
