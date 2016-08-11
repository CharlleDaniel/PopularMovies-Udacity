package com.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;


public class GridAdapter extends BaseAdapter {

    Context context;
    List<Movie> filmes;

    public GridAdapter(Context c, List<Movie> filmes){
        this.context = c;
        this.filmes = filmes;
    }

    @Override
    public int getCount() {
        return filmes.size();
    }

    @Override
    public Movie getItem(int position) {
        return filmes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.grid_adapter_list, null);

        ImageView imgMovie= (ImageView)view.findViewById(R.id.iv_movie);

        Picasso.with(context).load(filmes.get(position).getUrlImage()).into(imgMovie);

        return view;

    }
}
