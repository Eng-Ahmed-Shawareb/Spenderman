package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsCycle;

import java.util.List;
import java.util.Optional;

public interface ICycleDAO extends IRepository<ClsCycle> {
    List<ClsCycle> findByUserID(int userID);

    Optional<ClsCycle> findActiveCycle(int userID);

}
