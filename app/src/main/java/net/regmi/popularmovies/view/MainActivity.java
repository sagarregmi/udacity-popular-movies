package net.regmi.popularmovies.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.regmi.popularmovies.R;

/**
 * Created by sregmi1 on 12/23/15.
 */
public class MainActivity extends AppCompatActivity
                          implements MainActivityFragment.Callback {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        if(findViewById(R.id.fragment_container_2) != null) {
            MainActivityFragment frag1 = new MainActivityFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_2, frag1)
                    .commit();
        }
        */
    }

    @Override
    public void onItemSelected() {
        Log.v(LOG_TAG, "onItemSelected()");
        /*Toast.makeText(this, "" + "onItemSelected()",
                Toast.LENGTH_SHORT).show();
        */
    }
}
