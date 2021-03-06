package com.herprogramacion.lawyersapp.lawyerdetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.addeditlawyer.AddEditTareaActivity;
import com.herprogramacion.lawyersapp.data.Tarea;
import com.herprogramacion.lawyersapp.data.TareaDbHelper;
import com.herprogramacion.lawyersapp.lawyers.TareaActivity;
import com.herprogramacion.lawyersapp.lawyers.TareaFragment;

public class TareaDetailFragment extends Fragment {
    private static final String ARG_TAREA_ID = "tareaId";

    private String mTareaId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mId;
    private TextView mIdMateria;
    private TextView mIdUnidad;
    private TextView mNombre;
    private TextView mDescripcion;
    private TextView mFecha;
    private TextView mPorcentaje;
    private TextView mValor;
    private TextView mEstado;

    private TareaDbHelper mTareaDbHelper;


    public TareaDetailFragment() {
        // Required empty public constructor
    }

    public static TareaDetailFragment newInstance(String tareaId) {
        TareaDetailFragment fragment = new TareaDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TAREA_ID, tareaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTareaId = getArguments().getString(ARG_TAREA_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tarea_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mId = (TextView) root.findViewById(R.id.tv_id);
        mIdMateria = (TextView) root.findViewById(R.id.tv_idmateria);
        mIdUnidad = (TextView) root.findViewById(R.id.tv_idunidad);
        mNombre = (TextView) root.findViewById(R.id.tv_nombre);
        mDescripcion = (TextView) root.findViewById(R.id.tv_descripcion);
        mFecha = (TextView) root.findViewById(R.id.tv_fecha);
        mPorcentaje = (TextView) root.findViewById(R.id.tv_porcentaje);
        mValor = (TextView) root.findViewById(R.id.tv_valor);
        mEstado = (TextView) root.findViewById(R.id.tv_estado);
        mTareaDbHelper = new TareaDbHelper(getActivity());

        loadLawyer();

        return root;
    }

    private void loadLawyer() {
        new GetLawyerByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteLawyerTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TareaFragment.REQUEST_UPDATE_DELETE_TAREA) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showLawyer(Tarea tarea) {
        mCollapsingView.setTitle(tarea.getnombre());

        mId.setText(tarea.getId());
        mIdMateria.setText(tarea.getIdMateria());
        mIdUnidad.setText(tarea.getIdUnidad());
        mNombre.setText(tarea.getnombre());
        mDescripcion.setText(tarea.getdescripcion());
        mFecha.setText(tarea.getfecha());
        mPorcentaje.setText(tarea.getporcentaje());
        mValor.setText(tarea.getvalor());
        mEstado.setText(tarea.getestado());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditTareaActivity.class);
        intent.putExtra(TareaActivity.EXTRA_TAREA_ID, mTareaId);
        startActivityForResult(intent, TareaFragment.REQUEST_UPDATE_DELETE_TAREA);
    }

    private void showLawyersScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar la tarea", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mTareaDbHelper.getLawyerById(mTareaId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Tarea(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteLawyerTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mTareaDbHelper.deleteLawyer(mTareaId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showLawyersScreen(integer > 0);
        }

    }

}
