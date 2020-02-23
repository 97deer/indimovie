package com.example.mainindimovie_ex03.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.dataPackTab.MovieTabAdapter;

//현재 상영작, 개봉 예정작 등 영화 정보 보여주는 Acitivity
//ViewPager 형태
public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //MovieDataView.java에서 값 받기
        Intent intent = getIntent();
        String temp = intent.getStringExtra("initMode");

        MovieTabAdapter adapter = new MovieTabAdapter(MovieActivity.this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        //MovieDataView에서  + 버튼 클릭시  해당 Fragment로 가기 위한 내용
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //MovieDataView.java에서 받은 값에 따라 if문
        if ( temp.equals("Movie") ) {
            TabLayout.Tab tab = tabs.getTabAt(0);
            tab.select();
        } else {
            TabLayout.Tab tab = tabs.getTabAt(1);
            tab.select();
        }
        init();
    }

    private void init() {
        findViewById(R.id.movie_delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
