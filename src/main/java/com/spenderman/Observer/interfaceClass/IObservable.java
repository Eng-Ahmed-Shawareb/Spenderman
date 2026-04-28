package com.spenderman.Observer.interfaceClass;

import com.spenderman.Observer.EvenEnum.EnEvenType;

public interface IObservable {
    public void addObserver(IObserver observer);
    public void removeObserver(IObserver observer);
    public void notifyObservers(EnEvenType evenType , Object data);
}
