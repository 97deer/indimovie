package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Main2Activity;
import com.example.mainindimovie_ex03.MainActivity;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.StaticValues;
import com.example.mainindimovie_ex03.activitiesScenario.ScenarioFinishActivity;

//설정 페이지
//아이디 값 가져와서 보여준다.
public class SettingActivity extends AppCompatActivity {
    ImageButton imageButton;
    TextView idtext;
    TextView logout;
    //시간 지연 위한
    Handler timer = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);
        imageButton = findViewById(R.id.setting_delete_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        idtext = findViewById(R.id.idtext);
        idtext.setText(StaticValues.u_name);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticValues.u_id = "";

                if (StaticValues.u_name.length() >0) {

                    Intent intent2 = new Intent(getApplicationContext(), ScenarioFinishActivity.class);
                    startActivity(intent2);

                    //로그아웃하고 나서 전 스택 다 제거
                    timer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                            } else {
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                            startActivity(intent);
                        }
                    }, 2000);
                }
            }
        });
    }

    public void ononeBackPressed() {
        super.onBackPressed();
    }

}
