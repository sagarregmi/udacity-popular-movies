package net.regmi.popularmovies.view;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import net.regmi.popularmovies.R;
import net.regmi.popularmovies.controller.SyncMoviesAsyncTask;
import net.regmi.popularmovies.data.Movies;
import net.regmi.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sregmi1 on 12/23/15.
 */
public class MainActivityFragment extends Fragment{

    public static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private static final String SORT_SETTING_KEY = "sort_setting";
    private static final String POPULARITY_DESC = "popularity.desc";
    private static final String RATING_DESC = "vote_average.desc";
    private static final String FAVORITE = "favorite";
    private static final String MOVIES_KEY = "movies";

    GridView gridview;
    GridViewAdapter gridViewAdapter;

    private static Movies mMovies = new Movies();

    public MainActivityFragment() {
        Log.v(LOG_TAG, "MainActivityFragment constructor");
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        void onItemSelected();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_sort_most_popular:


            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView() savedInstanceState =" + savedInstanceState + " movieArrayList size = " + mMovies.getSize());
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview_movies);


        gridViewAdapter = new GridViewAdapter(getActivity(), mMovies.getMovies());
        gridview.setAdapter(gridViewAdapter);
        if(  savedInstanceState == null ) {
            mMovies.updateMoviesInfo(gridViewAdapter, getContext(), POPULARITY_DESC ); // create mMoviesList
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
                ((Callback) getActivity()).onItemSelected();
            }
        });

        return view;
    }
}
