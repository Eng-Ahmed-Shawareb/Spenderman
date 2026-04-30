package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsCategory;
import com.spenderman.model.ClsUser;

public class ClsCategoryService {
    private IRepository<ClsCategory> _categoryDAO;

    public ClsCategoryService(IRepository<ClsCategory> _categoryDAO) {
        this._categoryDAO = _categoryDAO;
    }
}
