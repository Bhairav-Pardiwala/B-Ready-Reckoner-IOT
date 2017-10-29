package com.psyzone.bhairavpardiwala.hellonotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Bhairav Pardiwala on 28-10-2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String allOk="allOk";
        boolean bln=false;
        if(intent.getExtras().size()>0)
        {
            bln=intent.getExtras().getBoolean("allOk");
            allOk = String.valueOf(bln);
        }

        if(bln)
        {
            //Switch One GPIO ON and Other OFF
            try {
               // PeripheralManagerService manager = new PeripheralManagerService();
                //mGpio = manager.openGpio(GPIO_NAME);
            } catch (Exception e) {
                Log.w(TAG, "Unable to access GPIO", e);
            }
        }
        else
        {
            //Switch ONe GPIO Off and Other ON
        }
        Log.d(TAG, allOk);
        Toast.makeText(context, allOk, Toast.LENGTH_SHORT).show();
    }
}
