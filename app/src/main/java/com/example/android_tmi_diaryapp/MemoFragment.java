package com.example.android_tmi_diaryapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_tmi_diaryapp.DTO.MemoItemDTO;

import java.util.ArrayList;

public class MemoFragment extends Fragment implements MemoRVAdapter.ItemClickListener{

    private RecyclerView mMemoRVView;
    private MemoRVAdapter mMemoRVAdapter;
    private ArrayList<MemoItemDTO> mMemoItems;
    private MemoDBActivity memoDBActivity;
    //FIXME
    private Context memoContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_memo, container, false);

        mMemoRVView = rootview.findViewById(R.id.rv_memo_container);

        setInit();

        rootview.findViewById(R.id.memo_button_add).setOnClickListener(new View.OnClickListener() {
            MemoDetailFragment memoDetailFragment = new MemoDetailFragment();

            public void onClick(View v) {
                Bundle bundle = new Bundle();
                memoDetailFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_conainer, memoDetailFragment, "memoDetailFragment")
                        .commitAllowingStateLoss();
            }
        });

        return rootview;
    }

    private void setInit(){
        memoDBActivity = new MemoDBActivity(getContext());
        mMemoItems = new ArrayList<>();

//        memoDBActivity.InsertMemo("안드로이드 시험", "안드로이드 기말 시험"); // DB에 인서트
//        MemoItemDTO item = new MemoItemDTO();
//        item.setTitle("안드로이드 시험");
//        item.setContent("안드로이드 기말 시험");
//        addItem(item);

        loadRecentDB();
    }

//    public void addItem(MemoItemDTO _item){
//        mMemoItems.add(0, _item);
//    }

    private void loadRecentDB() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mMemoRVView.setLayoutManager(layoutManager);
        mMemoItems = memoDBActivity.getMemoItem();
        mMemoRVAdapter = new MemoRVAdapter(mMemoItems, getContext(), this);
        mMemoRVView.setHasFixedSize(true);
        mMemoRVView.setAdapter(mMemoRVAdapter);
    }

    @Override
    public void onItemClick(MemoItemDTO memoItemDTO){
        MemoDetailFragment memoDetailFragment = new MemoDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("ID", memoItemDTO.getId());
        bundle.putString("title", memoItemDTO.getTitle());
        bundle.putString("content", memoItemDTO.getContent());
        bundle.putBoolean("isExist", true);
        memoDetailFragment.setArguments(bundle);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_conainer, memoDetailFragment, "memoDetailFragment")
                .commitAllowingStateLoss();
    }
}