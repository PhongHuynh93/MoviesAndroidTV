package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;

import java.util.List;

/**
 * Created by user on 9/30/2017.
 */

public class ThirdStepFragment extends GuidedStepFragment {
    @Override
    protected void onProvideFragmentTransitions() {
        super.onProvideFragmentTransitions();
    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        return super.onCreateGuidance(savedInstanceState);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        super.onCreateActions(actions, savedInstanceState);
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        super.onGuidedActionClicked(action);
    }
}
