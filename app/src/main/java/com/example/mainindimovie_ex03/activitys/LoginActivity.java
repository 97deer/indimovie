package com.example.mainindimovie_ex03.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.UserDataDo;
import com.example.mainindimovie_ex03.Main2Activity;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.aApi.Api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

//로그인 Activity
public class LoginActivity extends AppCompatActivity {

    private Api api;
    int u_id;
    EditText member_login_id_edit;  //아이디 입력
    EditText member_login_passwd_edit;  //비밀번호 입력
    Button member_login_btn; //버튼
    TextView member_login_join; //회원가입
    TextView member_login_infosearch; //아이디 찾기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login_delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.super.onBackPressed();
            }
        });

        member_login_id_edit = findViewById(R.id.member_login_id_edit);
        member_login_passwd_edit = findViewById(R.id.member_login_passwd_edit);
        member_login_join = findViewById(R.id.member_login_join);
        member_login_infosearch = findViewById(R.id.member_login_infosearch);

        //아이디찾기
        member_login_infosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IDPWSearchActivity.class);
                startActivity(intent);
                onBackPressed();
            }
        });
        //회원가입
        member_login_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
                onBackPressed();
            }
        });
        member_login_btn = findViewById(R.id.member_login_btn);
        member_login_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (member_login_id_edit.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    member_login_id_edit.requestFocus();
                    return;
                }
                if (member_login_passwd_edit.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    member_login_passwd_edit.requestFocus();
                    return;
                }
                (new getUserTask()).execute();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    //로그인 확인하는 API
    private class getUserTask extends AsyncTask<String, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        String u_idtext = member_login_id_edit.getText().toString();
        String u_password = member_login_passwd_edit.getText().toString();

        @Override
        //다 끝나고 나서(결과값)
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ddd", s);
            ArrayList<UserDataDo> temp = new ArrayList<>();
            UserDataDo item = new UserDataDo();

            try {
                //s를 자동으로 array로 바꿔준다.
                JSONObject array = new JSONObject(s);
                item.setU_id((String) array.get("u_id"));
                item.setU_idtext((String) array.get("u_idtext"));
                item.setU_password((String) array.get("u_password"));
                item.setU_birth((String) array.get("u_birth"));
                temp.add(item);
                String idd = item.getU_idtext();
                String ipass = item.getU_password();

                //서버 아이디값과 입력한 아이디값이 같고 서버 비밀번호값과 입력한 비밀번호값이 같을 경우
                if (u_idtext.equals(idd) && u_password.equals(ipass)) {
                    Log.d("ddd", "로그인2222");
                    StaticValues.u_id = item.getU_id();
                    StaticValues.u_name = item.getU_idtext();
                    StaticValues.u_age = item.getU_birth();

                    //회원 나이를 static 변수에 저장
                    Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    int age1 = Integer.parseInt(StaticValues.u_age);
                    int age2 = currentYear - age1 + 1;

                    //서버값이 있어 로그인 가능 Main2Activity 로 이동하기
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);

                    //전 stack들 다 제거하기
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "로그인실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "아이디/비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        String idtext = member_login_id_edit.getText().toString();
        String passwordtext = member_login_passwd_edit.getText().toString();

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpGet httpget = new HttpGet(api.API_URL+"/movie/getUserDataDoLoginin/?u_idtext=" + idtext + "&u_password=" + passwordtext);

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