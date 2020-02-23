package com.example.mainindimovie_ex03.activitiesScenario;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.dataPackTab.ScenarioDetailTabAdapter;

//시나리오의 상세 내용을 볼 수 있는 Activity
//시나리오 제목, 작성자, 장르, 모인금액, 마감날짜, 후원자수,
//창작자 소개, 목표 금액 펀딩의 목적, 시나리오 프로젝트개요,
//시나리오 제작실행 일정, 시놉시스 정보
public class ScenarioDetailActivity extends AppCompatActivity {
    ImageButton delete_btn;

    String selected_u_id;
    String selected_s_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_detail);
        delete_btn = findViewById(R.id.sinario_detail_delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScenarioDetailActivity.super.onBackPressed();
            }
        });

        //ScenarioActivity에서 받아온 s_id, u_id
        Intent intent = getIntent();
        selected_s_id = intent.getStringExtra("s_id");
        selected_u_id = intent.getStringExtra("u_id");

        //ScenarioDetailTabAdapter에 s_id, u_id를 주기
        ScenarioDetailTabAdapter adapter = new ScenarioDetailTabAdapter(ScenarioDetailActivity.this, getSupportFragmentManager());
        adapter.s_id = selected_s_id;
        adapter.u_id = selected_u_id;
        ViewPager viewPager = findViewById(R.id.sinario_detail_container);
        viewPager.setAdapter(adapter);
        TabLayout tabs = findViewById(R.id.sinario_detail_tab);
        tabs.setupWithViewPager(viewPager);
    }
}