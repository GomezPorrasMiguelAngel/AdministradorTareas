package com.herprogramacion.lawyersapp.addeditlawyer;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.data.Materia;
import com.herprogramacion.lawyersapp.data.MateriaDbHelper;

public class AddEditMateriaFragment extends Fragment{
    private static final String ARG_MATERIA_ID = "arg_materia_id";

    private String mmateriaId;

    private MateriaDbHelper mMateriaDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText midField;
    private TextInputEditText mnombreField;
    private TextInputEditText mprofesorField;
    private TextInputLayout midLabel;
    private TextInputLayout mnombreabel;
    private TextInputLayout mprofesorLabel;



    public AddEditMateriaFragment() {
        // Required empty public constructor
    }

    public static AddEditMateriaFragment newInstance(String materiaId) {
        AddEditMateriaFragment fragment = new AddEditMateriaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MATERIA_ID, materiaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mmateriaId = getArguments().getString(ARG_MATERIA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_materia, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        midField = (TextInputEditText) root.findViewById(R.id.et_id);
        mnombreField = (TextInputEditText) root.findViewById(R.id.et_nombre);
        mprofesorField = (TextInputEditText) root.findViewById(R.id.et_profesor);
        midLabel = (TextInputLayout) root.findViewById(R.id.til_id);
        mnombreabel = (TextInputLayout) root.findViewById(R.id.til_nombre);
        mprofesorLabel = (TextInputLayout) root.findViewById(R.id.til_profesor);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditMateria();
            }
        });

        mMateriaDbHelper = new MateriaDbHelper(getActivity());

        // Carga de datos
        if (mmateriaId != null) {
            loadMateria();
        }

        return root;
    }

    private void loadMateria() {
        new GetMateriaByIdTask().execute();
    }

    private void addEditMateria() {
        boolean error = false;

        String id = midField.getText().toString();
        String nombre = mnombreField.getText().toString();
        String profesor = mprofesorField.getText().toString();

        if (TextUtils.isEmpty(id)) {
            midLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(nombre)) {
            mnombreabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(profesor)) {
            mprofesorLabel.setError(getString(R.string.field_error));
            error = true;
        }

        

        if (error) {
            return;
        }

        Materia materia = new Materia(id, nombre, profesor);

        new AddEditMateriaTask().execute(materia);

    }

    private void showMateriasScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showMateria(Materia materia) {
        midField.setText(materia.getId());
        mnombreField.setText(materia.getnombre());
        mprofesorField.setText(materia.getprofesor());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetMateriaByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mMateriaDbHelper.getLawyerById(mmateriaId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showMateria(new Materia(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditMateriaTask extends AsyncTask<Materia, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Materia... materias) {
            if (mmateriaId != null) {
                return mMateriaDbHelper.updateLawyer(materias[0], mmateriaId) > 0;

            } else {
                return mMateriaDbHelper.saveLawyer(materias[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showMateriasScreen(result);
        }

    }
}
