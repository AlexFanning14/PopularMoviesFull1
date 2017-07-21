package com.example.android.popularmovies.Utils;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by User on 16-May-17.
 */

public class NetworkUtilities {
    private static final String TAG = NetworkUtilities.class.getSimpleName();


    private static final String MOVIE_DB_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular";

    private static final String MOVIE_DB_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated";

    private static final String API_KEY_PARAM = "api_key";

    private static final  String QUERY_PARAM = "query";
    private static final  String SORT_BY_PARAM = "sort_by";
    private static final  String SORT_BY_VAL = "most_popular";
    private static String api_key_val;

    private static final  String WITH_GENRE_PARAM = "with_genres";


    private static final String BASE_URL_MOVIE = "http://api.themoviedb.org/3/movie";

    private static final String ENDPOINT_VIDEOS = "videos";

    private static final String ENDPOINT_REVIEWS = "reviews";

    public static URL buildUrl(String sortByMovies,Context c) {
        //Build URL method snippets taken from Udacity Android Dev chaapter 2
        api_key_val = c.getString(R.string.mov_api_key);

        String movie_base_url = c.getString(R.string.empty_string);
        if (sortByMovies.equals(c.getString(R.string.menu_popular)) ){
            movie_base_url = MOVIE_DB_URL_POPULAR;
        }else if (sortByMovies.equals(c.getString(R.string.menu_top_rated))){
            movie_base_url = MOVIE_DB_URL_TOP_RATED;
        }

        Uri builtUri = Uri.parse(movie_base_url).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, api_key_val)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName() + url);
        return url;
    }



    public static URL buildGenreUrl(String sortByMovies,Context c) {
        //Build URL method snippets taken from Udacity Android Dev chaapter 2
        api_key_val = c.getString(R.string.mov_api_key);

        String movie_base_url = c.getString(R.string.url_base_moviedb_genre);
        Map<String,String> genMap = new GenreHashMapUtils().getGenreMap(c);
        String genID = genMap.get(sortByMovies);


        Uri builtUri = Uri.parse(movie_base_url).buildUpon()
                .appendQueryParameter(WITH_GENRE_PARAM,genID)
                .appendQueryParameter(API_KEY_PARAM, api_key_val)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName() + url);
        return url;
    }


    public static URL buildSearchUrl(Context c,String searchTerm) {
        //Build URL method snippets taken from Udacity Android Dev chaapter 2
        api_key_val = c.getString(R.string.mov_api_key);

        String movie_base_url = c.getString(R.string.url_base_moviedb_search);


        Uri builtUri = Uri.parse(movie_base_url).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, api_key_val)
                .appendQueryParameter(QUERY_PARAM,searchTerm)
                .appendQueryParameter(SORT_BY_PARAM,SORT_BY_VAL)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName() + url);
        return url;
    }



    public static String getJSONresponseFromUrl(URL url) throws IOException {
        //getJSONresponseFromURL method snippets taken from Udacity Android Dev chaapter 2
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();

        }
    }

    public static URL buildUrlVideoOrReview(String endpoint, String movID,Context c){
        api_key_val = c.getString(R.string.mov_api_key);

        String selected_movie_endpoint = c.getString(R.string.empty_string);
        if (endpoint.equals(ENDPOINT_VIDEOS) ){
            selected_movie_endpoint = ENDPOINT_VIDEOS;
        }else if (endpoint.equals(ENDPOINT_REVIEWS) ){
            selected_movie_endpoint = ENDPOINT_REVIEWS;
        }

        Uri builtUri = Uri.parse(BASE_URL_MOVIE).buildUpon()
                .appendPath(movID)
                .appendPath(selected_movie_endpoint)
                .appendQueryParameter(API_KEY_PARAM, api_key_val)
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }








}
