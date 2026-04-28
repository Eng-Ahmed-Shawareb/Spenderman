package com.spenderman.Observer.interfaceClass;

import com.spenderman.Observer.EvenEnum.EnEvenType;

public interface IObserver {
    public void update(EnEvenType evenType , Object data);
}
