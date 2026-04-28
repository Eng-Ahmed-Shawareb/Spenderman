package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsTransaction;

import java.util.List;
import java.util.Optional;

public class ClsTransactionDAO implements IRepository<ClsTransaction> {
    @Override
    public Optional<ClsTransaction> findByID(int ID) {
        return Optional.empty();
    }

    @Override
    public List<ClsTransaction> findAll() {
        return List.of();
    }

    @Override
    public boolean save(ClsTransaction entity) {
        return false;
    }

    @Override
    public boolean update(ClsTransaction entity) {
        return false;
    }

    @Override
    public boolean delete(int ID) {
        return false;
    }
}
