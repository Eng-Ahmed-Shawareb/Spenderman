package com.spenderman.Observer.Singleton;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObservable;
import com.spenderman.Observer.interfaceClass.IObserver;

import java.util.List;
import java.util.Observer;

public class ClsAppEventBus implements IObservable {
    private static ClsAppEventBus _instance = null;
    private List<IObserver> _observers;

    private ClsAppEventBus(){
        _observers = new java.util.ArrayList<>();
    }

    public static ClsAppEventBus getInstance(){
        if(_instance == null){
            _instance = new ClsAppEventBus();
        }
        return _instance;
    }

    @Override
    public void addObserver(IObserver observer) {
        _observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        _observers.remove(observer);
    }

    @Override
    public void notifyObservers(EnEvenType evenType, Object data) {
            for(var observer : _observers){
                observer.update(evenType , data);
            }
    }

    public void clearAllObservers() {
        if (_observers != null) {
            _observers.clear();
        }
    }
}
