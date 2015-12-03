package org.elasticsearch.river.kafka;

import java.nio.ByteBuffer;

import kafka.message.Message;

import org.elasticsearch.action.bulk.BulkRequestBuilder;

public abstract class MessageHandler {
	public static byte[] getMessageData(Message message) {
		ByteBuffer buf = message.payload();
		byte[] data = new byte[buf.remaining()];
		buf.get(data);
		return data;
	}

	public abstract void handle(BulkRequestBuilder bulkRequestBuilder, Message message) throws Exception;

}