package com.spenderman;

import com.spenderman.DAO.ClsCycleDAO;
import com.spenderman.model.ClsCycle;
import java.util.List;

public class TestDB {
    public static void main(String[] args) {
        ClsCycleDAO dao = new ClsCycleDAO();
        List<ClsCycle> cycles = dao.findAll();
        for (ClsCycle c : cycles) {
            System.out.println("ID: " + c.get_cycleID() + ", State: " + c.get_state());
        }
    }
}
