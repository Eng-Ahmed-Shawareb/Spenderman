package com.spenderman.Observer.Singleton;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObservable;
import com.spenderman.Observer.interfaceClass.IObserver;
import java.util.List;
import java.util.Observer;

/**
 * Class representing ClsAppEventBus.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsAppEventBus implements IObservable {

    private static ClsAppEventBus _instance = null;

    private List<IObserver> _observers;

    private ClsAppEventBus() {
        _observers = new java.util.ArrayList<>();
    }

    /**
     * Method to getInstance.
     *
     * @return the ClsAppEventBus
     */
    public static ClsAppEventBus getInstance() {
        if (_instance == null) {
            _instance = new ClsAppEventBus();
        }
        return _instance;
    }

    /**
     * Method to addObserver.
     *
     * @param observer the observer
     */
    @Override
    public void addObserver(IObserver observer) {
        _observers.add(observer);
    }

    /**
     * Method to removeObserver.
     *
     * @param observer the observer
     */
    @Override
    public void removeObserver(IObserver observer) {
        _observers.remove(observer);
    }

    /**
     * Method to notifyObservers.
     *
     * @param evenType the evenType
     * @param data the data
     */
    @Override
    public void notifyObservers(EnEvenType evenType, Object data) {
        for (var observer : _observers) {
            observer.update(evenType, data);
        }
    }

    /**
     * Method to clearAllObservers.
     */
    public void clearAllObservers() {
        if (_observers != null) {
            _observers.clear();
        }
    }
}
