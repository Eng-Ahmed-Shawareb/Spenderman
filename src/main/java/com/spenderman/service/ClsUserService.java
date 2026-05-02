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
        return user_DAO.findByUserName(username);
    }

    public boolean register(ClsUser user) {
        return user_DAO.save(user);
    }

    public boolean changePassowrd(int userID, String oldPassword, String newPassowrd) {
        ClsUser user = user_DAO.findByID(userID).get();
        if (!oldPassword.equals(user.getPasswordHash())) {
            throw new RuntimeException("please enter old password correctly.");
        }
        if (oldPassword.equals(newPassowrd)) {
            throw new RuntimeException("the old password equals with new password");
        }
        return true;
    }

    public void logout() {

    }
}
