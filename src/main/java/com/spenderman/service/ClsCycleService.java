package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsCycle;

public class ClsCycleService {
    private IRepository<ClsCycle> cycle_DAO;

    public ClsCycleService(IRepository<ClsCycle> cycle_DAO) {
        this.cycle_DAO = cycle_DAO;
    }
}
