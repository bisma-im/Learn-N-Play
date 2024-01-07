package com.example.learnnplay;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;
public class AirplaneModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                    boolean isAirplaneModeOn =
                            intent.getBooleanExtra("state", false);
                    String message = isAirplaneModeOn ? "Airplane mode turned on" : "Airplane mode turned off";
                    showToast(context, message);
                    break;
            }
        }
    }

    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}