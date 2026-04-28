package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsSavingGoal;

import java.util.List;
import java.util.Optional;

public class ClsSavingGoalDAO implements IRepository<ClsSavingGoal> {
    @Override
    public Optional<ClsSavingGoal> findByID(int ID) {
        return Optional.empty();
    }

    @Override
    public List<ClsSavingGoal> findAll() {
        return List.of();
    }

    @Override
    public boolean save(ClsSavingGoal entity) {
        return false;
    }

    @Override
    public boolean update(ClsSavingGoal entity) {
        return false;
    }

    @Override
    public boolean delete(int ID) {
        return false;
    }
}
