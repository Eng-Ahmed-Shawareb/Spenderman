package com.spenderman.service;

import com.spenderman.DAO.ClsCategoryDAO;
import com.spenderman.DAO.InterfaceClass.ICateogryDAO;
import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsCategory;
import com.spenderman.model.ClsUser;

import java.util.List;

public class ClsCategoryService {
    private ICateogryDAO _categoryDAO;

    public ClsCategoryService() {
        this._categoryDAO = new ClsCategoryDAO();
    }
    public List<ClsCategory> getByUser(int userID){
        return _categoryDAO.getByUserID(userID);
    }
    
    public boolean createCategory(ClsCategory cat){
        return _categoryDAO.save(cat);
    }
    
    public boolean update(int cateogryID){

        ClsCategory cateogry=_categoryDAO.findByID(cateogryID).get();
        if(_categoryDAO.update(cateogry))
            return true;
        return false;

    }
    public boolean delete(int cateogryID){
        if(_categoryDAO.delete(cateogryID))
            return true;
        return false;
    }
}
