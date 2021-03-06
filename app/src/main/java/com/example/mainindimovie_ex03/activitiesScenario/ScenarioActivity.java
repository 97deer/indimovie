package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.SinarioDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

//시나리오 후원하기 위해 필요한 검색창 + 시나리오 리스트 볼 수 있는 Activity
//시나리오는 장르, 제목, 작성자, 등록일자로 나열됨
public class ScenarioActivity extends AppCompatActivity {
    private ImageButton my_senario__delete_btn;
    private RecyclerView recyclerView;
    private SinarioDataAdapter adapter;
    private TextView sinario_search;
    private  ImageButton sinario_search_btn;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);

        //서버 통신
        (new getscenarioTask()).execute();
        my_senario__delete_btn = findViewById(R.id.my_senario__delete_btn);
        my_senario__delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScenarioActivity.super.onBackPressed();
            }
        });

        //시나리오 검색 페이지로 이동
        sinario_search =findViewById(R.id.sinario_search_edit);
        sinario_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScenarioSearch_Activity.class);
                startActivity(intent);
            }
        });

        //시나리오 검색 페이지로 이동
        sinario_search_btn =findViewById(R.id.sinario_search_btn);
        sinario_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScenarioSearch_Activity.class);
                startActivity(intent);
            }
        });
    }

    //시나리오 리스트 API통신
    private class getscenarioTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);

            ArrayList<SinarioDataDo> temp = new ArrayList<>();
            try {
                //array : movie 전체
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray scenario = (JSONArray) array.get(i);
                    SinarioDataDo item = new SinarioDataDo();
                    item.setS_id((Integer) scenario.get(0)+"");
                    item.setS_jang((String) scenario.get(1));
                    item.setS_title((String) scenario.get(2));
                    item.setU_id((Integer)scenario.get(5)+"");
                    item.setU_name((String) scenario.get(7));
                    item.setS_regdate((String)scenario.get(3));
                    temp.add(item);
                }
                adapter = new SinarioDataAdapter(temp);
                adapter.setonClickSinarioDataListener(new SinarioDataAdapter.onClickSinarioDataListener() {
                    @Override
                    public void onclick(View view, SinarioDataDo item) {
                        Intent intent = new Intent(getApplicationContext(), ScenarioDetailActivity.class);
                        intent.putExtra("s_id", item.getS_id());
                        intent.putExtra("u_id", item.getU_id());
                        startActivity(intent);

                    }
                });
                recyclerView = findViewById(R.id.sinario_recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getScenarioInfo");

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

                int res = response.getStatusLine().getStatusCode();
                Log.d("ddd", res + "");
                if (res >= 400) {

                } else {
                    //결과내용을 문자열로 바꾼다..
                    InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);

                    String line = null;
                    String data = "";

                    while ((line = reader.readLine()) != null) {
                        data += line;
                    }
                    reader.close();
                    is.close();

                    return data;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
