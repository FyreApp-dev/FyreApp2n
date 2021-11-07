package com.msgr2.fyreapp.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.adapter.AdapterChatList;
import com.msgr2.fyreapp.model.ModelChatList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartsFragment extends Fragment {
    private List<ModelChatList> list = new ArrayList<>();
    private RecyclerView recyclerView;

    public ChartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        recyclerView = view.findViewById(R.id.chat_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //getChartList();
        return view;
    }

    private void getChartList() {

        String img = "https://images.unsplash.com/photo-1579783483458-83d02161294e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHByb2ZpbGV8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60";
        String img2 = "https://images.unsplash.com/photo-1569173112611-52a7cd38bea9?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8cHJvZmlsZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60";
        String img3 = "https://images.unsplash.com/photo-1531427186611-ecfd6d936c79?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8cHJvZmlsZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60";
        list.add(new ModelChatList("1", "Fajulugbe Sunday", "hello ooo", "11:15 AM", img2));
        list.add(new ModelChatList("2", "Muyiwa S", "hello ooo", "11:15 AM", img));
        list.add(new ModelChatList("3", "Sunday", "hello ooo", "11:15 AM", img2));
        list.add(new ModelChatList("4", "AA", "hello ooo", "Yesterday", img3));
        list.add(new ModelChatList("5", "Fajulugbe", "hello ooo", "Aug 26", ""));
        recyclerView.setAdapter(new AdapterChatList(list, getContext()));
    }
}