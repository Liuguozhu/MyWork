package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.io.auction.AuctionIOTask;
import iyunu.NewTLOL.model.io.gang.GangIOTask;
import iyunu.NewTLOL.model.io.mail.MailIOTask;
import iyunu.NewTLOL.model.io.role.RoleIOTask;
import iyunu.NewTLOL.processor.io.AuctionProcessor;
import iyunu.NewTLOL.processor.io.CallBackProcessor;
import iyunu.NewTLOL.processor.io.GangTaskProcessor;
import iyunu.NewTLOL.processor.io.MailProcessor;
import iyunu.NewTLOL.processor.io.TaskProcessor;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.concurrent.ConcurrentLinkedQueue;

public class IOProcessManager {

	private static IOProcessManager instance = new IOProcessManager();

	private IOProcessManager() {

	}

	public static IOProcessManager instance() {
		return instance;
	}

	private ConcurrentLinkedQueue<RoleIOTask> queue = new ConcurrentLinkedQueue<RoleIOTask>();
	private ConcurrentLinkedQueue<RoleIOTask> callBackQueue = new ConcurrentLinkedQueue<RoleIOTask>();
	private ConcurrentLinkedQueue<GangIOTask> gangQueue = new ConcurrentLinkedQueue<GangIOTask>();
	private ConcurrentLinkedQueue<MailIOTask> mailQueue = new ConcurrentLinkedQueue<MailIOTask>();
	private ConcurrentLinkedQueue<AuctionIOTask> auctionQueue = new ConcurrentLinkedQueue<AuctionIOTask>();
	public static int dbTaskCount = 0;
	public TaskProcessor taskProcessor;
	public CallBackProcessor callBackProcessor;
	public GangTaskProcessor gangTaskProcessor;
	public MailProcessor mailProcessor;
	public AuctionProcessor auctionProcessor;

	/**
	 * 初始化IO处理器
	 */
	public void init() {
		taskProcessor = new TaskProcessor(this);
		taskProcessor.start();

		callBackProcessor = new CallBackProcessor(this);
		callBackProcessor.start();

		gangTaskProcessor = new GangTaskProcessor(this);
		gangTaskProcessor.start();

		mailProcessor = new MailProcessor(this);
		mailProcessor.start();

//		auctionProcessor = new AuctionProcessor(this);
//		auctionProcessor.start();

		LogManager.info("【IO处理器】加载完成");
	}

	/**
	 * 停机
	 */
	public void shutdown() {

		// 如果系统中还有数据尚未存完，则等候其存储完成
		while (!queue.isEmpty()) {
			try {
				Thread.sleep(500);
			} catch (Exception interruptedException) {
				interruptedException.printStackTrace();
			}
		}
		// 关掉所有线程
		taskProcessor.shutdown();
		LogManager.info("【IO任务队列】已关闭");

		// 如果系统中还有数据尚未存完，则等候其存储完成
		while (!callBackQueue.isEmpty()) {
			try {
				Thread.sleep(500);
			} catch (Exception interruptedException) {
				interruptedException.printStackTrace();
			}
		}
		// 关掉所有线程
		callBackProcessor.shutdown();
		LogManager.info("【IO回调任务队列】已关闭");

		// 如果系统中还有数据尚未存完，则等候其存储完成
		while (!gangQueue.isEmpty()) {
			try {
				Thread.sleep(500);
			} catch (Exception interruptedException) {
				interruptedException.printStackTrace();
			}
		}
		// 关掉所有线程
		gangTaskProcessor.shutdown();
		LogManager.info("【帮派IO任务队列】已关闭");

		// 如果系统中还有数据尚未存完，则等候其存储完成
		while (!mailQueue.isEmpty()) {
			try {
				Thread.sleep(500);
			} catch (Exception interruptedException) {
				interruptedException.printStackTrace();
			}
		}
		// 关掉所有线程
		mailProcessor.shutdown();
		LogManager.info("【邮件IO任务队列】已关闭");

		// 如果系统中还有数据尚未存完，则等候其存储完成
//		while (!auctionQueue.isEmpty()) {
//			try {
//				Thread.sleep(500);
//			} catch (Exception interruptedException) {
//				interruptedException.printStackTrace();
//			}
//		}
//		// 关掉所有线程
//		auctionProcessor.shutdown();
//		LogManager.info("【拍卖行IO任务队列】已关闭");

		LogManager.info("【IO处理器】已关闭");
	}

	/**
	 * 添加IO任务
	 * 
	 * @param task
	 *            IO任务
	 */
	public void addTask(final RoleIOTask task) {
		dbTaskCount++;
		queue.offer(task);
//		LogManager.exception("IOProcessManager*********queue********" + queue.size());
	}

	/**
	 * 获取IO任务
	 * 
	 * @return IO任务
	 */
	public RoleIOTask getNextTask() {
		if (dbTaskCount > 0) {
			dbTaskCount--;
		}
		return queue.poll();
	}

	/**
	 * 添加回调任务
	 * 
	 * @param task
	 *            任务
	 */
	public void addCallBackTask(final RoleIOTask task) {
		callBackQueue.offer(task);
//		LogManager.exception("IOProcessManager*********callBackQueue********" + callBackQueue.size());
	}

	/**
	 * 获取回调任务
	 * 
	 * @return 回调任务
	 */
	public RoleIOTask getNextCallBackTask() {
		return callBackQueue.poll();
	}

	/**
	 * 添加帮派任务
	 * 
	 * @param task
	 *            帮派任务
	 */
	public void addGangTask(final GangIOTask task) {
		gangQueue.offer(task);
//		LogManager.exception("IOProcessManager*********gangQueue********" + gangQueue.size());
	}

	/**
	 * 获取帮派任务
	 * 
	 * @return 帮派任务
	 */
	public GangIOTask getNextGangTask() {
		return gangQueue.poll();
	}

	/**
	 * 添加邮件任务
	 * 
	 * @param task
	 *            邮件任务
	 */
	public void addMailTask(final MailIOTask task) {
		mailQueue.offer(task);
//		LogManager.exception("IOProcessManager*********mailQueue********" + mailQueue.size());
	}

	/**
	 * 获取邮件任务
	 * 
	 * @return 邮件任务
	 */
	public MailIOTask getNextMailTask() {
		return mailQueue.poll();
	}

	/**
	 * 添加拍卖行任务
	 * 
	 * @param task
	 *            拍卖行任务
	 */
	public void addAutionTask(final AuctionIOTask task) {
		auctionQueue.offer(task);
		LogManager.exception("IOProcessManager*********auctionQueue********" + auctionQueue.size());
	}

	/**
	 * 获取拍卖行任务
	 * 
	 * @return 拍卖行任务
	 */
	public AuctionIOTask getNextAutionTask() {
		return auctionQueue.poll();
	}

}
