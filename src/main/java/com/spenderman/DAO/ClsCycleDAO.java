package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsCycle;

import java.util.List;
import java.util.Optional;

public class ClsCycleDAO implements IRepository<ClsCycle> {
    @Override
    public Optional<ClsCycle> findByID(int ID) {
        return Optional.empty();
    }

    @Override
    public List<ClsCycle> findAll() {
        return List.of();
    }

    @Override
    public boolean save(ClsCycle entity) {
        return false;
    }

    @Override
    public boolean update(ClsCycle entity) {
        return false;
    }

    @Override
    public boolean delete(int ID) {
        return false;
    }
}
