package net.regmi.popularmovies.data;

import android.content.Context;
import android.util.Log;

import net.regmi.popularmovies.controller.SyncMoviesAsyncTask;
import net.regmi.popularmovies.model.Movie;
import net.regmi.popularmovies.view.GridViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sregmi1 on 1/8/16.
 */
public class Movies implements SyncMoviesAsyncTask.MoviesCallback{
    private final String LOG_TAG = Movies.class.getSimpleName();
    List<Movie> movieArrayList = new ArrayList<Movie>();

    public Movies() {
        Log.v(LOG_TAG, "Movies() Constructor");
    }

    public List<Movie> getMovies() {
        Log.v(LOG_TAG, "getMovies()");
        return movieArrayList;
    }

    public void addMovies(List<Movie> movies ) {
        Log.v(LOG_TAG, "addMovies()");
        movieArrayList.addAll(movies);
    }

    public int getSize() {
        Log.v(LOG_TAG, "getSize()");
        return movieArrayList.size();
    }

    public void updateMoviesInfo(GridViewAdapter gridViewAdapter, Context context, String sort_by) {
        Log.v(LOG_TAG, "updateMoviesInfo()");
        new SyncMoviesAsyncTask(this, gridViewAdapter, context).execute(sort_by);
    }

    @Override
    public void updateMovies(List<Movie> movies) {
        addMovies(movies);
    }
}
