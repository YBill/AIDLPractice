package com.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Bill on 2017/1/19.
 */

public class AdditionService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IAdditionService.Stub() {

            @Override
            public int add(int num1, int num2) throws RemoteException {
                return (num1 + num2);
            }
        };

    }

}
