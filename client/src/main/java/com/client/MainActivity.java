package com.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.service.IAdditionService;

public class MainActivity extends AppCompatActivity {

    private AdditionServiceConnection connection;
    private IAdditionService iAdditionService;

    private AppCompatEditText numEdit1;
    private AppCompatEditText numEdit2;
    private TextView sumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numEdit1 = (AppCompatEditText) findViewById(R.id.aet_num1);
        numEdit2 = (AppCompatEditText) findViewById(R.id.aet_num2);
        sumText = (TextView) findViewById(R.id.tv_sum);

        initService();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }

    public void handleClick(View view) {
        String num1 = numEdit1.getText().toString().trim();
        String num2 = numEdit2.getText().toString().trim();

        try {
            if (!TextUtils.isEmpty(num1) && !TextUtils.isEmpty(num2)) {
                int sum = iAdditionService.add(Integer.parseInt(num1), Integer.parseInt(num2));
                sumText.setText(String.valueOf(sum));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void initService() {
        connection = new AdditionServiceConnection();
        Intent intent = new Intent("com.service.AdditionService");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    class AdditionServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iAdditionService = IAdditionService.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iAdditionService = null;
            Toast.makeText(MainActivity.this, "Service disconnected", Toast.LENGTH_LONG).show();
        }
    }

    private void releaseService() {
        unbindService(connection);
        connection = null;
    }
}
