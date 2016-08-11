package com.popularmovies;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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


public class FragmentsMovie extends Fragment {
    enum Category { POPULAR, TOP_RATED;}
    private View view;
    private List<Movie> filmes;
    private GridAdapter adapter;
    protected static Movie movie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragments_movie,container,false);
        filmes= new ArrayList<>();
        FetchMovieListTask fetchMovieListTask = new FetchMovieListTask();
        fetchMovieListTask.execute();
        return view;
    }
    public  void buildMovies(){

        adapter = new GridAdapter(getContext(), filmes);
        GridView gridView = (GridView) view.findViewById(R.id.gv_list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie forecast = adapter.getItem(position);
                movie=forecast;
                Intent it =new Intent(getContext(),DetailsActivity.class);
                startActivity(it);
            }
        });
    }
    public class FetchMovieListTask  extends AsyncTask<Void, Void, List<Movie>> {

        private final String LOG_TAG = FetchMovieListTask.class.getSimpleName();
        private static final String API_KEY = "df827257a355bae8ca6a223c7d7d8211";	//substituir pela chave de vcs

        @Override
        protected void onPostExecute(List<Movie> movies) {
            filmes=movies;
            buildMovies();
        }

        private Category getCategory(){

            //usar SharedPreferences para pegar a configura��o
            //abaixo est� configurado hard-coded
            SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());
            String type=prefs.getString(getString(R.string.pref_types_key), getString(R.string.pref_movie_default));
            if(type.equalsIgnoreCase("Popular")){
                Category category = Category.valueOf("POPULAR");
                return category;
            }else{
                Category category = Category.valueOf("TOP_RATED");
                return category;
            }

        }

        @Override
        protected List<Movie> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieListJsonStr = null;

            try{

                Category cat = getCategory();

                Uri.Builder builder = createMovieListFetchURI(cat);	//cria a URI para fazer a requisi��o das listas de filmes
                URL url = new URL(builder.build().toString());

                // Create the request to IMDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
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
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieListJsonStr = buffer.toString();
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
                return getMovieListFromJson(movieListJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        private Uri.Builder createMovieListFetchURI(Category cat){
            //http://api.themoviedb.org/3/movie/top_rated?api_key=b04269a2ca363d50b583e308eb8ceb18
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(cat.toString().toLowerCase())
                    .appendQueryParameter("api_key", API_KEY);

            Log.v(LOG_TAG, "Uri movie list fetch: "+builder.toString());

            return builder;
        }

        private List<Movie> getMovieListFromJson(String json)throws JSONException {
            List<Movie> list= new ArrayList<>();
            try {
                JSONObject jo = new JSONObject(json);
                JSONArray ja = jo.getJSONArray("results");

                String totalResults=jo.getString("total_results");
                String totalpages=jo.getString("total_pages");

                int totalPages=Integer.parseInt(totalpages);
                int totalResult=Integer.parseInt(totalResults);
                int tamanho = (int) Math.round(((double)totalResult / totalPages)+0.5d);
                for(int k=0; k<tamanho;k++){
                    JSONObject movie=ja.getJSONObject(k);
                    String titulo=movie.getString("title");
                    String descricao=movie.getString("overview");
                    String urlImage="https://image.tmdb.org/t/p/w300"+movie.getString("poster_path");
                    String urlBackGround="https://image.tmdb.org/t/p/w300"+movie.getString("backdrop_path");
                    String dataLancamento=movie.getString("release_date");
                    double popularidade=movie.getDouble("popularity");
                    double votosQuantidade=movie.getDouble("vote_count");
                    double votosPositivos=movie.getDouble("vote_average");

                    Movie m= new Movie(titulo,descricao,urlImage,urlBackGround,dataLancamento,popularidade,votosQuantidade,votosPositivos);
                    list.add(m);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }


    }

    @Override
    public void onResume() {
        buildMovies();
        super.onResume();
    }
}