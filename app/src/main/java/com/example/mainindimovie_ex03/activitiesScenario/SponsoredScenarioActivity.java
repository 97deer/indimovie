package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.Funding;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.dataPack.SponsoredSinarioDataAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

//내가 후원한 시나리오를 볼 수 있는 Activity(시나리오 장르, 작성자, 제목, 후원금액)
//MyFDPayInfoActivity과 다른점 : 다음 페이지는 해당 시나리오로 넘어감
public class SponsoredScenarioActivity extends AppCompatActivity {
    private ImageButton delete_btn;
    private RecyclerView recyclerView;
    private SponsoredSinarioDataAdapter adapter;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_sponsored_scenario);
        (new getSponScenarioTask()).execute();
        delete_btn = findViewById(R.id.sponsored_senario_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SponsoredScenarioActivity.super.onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    //후원한 시나리오 리스트 API
    private class getSponScenarioTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);
            try {
                JSONArray array = new JSONArray(s);
                if (array.length() > 0) {
                    ArrayList<Funding> temp = new ArrayList<>();
                    try {
                        //array : movie 전체

                        for (int i = 0; i < array.length(); i++) {
                            JSONArray notice = (JSONArray) array.get(i);
                            Funding item = new Funding();
                            item.setS_jang((String) notice.get(3));
                            item.setS_title((String) notice.get(4));
                            item.setU_idtext((String) notice.get(6));
                            item.setU_id((Integer) notice.get(7)+"");
                            item.setF_amount((Integer) notice.get(1));
                            item.setS_id((Integer) notice.get(2)+"");
                            temp.add(item);
                        }
                        adapter = new SponsoredSinarioDataAdapter(temp);
                        adapter.setonClickSponsoredSinarioDataListener(new SponsoredSinarioDataAdapter.onClickSponsoredSinarioDataListener() {
                            @Override
                            public void onclick(View view, Funding item) {
                                Intent intent = new Intent(getApplicationContext(), ScenarioDetailActivity.class);
                                intent.putExtra("s_id", item.getS_id());
                                intent.putExtra("u_id", item.getU_id());
                                startActivity(intent);

                            }

                        });

                        recyclerView = findViewById(R.id.sponsored_sinario_recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "시나리오가 없습니다.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "후원시나리오가 없습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SponsoredScenarioNotActivity.class);
                    onBackPressed();
                    startActivity(intent);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getMyFundingInfo/?u_id=" + StaticValues.u_id);

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
