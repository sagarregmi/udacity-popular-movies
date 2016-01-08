package net.regmi.popularmovies.controller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.load.engine.Resource;

import net.regmi.popularmovies.R;
import net.regmi.popularmovies.data.Movies;
import net.regmi.popularmovies.model.Movie;
import net.regmi.popularmovies.view.GridViewAdapter;

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
 * Created by sregmi1 on 1/8/16.
 */

public class SyncMoviesAsyncTask extends AsyncTask<String, Void, List<Movie>> {

    private final String LOG_TAG = SyncMoviesAsyncTask.class.getSimpleName();

    GridViewAdapter gridViewAdapter;
    MoviesCallback cb;
    Context context;

    public SyncMoviesAsyncTask(Movies m, GridViewAdapter gridViewAdapter, Context context) {
        this.gridViewAdapter = gridViewAdapter;
        cb = m;
        this.context = context;
    }

    private List<Movie> getMoviesDataFromJson(String jsonStr) throws JSONException {
        Log.v(LOG_TAG, "getMoviesDataFromJson()");
        JSONObject movieJson = new JSONObject(jsonStr);
        JSONArray movieArray = movieJson.getJSONArray("results");

        List<Movie> results = new ArrayList<>();

        for(int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            Movie movieModel = new Movie(movie);
            results.add(movieModel);
        }

        return results;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        Log.v(LOG_TAG, "doInBackground()");

        if (params.length == 0) {
            Log.v(LOG_TAG, "doInBackground() - returning null");
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonStr = null;

        try {
            final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY_PARAM, params[0])
                    .appendQueryParameter(API_KEY_PARAM,  context.getString(R.string.tmdb_api_key))
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMoviesDataFromJson(jsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        Log.v(LOG_TAG, "onPostExecute()");
        if (movies != null) {
            //movieArrayList.addAll(movies);
            //gridViewAdapter.notifyDataSetChanged();
            cb.updateMovies(movies);
            //moviesList.addMovies(movies);
            gridViewAdapter.notifyDataSetChanged();
        }
    }

    public interface MoviesCallback {
        public void updateMovies(List<Movie> movies);
    }
}