package com.demo.es.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.demo.es.User;

public class SQLSearchDemo {

	public User findById(long id) throws SQLException {
		String sql = "select * from users where id = " + id;
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(sql);
		while (results.next()) {
			User user = new User();
			user.setId(id);
			user.setName(results.getString("name"));
			user.setAge(results.getInt("age"));
			user.setBirthDay(results.getDate("birthDay"));
			user.setMessage(results.getString("message"));
			return user;
		}
		return null;
	}

	public List<User> findByPagination() throws SQLException {
		String sql = "select * from users order by age asc limit 4";
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(sql);
		List<User> users = new ArrayList<>();
		while (results.next()) {
			User user = new User();
			user.setId(results.getLong("id"));
			user.setName(results.getString("name"));
			user.setAge(results.getInt("age"));
			user.setBirthDay(results.getDate("birthDay"));
			user.setMessage(results.getString("message"));
			users.add(user);
		}
		return users;
	}

	public List<User> findByRange(long fromAge, long toAge) throws SQLException {
		String sql = "select * from users where age between " + fromAge + " and " + toAge +" order by age asc";
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(sql);
		List<User> users = new ArrayList<>();
		while (results.next()) {
			User user = new User();
			user.setId(results.getLong("id"));
			user.setName(results.getString("name"));
			user.setAge(results.getInt("age"));
			user.setBirthDay(results.getDate("birthDay"));
			user.setMessage(results.getString("message"));
			users.add(user);
		}
		return users;
	}

	public static void main(String[] args) throws SQLException {
		SQLSearchDemo demo = new SQLSearchDemo();
		List<User> users = demo.findByRange(20, 40);
		users.stream().forEach(u -> {
			System.out.println(u);
		});
	}
}
