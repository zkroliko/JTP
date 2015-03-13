package pl.edu.agh.jtp.zad6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

/**
 * Utility for chcecking fragments of an array
 * @author Zbigniew Królikowski
 *
 */
public final class FragmentChecker extends Thread {
	private int startIndex;
	private int endIndex;
	private int currIndex;
	private List<Entry> numbers;
	private boolean running;
	private boolean finished;

	private Stopwatch stopwatch = Stopwatch.createUnstarted();
	private List<Long> times = new ArrayList<Long>();
	public long creationTime;

	/**
	 * Main and only constructor
	 * 
	 * @param list
	 *            containing entries <BigInteger,bool>
	 * @param start
	 *            Starting index for checking
	 * @param end
	 *            Last index to check
	 */
	public FragmentChecker(List<Entry> list, int start, int end, long creationTime) {
		this.numbers = list;
		this.startIndex = start;
		this.endIndex = end;
		this.currIndex = startIndex;
		this.running = true;
		this.finished = false;
		this.creationTime = creationTime;
	}

	/**
	 * Execution method
	 */
	public void run() {
		stopwatch.start();
		while (currIndex <= endIndex) {
			while (!running) {
				try {
					sleep(1000);
				} catch (InterruptedException i) {
					continue;
				}
			}
			workLoop();
		}
		times.add(stopwatch.elapsed(TimeUnit.MILLISECONDS));
		System.out.println(times.get(0));
		System.out.println(times.get(1));
		System.out.println(times.get(2));
		stopwatch.stop();
		if (currIndex >= endIndex) {
			finished = true;
			running = false;
		}
	}

	/**
	 * Main work loop checking the array elements
	 */
	private void workLoop() {
		times.add(stopwatch.elapsed(TimeUnit.MILLISECONDS));
		while (running && currIndex <= endIndex) {
			if (!numbers.get(currIndex).getChecked()
					&& isPrime(numbers.get(currIndex).getValue())) {
				numbers.get(currIndex).setPrime(true);
				// DEBUG
				//System.out.println(numbers.get(currIndex).getValue());
				//
			}
			numbers.get(currIndex).check();
			currIndex++;
		}
		times.add(stopwatch.elapsed(TimeUnit.MILLISECONDS));
	}

	/**
	 * Used for stopping the thread
	 */
	public void stopWorking() {
		this.running = false;

	}

	/**
	 * Used for continuing the work of a thread
	 */
	public void continueWorking() {
		this.running = true;
		this.interrupt();
	}

	/**
	 * Returns true if thread stopped working
	 */
	public boolean isFinished() {
		return this.finished;
	}

	/**
	 * Outputs the thread start index
	 * 
	 * @return
	 */
	public int getStartIndex() {
		return this.startIndex;
	}

	/**
	 * Outputs the thread end index
	 * 
	 * @return
	 */
	public int getEndIndex() {
		return this.endIndex;
	}

	/**
	 * Outputs the thread current index
	 * 
	 * @return
	 */
	public int getCurrIndex() {
		return this.currIndex;
	}
	
	/**
	 * Getter for the time table
	 */
	public long getTime(int index) {
		return times.get(index);
	}
	
	/**
	 * Getter for the cretion time
	 */
	public long getCreationTime(int index) {
		return creationTime;
	}
	
	/**
	 * Checking whether a given number is prime.
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isPrime(final BigInteger number) {
		if (number.compareTo(BigInteger.valueOf(1)) == 0)
			return false;
		if (number.compareTo(BigInteger.valueOf(2)) == 0
				|| number.compareTo(BigInteger.valueOf(3)) == 0) {
			return true;
		}
		if ((number.mod(BigInteger.valueOf(2))).compareTo(new BigInteger("0")) == 0
				|| (number.mod(BigInteger.valueOf(3))).compareTo(BigInteger
						.valueOf(0)) == 0)
			return false;
		for (BigInteger i = new BigInteger("6"); (i.pow(2)).compareTo(number
				.add(BigInteger.valueOf(1))) == -1; i = i.add(BigInteger
				.valueOf(6))) {
			if (((number.mod(i.add(BigInteger.valueOf(-1))))
					.compareTo(BigInteger.valueOf(0)) == 0)
					|| (number.mod(i.add(BigInteger.valueOf(1))).compareTo(
							BigInteger.valueOf(0)) == 0)) {
				return false;

			}
		}
		return true;
	}
}
