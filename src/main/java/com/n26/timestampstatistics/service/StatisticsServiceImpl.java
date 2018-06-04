package com.n26.timestampstatistics.service;

import com.n26.timestampstatistics.entity.Statistics;
import com.n26.timestampstatistics.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	private PriorityQueue<Transaction> timeOrder;

	private volatile long size;
	private volatile double sum;
	private volatile double min;
	private volatile double max;

	private final Object queueLock = new Object();
	private final Object statisticsLock = new Object();

	public StatisticsServiceImpl() {
		timeOrder = new PriorityQueue<>(Comparator.comparing(Transaction::getTimestamp));

		size = 0;
		sum = 0;
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
	}


	@Override
	public void addTransaction(Transaction transaction) {
		synchronized (queueLock) {
			timeOrder.add(transaction);
		}

		synchronized (statisticsLock) {
			this.size++;
			this.sum += transaction.getAmount();
			this.min = Math.min(this.min, transaction.getAmount());
			this.max = Math.max(this.max, transaction.getAmount());
		}
	}

	@Override
	public synchronized void removeOldTransactions(Long minTimeStamp) {
		boolean queueChanged = false;

		long size = 0;
		double sum = 0;
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;

		synchronized (queueLock) {
			for (Iterator<Transaction> iterator = timeOrder.iterator(); iterator.hasNext(); ) {
				Transaction transaction = iterator.next();
				if (minTimeStamp <= transaction.getTimestamp()) break;
				iterator.remove();
				queueChanged = true;
			}


			if (queueChanged) {
				size = timeOrder.size();
				for (Transaction transaction : timeOrder) {
					Double amount = transaction.getAmount();

					sum += amount;
					min = Math.min(min, amount);
					max = Math.max(max, amount);
				}
			}
		}

		synchronized (statisticsLock) {
			if (queueChanged) {
				this.size = size;
				this.sum = sum;
				this.min = min;
				this.max = max;
			}
		}
	}

	@Override
	public Statistics getCurrentStatistics() {
		Statistics ret = new Statistics();

		synchronized (statisticsLock) {
			if (this.size > 0) {
				ret.setCount(this.size);
				ret.setSum(this.sum);
				ret.setAvg(this.sum / this.size);
				ret.setMin(this.min);
				ret.setMax(this.max);
			} else {
				ret.setCount(0L);
				ret.setSum(0.0);
				ret.setAvg(0.0);
				ret.setMin(0.0);
				ret.setMax(0.0);
			}
		}

		return ret;
	}

}
