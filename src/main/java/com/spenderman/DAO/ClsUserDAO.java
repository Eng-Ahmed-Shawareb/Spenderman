package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsUser;

import java.util.List;
import java.util.Optional;

public class ClsUserDAO implements IRepository<ClsUser> {
    @Override
    public Optional<ClsUser> findByID(int ID) {
        return Optional.empty();
    }

    @Override
    public List<ClsUser> findAll() {
        return List.of();
    }

    @Override
    public boolean save(ClsUser entity) {
        return false;
    }

    @Override
    public boolean update(ClsUser entity) {
        return false;
    }

    @Override
    public boolean delete(int ID) {
        return false;
    }
}
