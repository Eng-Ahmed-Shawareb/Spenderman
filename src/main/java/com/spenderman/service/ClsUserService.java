package com.spenderman.service;

import com.spenderman.DAO.ClsUserDAO;
import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.IRepositoryUsername;
import com.spenderman.model.ClsUser;

import java.util.Optional;

public class ClsUserService {
    private IRepositoryUsername user_DAO;

    public ClsUserService() {
        this.user_DAO = new ClsUserDAO();
    }

    public Optional<ClsUser> login(String username, String password) {
       return user_DAO.findByUserName(username)
                .filter(user -> password != null && password.equals(user.getPasswordHash()));
    }

    public boolean register(ClsUser user) {
        return user_DAO.save(user);
    }

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

    public void logout() {

    }
}
