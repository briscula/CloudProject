
package org.elasticsearch.river.kafka;

import org.elasticsearch.client.Client;

public class JsonMessageHandlerFactory implements MessageHandlerFactory {
	public MessageHandler createMessageHandler(Client client) throws Exception {
		return new JsonMessageHandler(client);
	}
}