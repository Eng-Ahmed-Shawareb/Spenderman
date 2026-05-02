package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.IRepositoryUsername;
import com.spenderman.model.ClsUser;

import java.util.Optional;

public class ClsUserService {
    private IRepositoryUsername user_DAO;

    public ClsUserService(IRepositoryUsername user_DAO) {
        this.user_DAO = user_DAO;
    }
    Optional<ClsUser> login(String username,String password){
        Optional<ClsUser>opAccount=user_DAO.findByUserName(username);
        if(opAccount.isEmpty()){
            throw new RuntimeException("wrong user name or password");

        }
        ClsUser user=opAccount.get();
        if(user.getPasswordHash().equals(password)){
            return opAccount;
        }
        else{
            throw new RuntimeException("Eror Password Please,Enter a valid password");

        }
    }
    boolean register(ClsUser user){


        if(user_DAO.save(user)){
            return true;
        }
        else {
            throw new RuntimeException("The username is used");
        }
    }

    boolean changePassowrd(int userID,String oldPassword,String newPassowrd){
        ClsUser user=user_DAO.findByID(userID).get();
        if(!oldPassword.equals(user.getPasswordHash())){
            throw new RuntimeException("please enter old password correctly.");
        }
        if(oldPassword.equals(newPassowrd)){
            throw new RuntimeException("the old password equals with new password");
        }
        return true;
    }
    void logout(){

    }

}
