package com.demo.syncLockTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
public class JMHLockTest {
	private final Lock lock = new ReentrantLock();
	private static int count;

	public static void main(String[] args) throws RunnerException {

		Options opt = new OptionsBuilder().include(JMHLockTest.class.getSimpleName()).forks(1).warmupIterations(5)
				.measurementIterations(5).build();

		new Runner(opt).run();

	}

	@Benchmark
	@Threads(50)
	public void add() {

		for (int i = 0; i < 500000; i++) {
			lock.lock();
			try {
				count++;
			} finally {
				lock.unlock();
			}
		}
	}

}
