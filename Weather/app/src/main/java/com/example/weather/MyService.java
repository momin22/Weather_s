package com.example.weather;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

public class MyService extends IntentService {
    private TextView txtView;
    private NotificationReceiver nReceiver;
    public MyService() {
        super(MyService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        //retrieving data from the received intent
        int id = intent.getIntExtra("id",0);
        String message = intent.getStringExtra("msg");

        Log.i("Data  ", "id : "+id+" message : "+ message );
        //-----------------------------------------------


        //Do your long running task here

        Intent i = new Intent("com.example.notifyservice.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");

        i.putExtra("command","list");
        sendBroadcast(i);
        //------------------------------------------------

       /* //Broadcasting some data
        Intent myIntent = new Intent("MyServiceStatus");
        myIntent.putExtra("serviceMessage", "Task done");


        // Send broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(myIntent);*/

    }
    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("notification_event") + "\n" + txtView.getText();
            txtView.setText(temp);
        }
    }
}