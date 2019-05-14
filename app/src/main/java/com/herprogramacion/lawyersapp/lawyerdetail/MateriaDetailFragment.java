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
import com.herprogramacion.lawyersapp.addeditlawyer.AddEditMateriaActivity;
import com.herprogramacion.lawyersapp.data.Materia;
import com.herprogramacion.lawyersapp.data.MateriaDbHelper;
import com.herprogramacion.lawyersapp.lawyers.MateriaActivity;
import com.herprogramacion.lawyersapp.lawyers.MateriaFragment;

public class MateriaDetailFragment extends Fragment {
    private static final String ARG_MATERIA_ID = "materiaId";

    private String mMateriaId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mId;
    private TextView mNombre;
    private TextView mProfesor;

    private MateriaDbHelper mMateriaDbHelper;


    public MateriaDetailFragment() {
        // Required empty public constructor
    }

    public static MateriaDetailFragment newInstance(String materiaId) {
        MateriaDetailFragment fragment = new MateriaDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MATERIA_ID, materiaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMateriaId = getArguments().getString(ARG_MATERIA_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_materia_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mId = (TextView) root.findViewById(R.id.tv_id);
        mNombre = (TextView) root.findViewById(R.id.tv_nombre);
        mProfesor = (TextView) root.findViewById(R.id.tv_profesor);

        mMateriaDbHelper = new MateriaDbHelper(getActivity());

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
        if (requestCode == MateriaFragment.REQUEST_UPDATE_DELETE_MATERIA) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showLawyer(Materia materia) {
        mCollapsingView.setTitle(materia.getnombre());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + materia.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mId.setText(materia.getId());
        mNombre.setText(materia.getnombre());
        mProfesor.setText(materia.getprofesor());

    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditMateriaActivity.class);
        intent.putExtra(MateriaActivity.EXTRA_MATERIA_ID, mMateriaId);
        startActivityForResult(intent, MateriaFragment.REQUEST_UPDATE_DELETE_MATERIA);
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
                "Error al eliminar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mMateriaDbHelper.getLawyerById(mMateriaId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Materia(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteLawyerTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mMateriaDbHelper.deleteLawyer(mMateriaId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showLawyersScreen(integer > 0);
        }

    }

}
