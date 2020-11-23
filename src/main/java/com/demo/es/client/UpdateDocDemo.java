package com.demo.es.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

public class UpdateDocDemo {

	public void updateById() throws IOException {
		UpdateRequest request = new UpdateRequest("users", "4");
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("message", "Hello, I am Joe");
		request.doc(jsonMap);
		RestHighLevelClient client = ESClientFactory.getEsClient();
		UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
		if (updateResponse.status() == RestStatus.OK) {
			System.out.println("update successfully!");
		}
		client.close();
	}

	public static void main(String[] args) throws IOException {
		new UpdateDocDemo().updateById();

	}

}
