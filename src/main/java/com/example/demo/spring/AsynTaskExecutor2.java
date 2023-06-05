package com.example.demo.spring;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chenjian
 * @date 2023/5/25 15:13
 * @description:
 */
@Slf4j
@Component
public class AsynTaskExecutor2 {
    private PlatformTransactionManager platform;

    private Executor executor;

    public AsynTaskExecutor2(PlatformTransactionManager platform) {
        this.platform = platform;
    }

    public void doTask(List<Runnable> tasks) throws Exception {
        //控制所有子线程是不是有异常
        final AtomicBoolean error = new AtomicBoolean(false);
        List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<>(tasks.size()));
        List<TransactionResource> transactionResources = Collections.synchronizedList(new ArrayList<>(tasks.size()));
        List<Runnable> taskList = new ArrayList<>(tasks.size());
        CompletableFuture[] completedList = new CompletableFuture[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            Runnable task = tasks.get(i);
            Task task1 = new Task(
                    task,
                    this.platform,
                    error,
                    transactionStatuses,
                    transactionResources,
                    completedList);
            taskList.add(task1);
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(task1, executor);
            completedList[i] = voidCompletableFuture;
        }
        try {
            CompletableFuture.allOf(completedList).get();
        } catch (Exception e) {
            throw e;
        }
        if (error.get()) {
            for (int i = 0; i < completedList.length; i++) {
                transactionResources.get(i).autoWiredTransactionResource();
                platform.rollback(transactionStatuses.get(i));
                transactionResources.get(i).removeTransactionResource();
            }
        } else {
            for (int i = 0; i < completedList.length; i++) {
                transactionResources.get(i).autoWiredTransactionResource();
                platform.commit(transactionStatuses.get(i));
                transactionResources.get(i).removeTransactionResource();
            }
        }
    }

    @PostConstruct
    public void init() {
        ThreadFactory threadFactory = r -> {
            Thread thread = new Thread(r, "Asyn-Task-Executor-");
            thread.setUncaughtExceptionHandler((t, e) -> {
                log.error("当前执行出错{},e:{}", Thread.currentThread().getName(),e.getMessage());
            });
            return thread;
        };
        this.executor = Executors.newScheduledThreadPool(4, threadFactory);
    }

    @Slf4j
    public static class Task implements Runnable {

        private Runnable runnable;
        private PlatformTransactionManager platform;
        private AtomicBoolean error;
        private List<TransactionStatus> transactionStatuses;
        private List<TransactionResource> transactionResources;
        private CompletableFuture[] completedList;

        public Task(Runnable runnable,
                    PlatformTransactionManager platform,
                    AtomicBoolean error,
                    List<TransactionStatus>  transactionStatuses,
                    List<TransactionResource> transactionResources,
                    CompletableFuture[] completedList) {
            this.runnable = runnable;
            this.platform = platform;
            this.error = error;
            this.transactionStatuses = transactionStatuses;
            this.transactionResources = transactionResources;
            this.completedList = completedList;
        }

        @Override
        public void run() {
            try {
                DefaultTransactionDefinition transactionDef = new DefaultTransactionDefinition();
                //transactionDef.setPropagationBehavior(3);
                //开启事务
                TransactionStatus transaction = this.platform.getTransaction(transactionDef);
                transactionStatuses.add(transaction);
                //copy事务资源
                transactionResources.add(TransactionResource.copyTransactionResource());
                // 执行业务逻辑
                this.runnable.run();
            } catch (Exception e) {
                error.set(Boolean.TRUE);
                //Arrays.asList(this.completedList).forEach(cf -> cf.cancel(true));
                throw e;
            }
        }
    }

    /**
     * 保存当前事务资源,用于线程间的事务资源COPY操作
     */
    @Builder
    private static class TransactionResource {
        //事务结束后默认会移除集合中的DataSource作为key关联的资源记录
        private Map<Object, Object> resources = new HashMap<>();

        //下面五个属性会在事务结束后被自动清理,无需我们手动清理
        private Set<TransactionSynchronization> synchronizations = new HashSet<>();

        private String currentTransactionName;

        private Boolean currentTransactionReadOnly;

        private Integer currentTransactionIsolationLevel;

        private Boolean actualTransactionActive;

        public static TransactionResource copyTransactionResource() {
            return TransactionResource.builder()
                    //返回的是不可变集合
                    .resources(TransactionSynchronizationManager.getResourceMap())
                    //如果需要注册事务监听者,这里记得修改--我们这里不需要,就采用默认负责--spring事务内部默认也是这个值
                    .synchronizations(new LinkedHashSet<>())
                    .currentTransactionName(TransactionSynchronizationManager.getCurrentTransactionName())
                    .currentTransactionReadOnly(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
                    .currentTransactionIsolationLevel(TransactionSynchronizationManager.getCurrentTransactionIsolationLevel())
                    .actualTransactionActive(TransactionSynchronizationManager.isActualTransactionActive())
                    .build();
        }

        public void autoWiredTransactionResource() {
            resources.forEach(TransactionSynchronizationManager::bindResource);
            //如果需要注册事务监听者,这里记得修改--我们这里不需要,就采用默认负责--spring事务内部默认也是这个值
            TransactionSynchronizationManager.initSynchronization();
            TransactionSynchronizationManager.setActualTransactionActive(actualTransactionActive);
            TransactionSynchronizationManager.setCurrentTransactionName(currentTransactionName);
            TransactionSynchronizationManager.setCurrentTransactionIsolationLevel(currentTransactionIsolationLevel);
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(currentTransactionReadOnly);
        }

        public void removeTransactionResource() {
            //事务结束后默认会移除集合中的DataSource作为key关联的资源记录
            //DataSource如果重复移除,unbindResource时会因为不存在此key关联的事务资源而报错
            resources.keySet().forEach(key -> {
                if (!(key instanceof DataSource)) {
                    TransactionSynchronizationManager.unbindResource(key);
                }
            });
        }
    }

}
