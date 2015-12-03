
package org.elasticsearch.river.kafka;

import org.elasticsearch.client.Client;

public interface MessageHandlerFactory {
	public MessageHandler createMessageHandler(Client client) throws Exception;
}