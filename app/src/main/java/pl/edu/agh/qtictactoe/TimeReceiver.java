package pl.edu.agh.qtictactoe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimeReceiver extends BroadcastReceiver {

    public TimeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Time changed", Toast.LENGTH_SHORT).show();
    }

}
