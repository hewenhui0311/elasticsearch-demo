package com.demo.es.client;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class SearchDocDemo {

	public void findByPagination() throws IOException {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(0);
		sourceBuilder.size(25);
		sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC)); // 排序
//      sourceBuilder.query(QueryBuilders.matchAllQuery());

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("users");
		searchRequest.source(sourceBuilder);
		RestHighLevelClient client = ESClientFactory.getEsClient();
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		RestStatus restStatus = searchResponse.status();
		if (restStatus == RestStatus.OK) {
			SearchHits searchHits = searchResponse.getHits();
			for (SearchHit hit : searchHits.getHits()) {
				String source = hit.getSourceAsString();
				System.out.println(source);
			}
		}
		client.close();
	}

	public void findByEqualsCondition() throws IOException {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", "JOE"));
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(queryBuilder);
		SearchRequest searchRequest = new SearchRequest().indices("users").source(sourceBuilder);
		RestHighLevelClient client = ESClientFactory.getEsClient();
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		RestStatus restStatus = searchResponse.status();
		if (restStatus == RestStatus.OK) {
			SearchHits searchHits = searchResponse.getHits();
			for (SearchHit hit : searchHits.getHits()) {
				String source = hit.getSourceAsString();
				System.out.println(source);
			}
		}
		client.close();
	}

	/**
	 * 模糊查询
	 * 
	 * @throws IOException
	 */
	public void findByWildcard() throws IOException {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery("name", ("*Joe*").toLowerCase()));
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(queryBuilder);
		SearchRequest searchRequest = new SearchRequest().indices("users").source(sourceBuilder);
		RestHighLevelClient client = ESClientFactory.getEsClient();
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		RestStatus restStatus = searchResponse.status();
		if (restStatus == RestStatus.OK) {
			SearchHits searchHits = searchResponse.getHits();
			for (SearchHit hit : searchHits.getHits()) {
				String source = hit.getSourceAsString();
				System.out.println(source);
			}
		}
		client.close();
	}

	public void findByRangeCondition() throws IOException {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.rangeQuery("age").gt(20).lt(40));
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(queryBuilder);
		SearchRequest searchRequest = new SearchRequest().indices("users").source(sourceBuilder);
		RestHighLevelClient client = ESClientFactory.getEsClient();
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		RestStatus restStatus = searchResponse.status();
		if (restStatus == RestStatus.OK) {
			SearchHits searchHits = searchResponse.getHits();
			for (SearchHit hit : searchHits.getHits()) {
				String source = hit.getSourceAsString();
				System.out.println(source);
			}
		}
		client.close();
	}

	public void findById() throws IOException {
		GetRequest getRequest = new GetRequest("users", "1");
		RestHighLevelClient client = ESClientFactory.getEsClient();
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		if (getResponse.isExists()) {
			String source = getResponse.getSourceAsString();
			System.out.println(source);
		}
		client.close();
	}

	public static void main(String[] args) throws IOException {
		SearchDocDemo demo = new SearchDocDemo();
		demo.findByPagination();
		demo.findById();
		demo.findByEqualsCondition();
		demo.findByWildcard();
		demo.findByRangeCondition();
	}

}
