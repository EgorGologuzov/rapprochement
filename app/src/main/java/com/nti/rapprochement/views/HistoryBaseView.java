package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.HistoryBaseVM;

public class HistoryBaseView {
    public static View create(ViewGroup parent, HistoryBaseVM vm) {
        RecyclerView view = (RecyclerView) ViewsUtils.createView(R.layout.history, parent);

        view.setAdapter(vm.getAdapter());
        vm.setItemInsertedListener(view::smoothScrollToPosition);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);
        view.setLayoutManager(layoutManager);

        return view;
    }
}
