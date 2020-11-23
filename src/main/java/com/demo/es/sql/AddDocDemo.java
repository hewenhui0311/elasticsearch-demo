package com.demo.es.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import com.demo.es.User;

/**
 * Elasticsearch 7.10.0不支持insert、update、delete
 * @author Administrator
 *
 */
public class AddDocDemo {

	public boolean addUser(User user) throws SQLException {
		String sql = "insert into users(id, name, age, birthDay, message) values (" + user.getId() + ","
				+ user.getName() + "," + user.getAge() + "," + user.getBirthDay() + "," + user.getMessage() + ")";
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		return statement.execute(sql);
	}

	public static void main(String[] args) throws SQLException {
		AddDocDemo demo = new AddDocDemo();
		User user = new User();
		user.setId(5);
		user.setName("Zhangxueyou");
		user.setAge(40);
		user.setBirthDay(new Date(1975,10, 10));
		user.setMessage("I am zhangxuyou");
		demo.addUser(user);
	}

}
