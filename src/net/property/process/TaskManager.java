package net.property.process;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.property.index.Indexer;

public class TaskManager implements ServletContextListener {
	private static ScheduledThreadPoolExecutor executor;
	
	public static void invoke() {
		executor = new ScheduledThreadPoolExecutor(5);
		
//		Indexer indexer = new Indexer(); //TODO temporarily suspended to avoid indexing of lot of data
//		executor.scheduleWithFixedDelay(indexer, 10000l, 36000000l, TimeUnit.MILLISECONDS);
		
		CrawlingController crawlingController = new CrawlingController();
		executor.scheduleWithFixedDelay(crawlingController, 500l, 86400000l, TimeUnit.MILLISECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		executor.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		TaskManager.invoke();
	}
}
