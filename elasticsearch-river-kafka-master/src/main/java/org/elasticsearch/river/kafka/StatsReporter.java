package org.elasticsearch.river.kafka;

import com.timgroup.statsd.StatsDClient;

public class StatsReporter {

	StatsDClient statsd = null;
	
	String numMsg;
	String flushes;
	String failed;
	String succeeded;
	String rateName;
	String backlog;
	
	public StatsReporter(KafkaRiverConfig riverConfig) {
		if(riverConfig.statsdHost == null){
			return;
		}
		
		statsd = new StatsDClient(riverConfig.statsdPrefix, riverConfig.statsdHost, riverConfig.statsdPort);
		String baseName = String.format("%s.%d.%s.%d", 
				riverConfig.brokerHost, riverConfig.brokerPort,
				riverConfig.topic, riverConfig.partition);
		
		numMsg    = baseName + ".numMsg";
		flushes   = baseName + ".flushes";
		failed    = baseName + ".failed";
		succeeded = baseName + ".succeeded";
		rateName  = baseName + ".rate";
		backlog   = baseName + ".backlog";
	}

	public void reoportStats(Stats stats) {
		if(!isEnabled())
			return;
		
		statsd.count(numMsg, stats.numMessages);
		statsd.count(flushes, stats.flushes);
		statsd.count(failed, stats.failed);
		statsd.count(succeeded, stats.succeeded);
		statsd.gauge(rateName, (int) Math.floor(stats.rate));
		if(stats.backlog > Integer.MAX_VALUE){
			statsd.gauge(backlog, Integer.MAX_VALUE);
		}
		else{
			statsd.gauge(backlog, (int)stats.backlog);
		}
	}
	
	boolean isEnabled() {
		return null != statsd;
	}
}
