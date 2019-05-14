package com.herprogramacion.lawyersapp.lawyers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.herprogramacion.lawyersapp.R;

public class TareaActivity extends AppCompatActivity {

    public static final String EXTRA_TAREA_ID = "extra_tarea_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TareaFragment fragment = (TareaFragment)
                getSupportFragmentManager().findFragmentById(R.id.tarea_container);

        if (fragment == null) {
            fragment = TareaFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.tarea_container, fragment)
                    .commit();
        }
    }
}

