package com.demo.es.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.elasticsearch.xpack.sql.jdbc.EsDataSource;

import com.demo.es.client.ESConnectionException;

public class ConnectionFactory {

	static final String ELASTICSEARCHE_ADDRESS;

	static {
		StringBuilder sb = new StringBuilder("http://127.0.0.1:9200/?timezone=UTC")
				.append("&connect.timeout=30")
				.append("&network.timeout=30")
				.append("&page.timeout=5")
				.append("&page.size=25")
				.append("&query.timeout=5")
				;
		ELASTICSEARCHE_ADDRESS = sb.toString();
	}

	public static Connection getConnection() {
		try {
			String address = "jdbc:es://" + ELASTICSEARCHE_ADDRESS;
			Properties connectionProperties = connectionProperties();
			Connection connection = DriverManager.getConnection(address, connectionProperties);
			return connection;
		} catch (SQLException e) {
			throw new ESConnectionException(e);
		}
	}

	public static Connection getConnection2() {
		try {
			EsDataSource dataSource = new EsDataSource();
			String address = "jdbc:es://" + ELASTICSEARCHE_ADDRESS;
			dataSource.setUrl(address);
			Properties connectionProperties = connectionProperties();
			dataSource.setProperties(connectionProperties);
			Connection connection = dataSource.getConnection();
			return connection;
		} catch (SQLException e) {
			throw new ESConnectionException(e);
		}
	}

	private static Properties connectionProperties() {
		Properties properties = new Properties();
		properties.put("user", "test_admin");
		properties.put("password", "x-pack-test-password");
		return properties;
	}

}
