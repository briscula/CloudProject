package org.elasticsearch.river.kafka;

import kafka.message.Message;

import org.elasticsearch.action.bulk.BulkRequestBuilder;

public class RawMessageHandler extends MessageHandler
{
	public void handle(BulkRequestBuilder bulkRequestBuilder, Message message) throws Exception
	{
		byte[] data = getMessageData(message);
		bulkRequestBuilder.add(data, 0, data.length, false);
	}
}