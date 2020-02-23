package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.TheaterDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

//영화관 리스트
//TheaterListActivity  에서 사용하는 Adapter
//Adapter은 RecyclerView을 연결하기 위한 코드
//영화관을 지역별로 묶어 보여준다.
//TheaterListAdapter 안에 ReserMovieTimeSlideAdapter가 묶여져 있다.
public class TheaterListSubSlideAdapter extends RecyclerView.Adapter<TheaterListSubSlideAdapter.TheaterListSubSlideHolder> {
    private ArrayList<String> texts;
    private OnClickSlidetextsListener listener;

    public interface OnClickSlidetextsListener{
        void OnClicked(TextView textView);
    }
    public void setOnClickSlidetextsListener(OnClickSlidetextsListener listener){
        this.listener = listener;
    }

    public TheaterListSubSlideAdapter(ArrayList<String> texts){
        this.texts=texts;
    }


    class TheaterListSubSlideHolder extends RecyclerView.ViewHolder {
        TextView text;


        public TheaterListSubSlideHolder(@NonNull View itemView) {
            super(itemView);
            text =itemView.findViewById(R.id.theater_list_text);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener !=null){
                        listener.OnClicked(text);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public TheaterListSubSlideHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_theater_list, viewGroup, false);
        return new  TheaterListSubSlideHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterListSubSlideHolder theaterListSubSlideHolder, int i) {
        String s_text = texts.get(i);
        theaterListSubSlideHolder.text.setText(s_text);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

}
