package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;

import com.gabilheri.moviestmdb.R;

import java.util.List;

import static com.gabilheri.moviestmdb.util.Constant.OPTION_CHECKED;
import static com.gabilheri.moviestmdb.util.Constant.OPTION_DESCRIPTIONS;
import static com.gabilheri.moviestmdb.util.Constant.OPTION_DRAWABLES;
import static com.gabilheri.moviestmdb.util.Constant.OPTION_NAMES;

/**
 * Created by user on 9/30/2017.
 */

public class SecondStepFragment extends BaseGuideStepFragment {
    // step - we can create custom view from this layout, with the same id
//    @Override
//    public GuidanceStylist onCreateGuidanceStylist() {
//        return new GuidanceStylist() {
//            @Override
//            public int onProvideLayoutId() {
//                return R.layout.guidedstep_second_guidance;
//            }
//        };
//    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        return createLeftGuidance(R.string.guidedstep_second_breadcrumb, R.string.guidedstep_second_title, R.string.guidedstep_second_description, R.drawable.ic_main_icon);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        addMutlipleAction(getActivity(),
                actions,
                R.string.guidedstep_action_title,
                R.string.guidedstep_action_description,
                OPTION_NAMES,
                OPTION_DESCRIPTIONS,
                OPTION_DRAWABLES,
                OPTION_CHECKED);
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        // go to next stepp with position
        addNextStep(ThirdStepFragment.newInstance(getSelectedActionPosition() - 1));
    }

}
