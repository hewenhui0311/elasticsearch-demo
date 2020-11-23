package com.demo.es.client;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESClientFactory {

	public static RestHighLevelClient getEsClient() {
		RestClientBuilder restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"),
				new HttpHost("127.0.0.1", 9201, "http"), new HttpHost("127.0.0.1", 9202, "http"));
		restClient.setFailureListener(new RestClient.FailureListener() {
			@Override
			public void onFailure(Node node) {
				super.onFailure(node);
			}
		});
		// 定义节点选择器 这个是跳过data=false，ingest为false的节点
		restClient.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
		restClient.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
			@Override
			public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
				return requestConfigBuilder.setConnectTimeout(9000) // 连接超时（默认为1秒）
						.setSocketTimeout(10000); // 套接字超时（默认为30秒）
			}
		});
		return new RestHighLevelClient(restClient);
	}
}
