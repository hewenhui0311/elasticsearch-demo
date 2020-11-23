package com.demo.es.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

public class AddDocDemo {

	public IndexRequest doIndexRequest() {
		IndexRequest request = new IndexRequest("users"); // 索引
		request.id("1"); // 文档id
		String jsonString = "{\"id\":1,\"name\":\"Joe Henry\",\"age\":30,\"birthDay\":\"1985-01-30\","
				+ "\"message\":\"my name is Joe Henry\"}";
		request.source(jsonString, XContentType.JSON); // 以字符串形式提供的文档源
		return request;
	}

	public IndexRequest doIndexRequest2() {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("id", "2");
		jsonMap.put("name", "Joe Hill");
		jsonMap.put("age", 20);
		jsonMap.put("birthDay", "1995-04-10");
		jsonMap.put("message", "my name is Joe hill");
		IndexRequest indexRequest = new IndexRequest("users").id("2").source(jsonMap); // 以Map形式提供的文档源，可自动转换为JSON格式
		return indexRequest;
	}

	public IndexRequest doIndexRequest3() throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
			builder.field("id", "3");
			builder.field("name", "Joe Zitong");
			builder.field("age", 25);
			builder.timeField("birthDay", "1990-07-08");
			builder.field("message", "my name is Joe Zitong");
		}
		builder.endObject();
		IndexRequest indexRequest = new IndexRequest("users").id("3").source(builder);
		return indexRequest;
	}

	public IndexRequest doIndexRequest4() {
		IndexRequest indexRequest = new IndexRequest("users").id("4").source("id", 4, "name", "Joe", "age", 34,
				"birthDay", "1981-11-11", "message", "my name is Joe");
		return indexRequest;
	}

	public void syncExecute() throws IOException {
		RestHighLevelClient client = ESClientFactory.getEsClient();
		IndexResponse indexResponse = client.index(doIndexRequest(), RequestOptions.DEFAULT);
		System.out.println(indexResponse.getId() + ":" + indexResponse.getSeqNo() + ":" + indexResponse.getIndex() + ":"
				+ indexResponse.getPrimaryTerm());
		indexResponse = client.index(doIndexRequest2(), RequestOptions.DEFAULT);
		System.out.println(indexResponse.getId() + ":" + indexResponse.getSeqNo() + ":" + indexResponse.getIndex() + ":"
				+ indexResponse.getPrimaryTerm());
		indexResponse = client.index(doIndexRequest3(), RequestOptions.DEFAULT);
		System.out.println(indexResponse.getId() + ":" + indexResponse.getSeqNo() + ":" + indexResponse.getIndex() + ":"
				+ indexResponse.getPrimaryTerm());
		indexResponse = client.index(doIndexRequest4(), RequestOptions.DEFAULT);
		System.out.println(indexResponse.getId() + ":" + indexResponse.getSeqNo() + ":" + indexResponse.getIndex() + ":"
				+ indexResponse.getPrimaryTerm());
		client.close();
	}

	public void asyncExecute() throws IOException {
		RestHighLevelClient client = ESClientFactory.getEsClient();
		ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {

			@Override
			public void onResponse(IndexResponse response) {
				String index = response.getIndex();
				String id = response.getId();
				if (response.getResult() == DocWriteResponse.Result.CREATED) {// 处理创建文档的情况

				} else if (response.getResult() == DocWriteResponse.Result.UPDATED) {// 处理文档更新的情况

				}
				ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
				if (shardInfo.getTotal() != shardInfo.getSuccessful()) {// 处理成功的分片数少于总分片数时的情况

				}
				if (shardInfo.getFailed() > 0) {// 处理潜在的故障
					for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
						String reason = failure.reason();
					}
				}
			}

			@Override
			public void onFailure(Exception e) {

			}
		};
		client.indexAsync(doIndexRequest(), RequestOptions.DEFAULT, listener);// listener是执行完成时要使用的侦听器
		client.indexAsync(doIndexRequest2(), RequestOptions.DEFAULT, listener);// listener是执行完成时要使用的侦听器
		client.indexAsync(doIndexRequest3(), RequestOptions.DEFAULT, listener);// listener是执行完成时要使用的侦听器
		client.indexAsync(doIndexRequest4(), RequestOptions.DEFAULT, listener);// listener是执行完成时要使用的侦听器
		client.close();
	}

	public static void main(String[] args) throws IOException {
		new AddDocDemo().syncExecute();
	}

}
