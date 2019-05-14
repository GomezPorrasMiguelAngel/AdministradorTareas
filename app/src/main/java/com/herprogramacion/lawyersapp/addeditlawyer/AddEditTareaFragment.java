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
import com.herprogramacion.lawyersapp.data.Tarea;
import com.herprogramacion.lawyersapp.data.TareaDbHelper;

public class AddEditTareaFragment extends Fragment {
    private static final String ARG_TAREA_ID = "arg_tarea_id";

    private String mtareaId;

    private TareaDbHelper mTareaDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText midField;
    private TextInputEditText midtareaField;
    private TextInputEditText midunidadField;
    private TextInputEditText mnombreField;
    private TextInputEditText mdescripcionField;
    private TextInputEditText mfechaField;
    private TextInputEditText mporcentajeField;
    private TextInputEditText mvalorField;
    private TextInputEditText mestadoField;
    private TextInputLayout midLabel;
    private TextInputLayout midtareaLabel;
    private TextInputLayout midunidadLabel;
    private TextInputLayout mnombreLabel;
    private TextInputLayout mdescripcionLabel;
    private TextInputLayout mfechaLabel;
    private TextInputLayout mporcentajeLabel;
    private TextInputLayout mvalorLabel;
    private TextInputLayout mestadoLabel;
   



    public AddEditTareaFragment() {
        // Required empty public constructor
    }

    public static AddEditTareaFragment newInstance(String tareaId) {
        AddEditTareaFragment fragment = new AddEditTareaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TAREA_ID, tareaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mtareaId = getArguments().getString(ARG_TAREA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_tarea, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        midField = (TextInputEditText) root.findViewById(R.id.et_id);
        midtareaField = (TextInputEditText) root.findViewById(R.id.et_id);
        midunidadField = (TextInputEditText) root.findViewById(R.id.et_idunidad);
        mnombreField = (TextInputEditText) root.findViewById(R.id.et_nombre);
        mdescripcionField = (TextInputEditText) root.findViewById(R.id.et_descripcion);
        mfechaField = (TextInputEditText) root.findViewById(R.id.et_fecha);
        mporcentajeField = (TextInputEditText) root.findViewById(R.id.et_porcentaje);
        mvalorField = (TextInputEditText) root.findViewById(R.id.et_valor);
        mestadoField = (TextInputEditText) root.findViewById(R.id.et_estado);

        midLabel = (TextInputLayout) root.findViewById(R.id.til_id);
        midtareaLabel = (TextInputLayout) root.findViewById(R.id.til_id);
        midunidadLabel = (TextInputLayout) root.findViewById(R.id.til_idunidad);
        mnombreLabel = (TextInputLayout) root.findViewById(R.id.til_nombre);
        mdescripcionLabel = (TextInputLayout) root.findViewById(R.id.til_descripcion);
        mfechaLabel = (TextInputLayout) root.findViewById(R.id.til_fecha);
        mporcentajeLabel = (TextInputLayout) root.findViewById(R.id.til_porcentaje);
        mvalorLabel = (TextInputLayout) root.findViewById(R.id.til_valor);
        mestadoLabel = (TextInputLayout) root.findViewById(R.id.til_estado);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditTarea();
            }
        });

        mTareaDbHelper = new TareaDbHelper(getActivity());

        // Carga de datos
        if (mtareaId != null) {
            loadTarea();
        }

        return root;
    }

    private void loadTarea() {
        new GetTareaByIdTask().execute();
    }

    private void addEditTarea() {
        boolean error = false;

        String id = midField.getText().toString();
        String idtarea = midtareaField.getText().toString();
        String idunidad = midunidadField.getText().toString();
        String nombre = mnombreField.getText().toString();
        String descripcion = mdescripcionField.getText().toString();
        String fecha = mfechaField.getText().toString();
        String porcentaje = mporcentajeField.getText().toString();
        String valor = mvalorField.getText().toString();
        String estado = mestadoField.getText().toString();

        if (TextUtils.isEmpty(id)) {
            midLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(idtarea)) {
            midtareaLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(idunidad)) {
            midunidadLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(nombre)) {
            mnombreLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(descripcion)) {
            mdescripcionLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(fecha)) {
            mfechaLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(porcentaje)) {
            mporcentajeLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(valor)) {
            mvalorLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(estado)) {
            mestadoLabel.setError(getString(R.string.field_error));
            error = true;
        }



        if (error) {
            return;
        }

        Tarea tarea = new Tarea(id,idtarea,idunidad, nombre, descripcion,fecha,porcentaje,valor,estado);

        new AddEditTareaTask().execute(tarea);

    }

    private void showTareasScreen(Boolean requery) {
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
                "Error al agregar nueva información de la tarea", Toast.LENGTH_SHORT).show();
    }

    private void showTarea(Tarea tarea) {
        midField.setText(tarea.getId());
        midtareaField.setText(tarea.getIdMateria());
        midunidadField.setText(tarea.getIdUnidad());
        mnombreField.setText(tarea.getnombre());
        mdescripcionField.setText(tarea.getdescripcion());
        mfechaField.setText(tarea.getfecha());
        mporcentajeField.setText(tarea.getporcentaje());
        mvalorField.setText(tarea.getvalor());
        mestadoField.setText(tarea.getestado());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar la Tarea", Toast.LENGTH_SHORT).show();
    }

    private class GetTareaByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mTareaDbHelper.getLawyerById(mtareaId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showTarea(new Tarea(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditTareaTask extends AsyncTask<Tarea, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Tarea... tareas) {
            if (mtareaId != null) {
                return mTareaDbHelper.updateLawyer(tareas[0], mtareaId) > 0;

            } else {
                return mTareaDbHelper.saveLawyer(tareas[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showTareasScreen(result);
        }

    }
}