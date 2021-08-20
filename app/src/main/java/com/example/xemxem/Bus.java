package com.example.xemxem;

import org.greenrobot.eventbus.EventBus;

public class Bus {

    static com.example.xemxem.Bus instance;

    public static com.example.xemxem.Bus getInstance() {
        if(instance==null) instance= new com.example.xemxem.Bus();
        return instance;
    }

    public void post(Object o){

        EventBus.getDefault().post(o);
    }
    public void register(Object o){
        if (!EventBus.getDefault().isRegistered(o))
            EventBus.getDefault().register(o);

    }

    public void unRegister(Object o){
        EventBus.getDefault().unregister(o);
    }
}
