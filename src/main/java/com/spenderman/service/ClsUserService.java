package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsUser;

public class ClsUserService {
    private IRepository<ClsUser> user_DAO;

    public ClsUserService(IRepository<ClsUser> user_DAO) {
        this.user_DAO = user_DAO;
    }
}
