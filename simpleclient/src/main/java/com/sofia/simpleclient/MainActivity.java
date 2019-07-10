package com.sofia.simpleclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sofia.mylib.ISimple;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    public static String TAG = "SimpleClient";

    private ISimple theService;

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent("com.sofia.simpserviceapp.SimpService")
                        .setPackage("com.sofia.simpserviceapp")
                ,this,BIND_AUTO_CREATE);
        Log.d (TAG, "Binding to the service");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bAdd = (Button) findViewById(R.id.buttonAdd);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int res = theService.add (10, 20);
                    Toast.makeText(getApplicationContext(), "Addition: " + res,
                            Toast.LENGTH_SHORT).show();
                    Log.d("tag","res:" + res);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        theService = ISimple.Stub.asInterface(iBinder);
        Log.d (TAG, "Service bound");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d (TAG, "Service unbound");
    }
}
