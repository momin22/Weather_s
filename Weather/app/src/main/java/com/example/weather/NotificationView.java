package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class NotificationView extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        setContentView(R.layout.activity_notification_view);
        textView = findViewById(R.id.textView);
        //getting the notification message
        String message=getIntent().getStringExtra("message");
        textView.setText(message);

        TextView receivedTxt = (TextView) findViewById(R.id.textView);
        receivedTxt.setText(receiveData());
        Linkify.addLinks(receivedTxt, Linkify.ALL);
    }

    private String receiveData()
    {
        String message = "";
        int id = 0;

        Bundle extras = getIntent().getExtras();// get intent data
        if (extras == null) {
            // If it is null then show error
            message = "Error";
        } else {
            // get id and message
            id = extras.getInt("notificationId");
            message = extras.getString("message");
        }
        message = "Notification Id : " + id + "n Message : " + message;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // remove the notification with the specific id
        mNotificationManager.cancel(id);

        return message;
    }
}