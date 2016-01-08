package net.regmi.popularmovies.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.Glide;

import net.regmi.popularmovies.R;
import net.regmi.popularmovies.model.Movie;

/**
 * Created by sregmi1 on 1/4/16.
 */
public class GridViewAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    private final LayoutInflater mInflater;

    public GridViewAdapter(Context c, List<Movie> movieList) {
        super(c, 0, movieList);
        mContext = c;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.grid_item_movie, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }


        viewHolder = (ViewHolder) view.getTag();
        //viewHolder.imageView.setImageResource(mThumbIds[position]);
        Movie movie = getItem(position);
        viewHolder.titleView.setText("" + movie.getName());

        String image_url = "http://image.tmdb.org/t/p/w185" + movie.getImage();
        //viewHolder.imageView.setImageResource(movie.getImage());
        Glide.with(getContext()).load(image_url).into(viewHolder.imageView);
        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView titleView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.grid_item_image);
            titleView = (TextView) view.findViewById(R.id.grid_item_title);
        }
    }
}