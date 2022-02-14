package com.example.weather;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class MainActivity extends AppCompatActivity {
    private TextView Temperature_View,Hydrogen_view,Js_view;
    private Handler mHandler;
    private int messageCount = 0;
    private static Uri alarmSound;
    private ImageView imageView;
    private final long[] pattern = { 100, 300, 300, 300 };
    private NotificationManager mNotificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        this.mHandler = new Handler();
        m_Runnable.run();
        Temperature_View=(TextView) findViewById(R.id.Temperature_View);
        Hydrogen_view=(TextView) findViewById(R.id.Hydrogen_view);
        Js_view=(TextView) findViewById(R.id.Js_view);
        imageView=(ImageView)findViewById(R.id.j);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    private final Runnable m_Runnable = new Runnable(){
        @Override
        public void run() {
            MainActivity.this.mHandler.postDelayed(m_Runnable, 5000);
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference uidRef = rootRef.child("gas");
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String Carbon_Monoxide = dataSnapshot.child("Carbon_Monoxide").getValue().toString();
                    String Hydrogen_Gas = dataSnapshot.child("Hydrogen_Gas").getValue().toString();
                    String Natural_gas = dataSnapshot.child("Natural_gas").getValue().toString();
                    Temperature_View.setText(Carbon_Monoxide);
                    Hydrogen_view.setText(Hydrogen_Gas);
                    Js_view.setText(Natural_gas);
                    if(Temperature_View.equals("high")){
                        showNotification();
                    }
                    else if(Hydrogen_view.equals("high")){
                        showNotification();
                    }
                    else if(Js_view.equals("high")){
                        showNotification();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            uidRef.addListenerForSingleValueEvent(valueEventListener);
        }
    };
    protected void showNotification() {
        Log.i("Start", "notification");
        // Invoking the default notification service
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
        mBuilder.setContentTitle("Weather ");
        mBuilder.setContentText("Weather High");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setSmallIcon(R.drawable.logo1);
        //Increment message count when a new message arrives
        mBuilder.setNumber(++messageCount);
        mBuilder.setSound(alarmSound);
        mBuilder.setVibrate(pattern);
        // Explicit intent to open notifactivity
        Intent i = new Intent(MainActivity.this,NotificationView.class);
        i.putExtra("notificationId", 111);
        i.putExtra("message", "Weather High");
        // Task builder to maintain task for pending intent
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(i);
        //PASS REQUEST CODE AND FLAG
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(111,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(111, mBuilder.build());

    }


}