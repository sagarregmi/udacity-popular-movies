package net.regmi.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sregmi1 on 1/6/16.
 */
public class Movie implements Parcelable{

    public static final String LOG_TAG = Movie.class.getSimpleName();

    private int id;
    private String title; // original_title
    private String image; // poster_path
    private String image2; // backdrop_path
    private String overview;
    private int rating; // vote_average
    private String date; // release_date

    public Movie(int id,
                 String title,
                 int image_path,
                 int rating,
                 String release_date,
                 String overview ) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.date = release_date;
        this.overview = overview;
    }

    public Movie(JSONObject movie) throws JSONException {
        this.id = movie.getInt("id");
        this.title = movie.getString("original_title");
        this.image = movie.getString("poster_path");
        this.image2 = movie.getString("backdrop_path");
        this.overview = movie.getString("overview");
        this.rating = movie.getInt("vote_average");
        this.date = movie.getString("release_date");

        Log.v(LOG_TAG, "id = " + this.id + "\n" +
        "title = " + this.title + "\n" +
        "image = " + this.image + "\n" +
        "bakdrop image path = " + this.image2 + "\n" +
        "overview = " + this.overview + "\n" +
        "rating = " + this.rating + "\n" +
        "date = " + this.date) ;
    }

    public String getName() {
        return title;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(image2);
        dest.writeString(overview);
        dest.writeInt(rating);
        dest.writeString(date);

    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        image2 = in.readString();
        overview = in.readString();
        rating = in.readInt();
        date = in.readString();
    }

}
