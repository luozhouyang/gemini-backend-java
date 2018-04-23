package me.stupidme.gemini.dao;

import me.stupidme.gemini.domain.User;

import java.sql.*;
import java.util.Calendar;

/**
 * Database operations o User model.
 */
public class UserDao {

    private static final String SQL_INSERT_USER = "INSERT INTO users (uuid, name, email, password, created_at) " +
            "VALUES (?, ?, ?, ?, ?) RETURNING id, created_at";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?";
    private static final String SQL_DELETE_PROFILE_BY_USER_ID = "DELETE FROM profiles WHERE user_id=?";
    private static final String SQL_DELETE_ARTICLES_BY_USER_ID = "DELETE FROM articles WHERE user_id=?";
    private static final String SQL_DELETE_COMMENTS_BY_USER_ID = "DELETE FROM comments WHERE user_id=?";
    private static final String SQL_DELETE_SESSIONS_BY_USER_ID = "DELETE FROM sessions WHERE user_id=?";

    private static final String SQL_QUERY_USER_ID_BY_EMAIL = "SELECT id FROM users WHERE email=?";
    private static final String SQL_QUERY_USER_ID_BY_UUID = "SELECT id FROM users WHERE uuid=?";


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
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, createUuid(user));
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setTimestamp(5, user.getCreatedAt());
            int effected = statement.executeUpdate();
            if (effected <= 0) {
                connection.commit();
                return false;
            }
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                user.setId(set.getLong(1));
                user.setCreatedAt(set.getTimestamp(2));
                connection.commit();
                return true;
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    private void deleteSessionByUserId(PreparedStatement statement, long id) throws SQLException {
        statement.setLong(1, id);
        statement.execute();
    }

    private void deleteProfileByUserId(PreparedStatement statement, long id) throws SQLException {
        statement.setLong(1, id);
        statement.execute();
    }

    private void deleteCommentsByUserId(PreparedStatement statement, long id) throws SQLException {
        statement.setLong(1, id);
        statement.execute();
    }

    private void deleteArticlesByUserId(PreparedStatement statement, long id) throws SQLException {
        statement.setLong(1, id);
        statement.execute();
    }

    private void deleteUserByUserId(PreparedStatement statement, long id) throws SQLException {
        statement.setLong(1, id);
        statement.execute();
    }


    public boolean deleteById(long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        if (connection == null) {
            return false;
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PROFILE_BY_USER_ID);
            deleteProfileByUserId(statement, id);
            statement = connection.prepareStatement(SQL_DELETE_SESSIONS_BY_USER_ID);
            deleteSessionByUserId(statement, id);
            statement = connection.prepareStatement(SQL_DELETE_COMMENTS_BY_USER_ID);
            deleteCommentsByUserId(statement, id);
            statement = connection.prepareStatement(SQL_DELETE_ARTICLES_BY_USER_ID);
            deleteArticlesByUserId(statement, id);
            statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            deleteUserByUserId(statement, id);
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    private long queryUserId(String sql, String value) {
        long id = -1;
        Connection connection = ConnectionPool.getInstance().getConnection();
        if (connection == null) {
            return -1;
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                id = set.getLong(1);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return id;
    }

    public boolean deleteByUuid(String uuid) {
        long id = queryUserId(SQL_QUERY_USER_ID_BY_UUID, uuid);
        return id != -1 && deleteById(id);
    }

    public boolean deleteByEmail(String email) {
        long id = queryUserId(SQL_QUERY_USER_ID_BY_EMAIL, email);
        return id != -1 && deleteById(id);
    }


    private String createUuid(User user) {
        return "";
    }

}
