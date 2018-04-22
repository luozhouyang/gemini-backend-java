package me.stupidme.gemini.dao;

import me.stupidme.gemini.domain.User;

import java.sql.*;
import java.util.Calendar;

public class UserDao {

    private static final String SQL_INSERT = "INSERT INTO users (uuid, name, email, password, created_at) " +
            "VALUES (?, ?, ?, ?, ?) RETURNING id, created_at";

    public UserDao() {

    }

    public boolean insert(User user) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        if (connection == null) {
            return false;
        }
        try {
            if (user.getCreatedAt() == null) {
                Calendar calendar = Calendar.getInstance();
                long time = calendar.getTimeInMillis();
                user.setCreatedAt(new Timestamp(time));
            }
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, createUuid(user));
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setTimestamp(5, user.getCreatedAt());
            ResultSet set = statement.getGeneratedKeys();
            user.setId(set.getLong(1));
            user.setCreatedAt(set.getTimestamp(2));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String createUuid(User user) {
        return "";
    }

}
