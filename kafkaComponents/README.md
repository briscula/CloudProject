#kafkaComponents

Kafka is a high-throughput and distributed messaging system. The messages or the data from crawler are published to topics by Producers. Consumers will subscribe to the topics and process the feed.

Topics data is stored in partitions to serve the Kafka Consumers. We are using multiple producers to increase the throughput of feed data. This can also be scaled depending on load of data. Consumers read data from partitions in sequential order and will re-read the data in case of any failures.
