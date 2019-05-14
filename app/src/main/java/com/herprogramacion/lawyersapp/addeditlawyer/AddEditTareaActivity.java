package com.herprogramacion.lawyersapp.addeditlawyer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.lawyers.TareaActivity;

public class AddEditTareaActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_TAREA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_tarea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String tareaId = getIntent().getStringExtra(TareaActivity.EXTRA_TAREA_ID);

        setTitle(tareaId == null ? "Añadir tarea" : "Editar tarea");

        AddEditTareaFragment addEditTareaFragment = (AddEditTareaFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_materia_container);
        if (addEditTareaFragment == null) {
            addEditTareaFragment = AddEditTareaFragment.newInstance(tareaId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_tarea_container, addEditTareaFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
