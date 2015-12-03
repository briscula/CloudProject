
package org.elasticsearch.river.kafka;

import java.util.Map;

import kafka.message.Message;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.type.TypeReference;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

/**
 * JsonMessageHandler
 * 
 * Handle a simple json message
 * Uses BulkRequestBuilder to send messages in bulk
 * 
 * example format 
 * { "index" : "example_index", 
 *  "type" : "example_type", 
 *  "id" : "asdkljflkasjdfasdfasdf", 
 *  "source" : {"source_data1":"values of source_data1", "source_data2" : 99999 } 
 *  }
 *  
 *  index, type, and source are required id is optional
 *   
 */
public class JsonMessageHandler extends MessageHandler {

	final ObjectReader reader = new ObjectMapper().reader(new TypeReference<Map<String, Object>>() {});

	private Client client;
	private Map<String, Object> messageMap;

	public JsonMessageHandler(Client client) {
		this.client = client;
	}

	protected void readMessage(Message message) throws Exception {
		messageMap = reader.readValue(getMessageData(message));
	}

	protected String getIndex() {
		return (String) messageMap.get("index");
	}

	protected String getType() {
		return (String) messageMap.get("type");
	}

	protected String getId() {
		return (String) messageMap.get("id");
	}

	protected Map<String, Object> getSource() {
		return (Map<String, Object>) messageMap.get("source");
	}

	protected IndexRequestBuilder createIndexRequestBuilder() {
		//prepareIndex() will automatically create the index if it doesn't exist
		return client.prepareIndex(getIndex(), getType(), getId()).setSource(getSource());
	}

	@Override
	public void handle(BulkRequestBuilder bulkRequestBuilder, Message message) throws Exception {
		this.readMessage(message);
		bulkRequestBuilder.add( this.createIndexRequestBuilder() );
	}

}
