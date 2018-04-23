package me.stupidme.gemini.dao;

import me.stupidme.gemini.domain.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {

    @Test
    public void insert() {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUuid("abcd2345abgthki");
        user.setUserName("allen");
        user.setPassword("ajd342l");
        user.setEmail("email@example.com");
        boolean success = userDao.insert(user);
        assertEquals(1, user.getId());
        assertEquals(true, success);
    }
}