package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;

//내 시나리오가 없을 경우 Activity
//시나리오가 없으면 웹뷰로 페이지 이동하도록 함
public class MyScenarioNotActivity extends AppCompatActivity {
    Button button;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scenario_not);

        imageButton = findViewById(R.id.scenarionot_delete_btn);
        button = findViewById(R.id.go_site);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyScenarioNotActivity.super.onBackPressed();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "이동", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), GoSiteActivity.class);
                onBackPressed();
                startActivity(intent);
            }
        });
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
}
