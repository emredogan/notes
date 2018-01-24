package com.note.notepad.notes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by emredogan on 24/01/2018.
 */

public class InterceptCall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
            Toast.makeText(context, "Ringing", Toast.LENGTH_SHORT).show();
        }

        if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
        }

        if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {
            Toast.makeText(context, "Idle", Toast.LENGTH_SHORT).show();

            Intent startIntent = new Intent(context, MainActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startIntent);
        }



        /*

        try {
            Toast.makeText(context, "Ringing", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        */


        
    }
}
