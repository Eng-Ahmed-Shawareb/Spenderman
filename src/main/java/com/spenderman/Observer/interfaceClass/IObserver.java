package com.spenderman.Observer.interfaceClass;

import com.spenderman.Observer.EvenEnum.EnEvenType;

/**
 * Class representing IObserver.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface IObserver {

    /**
     * Method to update.
     *
     * @param evenType the evenType
     * @param data the data
     */
    public void update(EnEvenType evenType, Object data);
}
