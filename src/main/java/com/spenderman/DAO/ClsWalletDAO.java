package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsWallet;

import java.util.List;
import java.util.Optional;

public class ClsWalletDAO implements IRepository<ClsWallet> {
    @Override
    public Optional<ClsWallet> findByID(int ID) {
        return Optional.empty();
    }

    @Override
    public List<ClsWallet> findAll() {
        return List.of();
    }

    @Override
    public boolean save(ClsWallet entity) {
        return false;
    }

    @Override
    public boolean update(ClsWallet entity) {
        return false;
    }

    @Override
    public boolean delete(int ID) {
        return false;
    }
}
