package me.stupidme.gemini.services;

import me.stupidme.gemini.domain.User;

import java.util.List;

/**
 * A default implementation of UserService.
 */
public class DefaultUserService implements UserService {

    @Override
    public boolean insertUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean updateOrInsert(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserById(long id) {
        return null;
    }

    @Override
    public User findUserByUuid(String uuid) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }
}
