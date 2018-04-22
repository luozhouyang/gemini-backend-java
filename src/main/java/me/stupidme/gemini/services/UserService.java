package me.stupidme.gemini.services;

import me.stupidme.gemini.domain.User;

import java.util.List;

/**
 * Define all operations to User.
 */
public interface UserService {

    /**
     * Insert a User.
     *
     * @param user 　the user
     * @return true if insertion is success, or false
     */
    boolean insertUser(User user);

    /**
     * Update a user.
     *
     * @param user user
     * @return true if update is success, or false
     */
    boolean updateUser(User user);

    /**
     * Update a user, if user does not exist then insert one.
     *
     * @param user user
     * @return true if update or insertion is success, or false.
     */
    boolean updateOrInsert(User user);

    /**
     * Delete a user.
     *
     * @param user user
     * @return true if deletion is success, or false.
     */
    boolean deleteUser(User user);

    /**
     * Find user by email.
     *
     * @param email email
     * @return user if found, or null
     */
    User findUserByEmail(String email);

    /**
     * Find user by user's id.
     *
     * @param id id
     * @return user if found, or null
     */
    User findUserById(long id);

    /**
     * Find user by uuid.
     *
     * @param uuid uuid
     * @return user if found, or null
     */
    User findUserByUuid(String uuid);

    /**
     * Find all users.
     *
     * @return users list if have any, or empty list
     */
    List<User> findAllUsers();
}
