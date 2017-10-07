package com.gabilheri.moviestmdb.ui.adapter;

import android.content.Context;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import com.example.myapplication.data.models.Option;
import com.gabilheri.moviestmdb.ui.presenter.IconItemPresenter;

/**
 * Created by user on 10/7/2017.
 */

public class OptionsAdapter extends ArrayObjectAdapter {
    private IconItemPresenter mOptionsItemPresenter;

    public OptionsAdapter(Context context) {
        mOptionsItemPresenter = new IconItemPresenter();
        setPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return mOptionsItemPresenter;
            }
        });
    }

    public void addOption(Option option) {
        add(option);
    }

    public void updateOption(Option option) {
        Option first = (Option) get(0);
        first.value = option.value;
        notifyChanged();
    }
}
