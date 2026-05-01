package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsUser;

import java.util.Optional;

public interface IRepositoryUsername extends IRepository<ClsUser>{

    public Optional<ClsUser> findByUserName(String userName);
}
