package org.elasticsearch.river.kafka;

public class Stats {
	public int numMessages;
	public int flushes;
	public int succeeded;
	public int failed;
	public double rate;
	long backlog;
	
	public Stats(){
		reset();
	}
	
	void reset() {
		numMessages = 0;
		flushes = 0;
		succeeded = 0;
		failed = 0;
		rate = 0;
		backlog = 0;
	}
}
