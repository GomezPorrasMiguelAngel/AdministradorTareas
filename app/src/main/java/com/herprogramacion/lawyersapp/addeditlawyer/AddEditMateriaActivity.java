package com.herprogramacion.lawyersapp.addeditlawyer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.lawyers.MateriaActivity;

public class AddEditMateriaActivity extends AppCompatActivity{
    public static final int REQUEST_ADD_MATERIA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_materia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String materiaId = getIntent().getStringExtra(MateriaActivity.EXTRA_MATERIA_ID);

        setTitle(materiaId == null ? "Añadir materia" : "Editar materia");

        AddEditMateriaFragment addEditMateriaFragment = (AddEditMateriaFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_materia_container);
        if (addEditMateriaFragment == null) {
            addEditMateriaFragment = AddEditMateriaFragment.newInstance(materiaId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_materia_container, addEditMateriaFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
