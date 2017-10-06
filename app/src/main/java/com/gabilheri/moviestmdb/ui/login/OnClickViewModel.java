package com.gabilheri.moviestmdb.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by user on 10/7/2017.
 */

public class OnClickViewModel extends ViewModel{
    private final MutableLiveData<Boolean> selected = new MutableLiveData<>();

    public OnClickViewModel() {
        select(false);
    }

    public void select(Boolean item) {
        selected.setValue(item);
    }

    public LiveData<Boolean> getSelected() {
        return selected;
    }
}
