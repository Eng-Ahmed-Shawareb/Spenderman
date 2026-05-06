package com.spenderman.service;

import com.spenderman.DAO.ClsUserDAO;
import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.IRepositoryUsername;
import com.spenderman.model.ClsUser;
import java.util.Optional;

/**
 * Class representing ClsUserService.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsUserService {

    private IRepositoryUsername user_DAO;

    public ClsUserService() {
        this.user_DAO = new ClsUserDAO();
    }

    /**
     * Method to login.
     *
     * @param username the username
     * @param password the password
     * @return the Optional<ClsUser>
     */
    public Optional<ClsUser> login(String username, String password) {
        return user_DAO.findByUserName(username).filter(user -> password != null && password.equals(user.getPasswordHash()));
    }

    /**
     * Method to register.
     *
     * @param user the user
     * @return the boolean
     */
    public boolean register(ClsUser user) {
        return user_DAO.save(user);
    }

    /**
     * Method to changePassword.
     *
     * @param userID the userID
     * @param oldPassword the oldPassword
     * @param newPassword the newPassword
     * @return the boolean
     */
    public boolean changePassword(int userID, String oldPassword, String newPassword) {
        Optional<ClsUser> oUser = user_DAO.findByID(userID);
        if (oUser.isEmpty()) {
            return false;
        }
        ClsUser user = oUser.get();
        if (!oldPassword.equals(user.getPasswordHash()) || oldPassword.equals(newPassword)) {
            return false;
        }
        user.setPasswordHash(newPassword);
        return user_DAO.update(user);
    }

    /**
     * Method to logout.
     */
    public void logout() {
    }
}
