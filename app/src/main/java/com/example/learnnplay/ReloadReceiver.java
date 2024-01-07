package com.example.learnnplay;

// ReloadReceiver.java
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Start the ColorsRecognition activity
        Intent reloadIntent = new Intent(context, ColorsRecognition.class);
        Intent reloadIntent2 = new Intent(context, ShapesRecognition.class);
        reloadIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(reloadIntent2);
        reloadIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(reloadIntent);
    }
}
