package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 另起一个事物,处理调用this.method方法的问题
 * @author Administrator
 *
 */
@Component
public class TransactionalSupport {
	private static final Consumer<RuntimeException> LOG_HANDLER = new Consumer<RuntimeException>() {
		private final Logger LOGGER = LoggerFactory.getLogger(TransactionalSupport.class);
		@Override
		public void accept(RuntimeException t) {
			LOGGER.error(t.getMessage(),t);
		}
	};
	public Consumer<RuntimeException> getLogHandler(){
		return LOG_HANDLER;
	}
	@Autowired
	private Executor executor;
	
	/**
	 * 运行任务并且回滚(不会提交数据库的运行方式)
	 * @param task
	 */
	@SuppressWarnings("unchecked")
	public <T> T execWithRollback(Supplier<T> task){
		try {
			this.doExecWithRollback(task);
			throw new RuntimeException("系统异常");
		} catch (RollbackException e) {
			return (T) e.result;
		}
	}
	private void doExecWithRollback(Supplier<?> task) {
		this.execTaskWithRequiresNew(new Runnable() {
			@Override
			public void run() {
				throw new RollbackException(task.get());
			}
		});
	}
	private static class RollbackException extends RuntimeException{
		private Object result;
		public RollbackException(Object result) {
			super();
			this.result = result;
		}
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * 另起一个事物
	 * @param task
	 * @param exceptionHandler
	 */
	public void execTaskWithRequiresNew(Runnable task,Consumer<RuntimeException> exceptionHandler){
		this.doExec(()->executor.execTaskWithRequiresNew(task),exceptionHandler);
	}
	
	public void execTaskWithRequiresNew(Runnable task) {
		this.execTaskWithRequiresNew(task, null);
	}

	public <T> T execTaskWithRequiresNew(Callable<T> task) {
	    return executor.execTaskWithRequiresNew(task);
    }
	
	/**
	 * 内嵌一个子事物
	 * @param task
	 * @param exceptionHandler
	 */
	public void execTaskWithNested(Runnable task,Consumer<RuntimeException> exceptionHandler){
		this.doExec(()->executor.execTaskWithNested(task),exceptionHandler);
	}
	
	public void execTaskWithNested(Runnable task) {
		this.execTaskWithNested(task, null);
	}
	
	private void doExec(Runnable task,Consumer<RuntimeException> exceptionHandler){
		try {
			task.run();
		} catch (RuntimeException e) {
			if(exceptionHandler!=null){
				exceptionHandler.accept(e);
			}else{
				throw e;
			}
		}
	}

	@Component
	public static class Executor{
		@Transactional(propagation= Propagation.REQUIRES_NEW,isolation= Isolation.READ_COMMITTED)
		public void execTaskWithRequiresNew(Runnable task){
			task.run();
		}
        @Transactional(propagation= Propagation.REQUIRES_NEW,isolation= Isolation.READ_COMMITTED)
        public <T> T execTaskWithRequiresNew(Callable<T> task) {
		    try {
                return task.call();
            } catch (Exception e) {  // 出现任何异常，回滚事务
				System.out.println(e.getMessage());
            }
		    return null;
        }
		@Transactional(propagation= Propagation.NESTED)
		public void execTaskWithNested(Runnable task){
			task.run();
		}
		@Transactional(propagation= Propagation.REQUIRED)
		public void execTaskWithRequired(Runnable task){
			task.run();
		}
	}
}
