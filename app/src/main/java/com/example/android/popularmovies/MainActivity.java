package com.example.android.popularmovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.Adapters.MovieDataAdapter;
import com.example.android.popularmovies.Loaders.MovieLoader;

import static com.example.android.popularmovies.Loaders.MovieLoader.MOVIE_LOADER_ID;

public class MainActivity extends AppCompatActivity implements MovieDataAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<Movie[]>{


    private TextView mTextViewError;
    private ProgressBar mProgBar;
    private final static String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mMovieGrid;
    private MovieDataAdapter mMovieDadapter;
    private static final String MENU_TOP_RATED = "Top Rated";
    private static final String MENU_MOST_POPULAR ="Most Popular" ;
    private static final String MENU_FAVOURITE = "Favourites";
    private static String MENU_SELECTED = "";

    private static final String MENU_SEARCH = "Search";

    private String movieToSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NetworkUtilities.buildUrlVideoOrReview("reviews","297762");
        setContentView(R.layout.activity_main);
        findInitialViews();
        //getConfigMethod taken from https://stackoverflow.com/questions/3663665/how-can-i-get-the-current-screen-orientation
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mMovieGrid.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        }
        else{
            mMovieGrid.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        }
        mMovieGrid.setHasFixedSize(true);
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID,null,this);
        String menuState = getString(R.string.empty_string);
        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.selected_menu_item_key))){
            menuState = savedInstanceState.getString(getString(R.string.selected_menu_item_key));
            if (menuState.equals(getString(R.string.menu_search))){
                if (savedInstanceState.containsKey(getString(R.string.movie_to_search_key))){
                    movieToSearch = savedInstanceState.getString(getString(R.string.movie_to_search_key));
                }
            }

        }
        else{
//            menuState = getString(R.string.menu_popular);
            MENU_SELECTED = getString(R.string.menu_popular);
        }
        loadMovieData();
    }
    //Use of menu Methods taken from Alex_Android_Notes topic 2.8
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String menuState = getString(R.string.empty_string);
        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey(getString(R.string.selected_menu_item_key))) {
            menuState = b.getString(getString(R.string.selected_menu_item_key));
        }
        if (menuState.equals(getString(R.string.menu_favourite))){
            loadMovieData();
        }
        

    }

    @Override
    protected void onStop() {
        super.onStop();
        getIntent().putExtra(getString(R.string.selected_menu_item_key),MENU_SELECTED);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.selected_menu_item_key),MENU_SELECTED);
        outState.putString(getString(R.string.movie_to_search_key),movieToSearch);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();

         if (itemID == R.id.menuSearch){
            DisplayPopUp();

        }else{
             setMenuSelected(itemID);
             loadMovieData();

         }
        return super.onOptionsItemSelected(item);
    }

    private void setMenuSelected(int itemId){
        switch (itemId){
            case R.id.menuTopRated:
                MENU_SELECTED = getString(R.string.menu_top_rated);
                break;
            case R.id.menuPopular:
                MENU_SELECTED = getString(R.string.menu_popular);
                break;
            case R.id.menuFavourite:
                MENU_SELECTED = getString(R.string.menu_favourite);
                break;
            case R.id.menu_action:
                MENU_SELECTED = getString(R.string.menu_action);
                break;
            case R.id.menu_adventure:
                MENU_SELECTED = getString(R.string.menu_adventure);
                break;
            case R.id.menu_animation:
                MENU_SELECTED = getString(R.string.menu_animation);
                break;
            case R.id.menu_comedy:
                MENU_SELECTED = getString(R.string.menu_comedy);
                break;
            case R.id.menu_crime:
                MENU_SELECTED = getString(R.string.menu_crime);
                break;
            case R.id.menu_documentary:
                MENU_SELECTED = getString(R.string.menu_documentary);
                break;
            case R.id.menu_drama:
                MENU_SELECTED = getString(R.string.menu_drama);
                break;
            case R.id.menu_family:
                MENU_SELECTED = getString(R.string.menu_family);
                break;
            case R.id.menu_fantasy:
                MENU_SELECTED = getString(R.string.menu_fantasy);
                break;

            case R.id.menu_history:
                MENU_SELECTED = getString(R.string.menu_history);
                break;
            case R.id.menu_horror:
                MENU_SELECTED = getString(R.string.menu_horror);
                break;
            case R.id.menu_music:
                MENU_SELECTED = getString(R.string.menu_music);
                break;
            case R.id.menu_mystery:
                MENU_SELECTED = getString(R.string.menu_mystery);
                break;
            case R.id.menu_romance:
                MENU_SELECTED = getString(R.string.menu_romance);
                break;
            case R.id.menu_sci_fi:
                MENU_SELECTED = getString(R.string.menu_sci_fi);
                break;
            case R.id.menu_thriller:
                MENU_SELECTED = getString(R.string.menu_thriller);
                break;
            case R.id.menu_war:
                MENU_SELECTED = getString(R.string.menu_war);
                break;
            case R.id.menu_western:
                MENU_SELECTED = getString(R.string.menu_western);
                break;

        }


    }

    private void DisplayPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Movie Search");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                movieToSearch = input.getText().toString();
                MENU_SELECTED = MENU_SEARCH;
                loadMovieData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void findInitialViews(){
        mTextViewError = (TextView) findViewById(R.id.tv_error);
        mProgBar = (ProgressBar) findViewById(R.id.pb_loading);
        mMovieGrid = (RecyclerView) findViewById(R.id.rv_movies);

    }

    private void loadMovieData(){
//        new RetrieveMovieDataTask(this).execute(selectedMenuItem);
        LoaderManager lm = getSupportLoaderManager();
        android.support.v4.content.Loader<Movie[]> movieLoader = lm.getLoader(MOVIE_LOADER_ID);
        if (movieLoader == null){
            lm.initLoader(MOVIE_LOADER_ID,null,this);
        }else{
            lm.restartLoader(MOVIE_LOADER_ID,null,this).forceLoad();
        }

    }

    private void showRecyclerViewHideError(){
        mMovieGrid.setVisibility(View.VISIBLE);
        mTextViewError.setVisibility(View.INVISIBLE);
    }
    private void showErrorViewHideRv(String msg){
        mMovieGrid.setVisibility(View.INVISIBLE);
        mTextViewError.setVisibility(View.VISIBLE);
        mTextViewError.setText(msg);
    }

    //onListItemClick method taken from Udacity Creating Android Apps chapter 3
    @Override
    public void onListItemClick(int clickedItemIndex,Movie m) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName() + m.getOrigTitle());
        Intent movieDetailsActivityIntent = new Intent(this,MovieDetailsActivity.class);
        movieDetailsActivityIntent.putExtra(getString(R.string.mov_key),m);
        startActivity(movieDetailsActivityIntent);
    }

    //onListItemClick method taken from Udacity Creating Android Apps chapter 3


    @Override
    public android.support.v4.content.Loader<Movie[]> onCreateLoader(final int id, final Bundle args) {
        MovieLoader ml;
        if (MENU_SELECTED.equals(MENU_SEARCH)){
            ml = new MovieLoader(this,MENU_SELECTED,movieToSearch);
        }else{
            ml = new MovieLoader(this,MENU_SELECTED);
        }

        mProgBar.setVisibility(View.VISIBLE);
        return ml;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Movie[]> loader, Movie[] movies) {
        mProgBar.setVisibility(View.INVISIBLE);
        boolean isFave = false;
        // TODO is FAve --- movies will not be null
        if (movies == null){
            showErrorViewHideRv(getString(R.string.tv_error_str));
        }else if(movies.length == 0 && MENU_SELECTED.equals(getString(R.string.menu_favourite))){
            showErrorViewHideRv(getString(R.string.no_favs_error));
        }else if(movies.length == 0 && MENU_SELECTED.equals(MENU_SEARCH)) {
            showErrorViewHideRv("The search returned no results");
        }else{
            showRecyclerViewHideError();
            Log.d(TAG, getString(R.string.on_post_execute) + movies.length);
            if (MENU_SELECTED.equals(getString(R.string.menu_favourite))){
                isFave = true;
            }
            mMovieDadapter = new MovieDataAdapter(movies.length,movies,getApplicationContext(),this,isFave);
            mMovieGrid.setAdapter(mMovieDadapter);
            Log.d(TAG, getString(R.string.adapter_set));
        }
        setTitle(MENU_SELECTED);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Movie[]> loader) {
        //Do nothing
    }

}
