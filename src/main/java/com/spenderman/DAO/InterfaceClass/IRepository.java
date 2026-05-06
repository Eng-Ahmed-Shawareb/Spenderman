package com.spenderman.DAO.InterfaceClass;

import java.util.List;
import java.util.Optional;

/**
 * Class representing IRepository.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface IRepository<T> {

    /**
     * Method to findByID.
     *
     * @param ID the ID
     * @return the Optional<T>
     */
    public Optional<T> findByID(int ID);

    /**
     * Method to findAll.
     *
     * @return the List<T>
     */
    public List<T> findAll();

    /**
     * Method to save.
     *
     * @param entity the entity
     * @return the boolean
     */
    public boolean save(T entity);

    /**
     * Method to update.
     *
     * @param entity the entity
     * @return the boolean
     */
    public boolean update(T entity);

    /**
     * Method to delete.
     *
     * @param ID the ID
     * @return the boolean
     */
    public boolean delete(int ID);
}
