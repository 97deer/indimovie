package com.example.mainindimovie_ex03.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.MovieActivity;
import com.example.mainindimovie_ex03.activitys.MovieDetail_Present_Activity;
import com.example.mainindimovie_ex03.activitys.MovieDetail_Release_Activity;
import com.example.mainindimovie_ex03.dataPack.ShowDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

//개봉 예정작를 View로 묶은 코드
//사용자 인터페이스를 편리하게 하기 위해 디자인적인 코드임
public class ShowDataView extends LinearLayout {
    private Context context;
    private TextView Show_Category_title;
    private RecyclerView recyclerView;
    private ShowDataAdapter adapter;
    private ImageView category_button;
    private Api api;

    private OnClickShowItemListener listener;

    private  void setOnClickShowItemListener(final OnClickShowItemListener listener){
        this.listener = listener;

    }
    public interface  OnClickShowItemListener{
        void onshowItemSelected(View view, MovieDataDo item);
    }
    public ShowDataView(Context context) {
        super(context);
        this.context = context;
        setview(context);
    }

    //+ 버튼 클릭시 페이지 이동을 위한 처리
    public void goScreen(Context context) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra("initMode", "gaebong");
        context.startActivity(intent);
    }

    private void setview(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_view_main_home, this, false);
        Show_Category_title = view.findViewById(R.id.show_data_view_title);
        category_button = view.findViewById(R.id.category_button);
        category_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fragment로 intent하기
                goScreen(getContext());
            }
        });
        recyclerView = view.findViewById(R.id.show_category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        this.addView(view);
        (new getYetMovieDataTask()).execute();
    }

    public void setViewDate(String title) {
        Show_Category_title.setText(title);
    }

    private class getYetMovieDataTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<MovieDataDo> temp = new ArrayList<>();

            try{
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for(int i=0;i<array.length();i++){
                    JSONArray  movie = (JSONArray)array.get(i);
                    MovieDataDo item = new MovieDataDo();
                    item.setM_id((Integer) movie.get(0)+"");
                    item.setM_title((String)movie.get(1));
                    item.setM_image_url((String)movie.get(11));
                    temp.add(item);
                }
                adapter = new ShowDataAdapter(temp, context);
                adapter.setonClickShowViewListener(new ShowDataAdapter.onClickShowViewListener() {

                    @Override
                    public void onclick(View view, MovieDataDo item) {
                        Intent intent = new Intent(getContext(), MovieDetail_Release_Activity.class);
                        intent.putExtra("movie_id", item.getM_id());
                        Log.d("eee", item.getM_title());
                        context.startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);

                recyclerView.setAdapter(adapter);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                HttpGet httpPost = new HttpGet(api.API_URL+"/movie/getYetMovieInfo");
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpPost);

                int res = response.getStatusLine().getStatusCode();
                if(res >= 400){

                }
                else{
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);

                    String line = null;
                    String data = "";

                    while((line = reader.readLine())!=null){

                        data += line;
                    }
                    reader.close();
                    is.close();

                    return data;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return "";
        }
    }
}
