package com.spenderman.service;

import com.spenderman.DAO.ClsCategoryDAO;
import com.spenderman.DAO.InterfaceClass.ICateogryDAO;
import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsCategory;
import com.spenderman.model.ClsUser;
import java.util.List;

/**
 * Class representing ClsCategoryService.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsCategoryService {

    private ICateogryDAO _categoryDAO;

    public ClsCategoryService() {
        this._categoryDAO = new ClsCategoryDAO();
    }

    /**
     * Method to getByUser.
     *
     * @param userID the userID
     * @return the List<ClsCategory>
     */
    public List<ClsCategory> getByUser(int userID) {
        return _categoryDAO.getByUserID(userID);
    }

    /**
     * Method to createCategory.
     *
     * @param cat the cat
     * @return the boolean
     */
    public boolean createCategory(ClsCategory cat) {
        return _categoryDAO.save(cat);
    }

    /**
     * Method to update.
     *
     * @param cateogryID the cateogryID
     * @return the boolean
     */
    public boolean update(int cateogryID) {
        ClsCategory cateogry = _categoryDAO.findByID(cateogryID).get();
        if (_categoryDAO.update(cateogry))
            return true;
        return false;
    }

    /**
     * Method to delete.
     *
     * @param cateogryID the cateogryID
     * @return the boolean
     */
    public boolean delete(int cateogryID) {
        if (_categoryDAO.delete(cateogryID))
            return true;
        return false;
    }
}
