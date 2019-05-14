package com.herprogramacion.lawyersapp.lawyers;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.addeditlawyer.AddEditMateriaActivity;
import com.herprogramacion.lawyersapp.data.MateriaContract;
import com.herprogramacion.lawyersapp.data.MateriaDbHelper;
import com.herprogramacion.lawyersapp.lawyerdetail.MateriaDetailActivity;

public class MateriaFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_MATERIA = 2;

    private MateriaDbHelper mMateriaDbHelper;

    private ListView mMateriaList;
    private MateriaCursorAdapter mMateriaAdapter;
    private FloatingActionButton mAddButton;


    public MateriaFragment() {
        // Required empty public constructor
    }

    public static MateriaFragment newInstance() {
        return new MateriaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_materia, container, false);

        // Referencias UI
        mMateriaList = (ListView) root.findViewById(R.id.materia_list);
        mMateriaAdapter = new MateriaCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mMateriaList.setAdapter(mMateriaAdapter);

        // Eventos
        mMateriaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mMateriaAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(MateriaContract.MateriaEntry.ID));

                showDetailScreen(currentLawyerId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        //getActivity().deleteDatabase(MateriaDbHelper.DATABASE_NAME);

        // Instancia de helper
        mMateriaDbHelper = new MateriaDbHelper(getActivity());

        // Carga de datos
        loadLawyers();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditMateriaActivity.REQUEST_ADD_MATERIA:
                    showSuccessfullSavedMessage();
                    loadLawyers();
                    break;
                case REQUEST_UPDATE_DELETE_MATERIA:
                    loadLawyers();
                    break;
            }
        }
    }

    private void loadLawyers() {
        new LawyersLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Materia Agregada Exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditMateriaActivity.class);
        startActivityForResult(intent, AddEditMateriaActivity.REQUEST_ADD_MATERIA);
    }

    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(), MateriaDetailActivity.class);
        intent.putExtra(MateriaActivity.EXTRA_MATERIA_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_MATERIA);
    }

    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mMateriaDbHelper.getAllLawyers();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mMateriaAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
