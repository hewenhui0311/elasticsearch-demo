package com.demo.es.client;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

public class DeleteDocDemo {

	public void deleteById() throws IOException {
		RestHighLevelClient client = ESClientFactory.getEsClient();
		DeleteRequest request = new DeleteRequest("users", "1");
		DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
		if (deleteResponse.status() == RestStatus.OK) {
           System.out.println("delete successfully!");
        } else {
        	 System.out.println("id=1 is not exist!");
        }
		client.close();
	}
	
	public static void main(String[] args) throws IOException {
		new DeleteDocDemo().deleteById();
	}
}
