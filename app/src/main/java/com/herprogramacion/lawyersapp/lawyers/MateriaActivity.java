package com.herprogramacion.lawyersapp.lawyers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.*;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;

import com.herprogramacion.lawyersapp.R;

import java.util.Calendar;

public class MateriaActivity extends AppCompatActivity {

    public static final String EXTRA_MATERIA_ID = "extra_materia_id";
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //crearAlarma();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MateriaFragment fragment = (MateriaFragment)
                getSupportFragmentManager().findFragmentById(R.id.materia_container);

        if (fragment == null) {
            fragment = MateriaFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.materia_container, fragment)
                    .commit();
        }
    }
    /*protected void crearAlarma() {
        //ALARMA
        //alarmMgr = (AlarmManager)Context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr=(AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE) ;
        Intent intent = new Intent(getApplicationContext() , AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext() , 0, intent, 0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 54);

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, alarmIntent);
        //FIN ALARMA

    }*/
}
