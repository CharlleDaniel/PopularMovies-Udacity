package com.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        movie=FragmentsMovie.movie;
        toolbar.setTitle(movie.getTitulo());

        ImageView img= (ImageView)findViewById(R.id.iv_back);
        Picasso.with(this).load(movie.getUrlBackGround()).into(img);
        TextView tv_title= (TextView)findViewById(R.id.tv_title);
        tv_title.setText(movie.getDataLancamento());
        TextView tv_des=(TextView)findViewById(R.id.tv_des);
        tv_des.setText(movie.getDescricao());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
