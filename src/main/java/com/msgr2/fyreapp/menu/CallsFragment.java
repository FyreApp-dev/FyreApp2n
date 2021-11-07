package com.msgr2.fyreapp.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.adapter.AdapterCallList;
import com.msgr2.fyreapp.model.ModelCallList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallsFragment extends Fragment {
    private List<ModelCallList> list;
    private RecyclerView recyclerView;

    public CallsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calls, container, false);
        recyclerView = view.findViewById(R.id.call_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //
        // getCallList();
        return view;
    }

    private void getCallList() {
        String img = "https://images.unsplash.com/photo-1579783483458-83d02161294e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHByb2ZpbGV8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60";
        String img2 = "https://images.unsplash.com/photo-1569173112611-52a7cd38bea9?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8cHJvZmlsZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60";
        String img3 = "https://images.unsplash.com/photo-1531427186611-ecfd6d936c79?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8cHJvZmlsZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60";

        list = new ArrayList<>();
        list.add(new ModelCallList(
                "1",
                "Fajulugbe",
                "Today",
                img,
                "income"
        ));

        list.add(new ModelCallList(
                "2",
                "AA",
                "Today",
                img2,
                "missed"
        ));
        list.add(new ModelCallList(
                "3",
                "muyiwa",
                "Yesterday",
                img3,
                ""
        ));
        list.add(new ModelCallList(
                "3",
                "muyiwa",
                "Yesterday",
                img3,
                ""));
        list.add(new ModelCallList(
                "4",
                "Love",
                "Aug 01, 21",
                "",
                "income"
        ));

        recyclerView.setAdapter(new AdapterCallList(list, getContext()));
    }
}