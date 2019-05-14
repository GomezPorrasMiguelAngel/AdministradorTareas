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
import com.herprogramacion.lawyersapp.addeditlawyer.AddEditTareaActivity;
import com.herprogramacion.lawyersapp.data.TareaContract;
import com.herprogramacion.lawyersapp.data.TareaDbHelper;
import com.herprogramacion.lawyersapp.lawyerdetail.TareaDetailActivity;

public class TareaFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_TAREA = 2;

    private TareaDbHelper mTareaDbHelper;

    private ListView mTareaList;
    private TareaCursorAdapter mTareaAdapter;
    private FloatingActionButton mAddButton;


    public TareaFragment() {
        // Required empty public constructor
    }

    public static TareaFragment newInstance() {
        return new TareaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tarea, container, false);

        // Referencias UI
        mTareaList = (ListView) root.findViewById(R.id.tarea_list);
        mTareaAdapter = new TareaCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mTareaList.setAdapter(mTareaAdapter);

        // Eventos
        mTareaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mTareaAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(TareaContract.TareaEntry.ID));

                showDetailScreen(currentLawyerId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        //getActivity().deleteDatabase(TareaDbHelper.DATABASE_NAME);

        // Instancia de helper
        mTareaDbHelper = new TareaDbHelper(getActivity());

        // Carga de datos
        loadLawyers();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditTareaActivity.REQUEST_ADD_TAREA:
                    showSuccessfullSavedMessage();
                    loadLawyers();
                    break;
                case REQUEST_UPDATE_DELETE_TAREA:
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
                "Tarea Agregada Exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditTareaActivity.class);
        startActivityForResult(intent, AddEditTareaActivity.REQUEST_ADD_TAREA);
    }

    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(), TareaDetailActivity.class);
        intent.putExtra(TareaActivity.EXTRA_TAREA_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_TAREA);
    }

    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mTareaDbHelper.getAllLawyers();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mTareaAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
