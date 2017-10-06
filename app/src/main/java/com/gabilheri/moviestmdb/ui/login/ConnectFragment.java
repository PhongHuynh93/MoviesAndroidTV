package com.gabilheri.moviestmdb.ui.login;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.databinding.FragmentConnectBinding;

/**
 * Created by user on 10/7/2017.
 */

public class ConnectFragment extends Fragment {
    private FragmentConnectBinding viewDataBinding;

    public static Fragment newInstance() {
        return new ConnectFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_connect, container, false);
        OnClickViewModel model = ViewModelProviders.of((FragmentActivity) getActivity()).get(OnClickViewModel.class);
        viewDataBinding.buttonSignIn.setOnClickListener(v -> {
            model.select(true);
        });
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
