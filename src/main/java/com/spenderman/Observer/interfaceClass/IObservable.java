package com.spenderman.Observer.interfaceClass;

import com.spenderman.Observer.EvenEnum.EnEvenType;

/**
 * Class representing IObservable.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface IObservable {

    /**
     * Method to addObserver.
     *
     * @param observer the observer
     */
    public void addObserver(IObserver observer);

    /**
     * Method to removeObserver.
     *
     * @param observer the observer
     */
    public void removeObserver(IObserver observer);

    /**
     * Method to notifyObservers.
     *
     * @param evenType the evenType
     * @param data the data
     */
    public void notifyObservers(EnEvenType evenType, Object data);
}
