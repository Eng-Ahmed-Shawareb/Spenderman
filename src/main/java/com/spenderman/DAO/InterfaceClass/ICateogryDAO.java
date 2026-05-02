package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsCategory;

import java.util.List;

public interface ICateogryDAO extends IRepository<ClsCategory> {
    public List<ClsCategory> getByUserID(int userID);
}
