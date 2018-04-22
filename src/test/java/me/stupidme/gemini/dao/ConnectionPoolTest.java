package me.stupidme.gemini.dao;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertNotEquals;

public class ConnectionPoolTest {

    @Test
    public void getInstance() {
        ConnectionPool pool = ConnectionPool.getInstance();
        assertNotEquals(null, pool);
    }

    @Test
    public void getConnection() {
        Connection connection = ConnectionPool.getInstance().getConnection();
        assertNotEquals(null, connection);
    }
}