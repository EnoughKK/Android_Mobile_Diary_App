package com.example.android_tmi_diaryapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarRVAdapter extends RecyclerView.Adapter<CalendarRVAdapter.ViewHolder> {
    private ArrayList<CalendarItemDTO> mCalendarItemDTO;
    private Context mcontext;
    private DatabaseHelper mDBHelper;
    private String mselectedDate;
    private ItemClickListener clickListener;

    public CalendarRVAdapter(ArrayList<CalendarItemDTO> mCalendarItemDTO, Context mcontext, String selectedDate, ItemClickListener clickListener) {
        this.mCalendarItemDTO = mCalendarItemDTO;
        this.mcontext = mcontext;
        this.clickListener = clickListener;
        mDBHelper = new DatabaseHelper(mcontext);
        mselectedDate = selectedDate;
    }

    public CalendarRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_diary, parent, false);
        return new ViewHolder(holder);
    }

    public void onBindViewHolder(CalendarRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(mCalendarItemDTO.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(mCalendarItemDTO.get(position));
            }
        });
    }

    public void setCalendarItemList(ArrayList<CalendarItemDTO> list){
        this.mCalendarItemDTO = list;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return mCalendarItemDTO.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView){
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.rv_calendar_title);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                CalendarDetailFragment calendarDetailFragment = new CalendarDetailFragment();
//
//                @Override
//                public void onClick(View view) {
//                    int curPos = getAdapterPosition(); // 현재 리스트 클릭한 이이템 위치
//                    CalendarItemDTO calendarItemDTO = mCalendarItemDTO.get(curPos);

//                    String[] strChoiceItems = {"수정하기", "삭제하기"};
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
//                    builder.setTitle("원하는 작업을 선택해 주세요");
//                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int position) {
//                            if(position == 0) {
//
//                            }
//                            if(position == 1) {
//                                int id = calendarItemDTO.getId();
//                                mDBHelper.DeleteCalendar(id);
//                                mCalendarItemDTO.remove(curPos);
//                                notifyItemRemoved(curPos);
//                                Toast.makeText(mcontext, "목록이 제거되었습니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    builder.show();
//                }
//            });
        }

        void onBind(CalendarItemDTO item) {
            title.setText(item.getTitle());
        }
    }

    public interface ItemClickListener {
        public void onItemClick(CalendarItemDTO calendarItemDTO);
    }
}
