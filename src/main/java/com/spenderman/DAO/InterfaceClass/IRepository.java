package com.spenderman.DAO.InterfaceClass;

import java.util.List;
import java.util.Optional;

public interface IRepository <T> {
    public Optional<T> findByID(int ID);
    public List<T> findAll();
    public boolean save(T entity);
    public boolean update(T entity);
    public boolean delete(int ID);
}
