package com.example.android.popularmovies.Utils;

import android.content.Context;
import android.content.res.Resources;

import com.example.android.popularmovies.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex.fanning on 19/07/2017.
 */

public class GenreHashMapUtils {
    public static final String TAG = GenreHashMapUtils.class.getSimpleName();

    public GenreHashMapUtils(){

    }

    public Map<String,String> getGenreMap(Context c){
        Map<String,String> result = new HashMap<String,String>();
        result.put(c.getString(R.string.menu_action), c.getString(R.string.menu_action_id));
        result.put(c.getString(R.string.menu_adventure), c.getString(R.string.menu_adventure_id));
        result.put(c.getString(R.string.menu_animation), c.getString(R.string.menu_animation_id));
        result.put(c.getString(R.string.menu_comedy), c.getString(R.string.menu_comedy_id));
        result.put(c.getString(R.string.menu_crime), c.getString(R.string.menu_crime_id));
        result.put(c.getString(R.string.menu_documentary), c.getString(R.string.menu_documentary_id));
        result.put(c.getString(R.string.menu_drama), c.getString(R.string.menu_drama_id));
        result.put(c.getString(R.string.menu_family), c.getString(R.string.menu_family_id));
        result.put(c.getString(R.string.menu_fantasy), c.getString(R.string.menu_fantasy_id));
        result.put(c.getString(R.string.menu_history), c.getString(R.string.menu_history_id));
        result.put(c.getString(R.string.menu_horror), c.getString(R.string.menu_horror_id));
        result.put(c.getString(R.string.menu_music), c.getString(R.string.menu_music_id));
        result.put(c.getString(R.string.menu_mystery), c.getString(R.string.menu_mystery_id));
        result.put(c.getString(R.string.menu_romance), c.getString(R.string.menu_romance_id));
        result.put(c.getString(R.string.menu_sci_fi), c.getString(R.string.menu_sci_fi_id));
        result.put(c.getString(R.string.menu_tv_movie), c.getString(R.string.menu_tv_movie_id));
        result.put(c.getString(R.string.menu_thriller), c.getString(R.string.menu_thriller_id));
        result.put(c.getString(R.string.menu_war), c.getString(R.string.menu_war_id));
        result.put(c.getString(R.string.menu_western), c.getString(R.string.menu_western_id));
        return Collections.unmodifiableMap(result);
    }


}
