package com.spenderman.DAO;


import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsCategory;

import java.util.List;
import java.util.Optional;

public class ClsCategoryDAO implements IRepository<ClsCategory> {
    @Override
    public Optional<ClsCategory> findByID(int ID) {
        return Optional.empty();
    }

    @Override
    public List<ClsCategory> findAll() {
        return List.of();
    }

    @Override
    public boolean save(ClsCategory entity) {
        return false;
    }

    @Override
    public boolean update(ClsCategory entity) {
        return false;
    }

    @Override
    public boolean delete(int ID) {
        return false;
    }
}
