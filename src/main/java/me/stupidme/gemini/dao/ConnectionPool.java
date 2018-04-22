package me.stupidme.gemini.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    private static class Holder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    private HikariPool mPool;

    private ConnectionPool() {
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "gemini");
        props.setProperty("dataSource.password", "usergemini");
        props.setProperty("dataSource.databaseName", "geminidb_java");
        props.put("dataSource.logWriter", new PrintWriter(System.out));
        HikariConfig config = new HikariConfig(props);
        HikariDataSource dataSource = new HikariDataSource(config);
        mPool = new HikariPool(dataSource);
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = mPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
