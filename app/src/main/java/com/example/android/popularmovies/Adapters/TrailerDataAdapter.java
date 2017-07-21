package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;

/**
 * Created by alex.fanning on 28/06/2017.
 */

public class TrailerDataAdapter extends  RecyclerView.Adapter<TrailerDataAdapter.TrailerViewHolder> {
    private static final String TAG = TrailerDataAdapter.class.getSimpleName();
    private String[] trailers;
    private static int sViewHolderCount;
    private int mNumberItems;
    private Context context;
    private final ListItemClickListener mOnClickListener;

    private int position;

    public int getPosition(){return position;}

    public void setPosition(int _pos){position = _pos;}

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex, String trailer);
    }


    public TrailerDataAdapter(String[] _trailers, Context _c,ListItemClickListener l){
        mNumberItems = _trailers.length;
        trailers = _trailers;
        context = _c;
        sViewHolderCount = 0;
        mOnClickListener = l;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForGridItem = R.layout.trailer_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForGridItem,parent,false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        sViewHolderCount++;
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, int position) {
        holder.bind(trailers[position], position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.getAdapterPosition();
                return false;
            }
        });

    }

    public String getKeyAtPosition(int position){
        return trailers[position];
    }


    @Override
    public int getItemCount()
    {
            return trailers.length;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        TextView tv = null;



        public TrailerViewHolder(View itemView){
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv_trailer_grid);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(String trailerKey, int trailerNum){
            String trailerText = context.getString(R.string.trailer_grid_num) + (trailerNum + 1);
            tv.setText(trailerText);


        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();

            String trailerKey = trailers[clickedPosition];
            mOnClickListener.onListItemClick(clickedPosition,trailerKey);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE,R.id.menuYoutube,Menu.NONE,context.getString(R.string.menu_youtube));
            menu.add(Menu.NONE,R.id.menuShare,Menu.NONE,context.getString(R.string.menu_share));
        }
    }





}
