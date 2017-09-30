package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
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
    // step - we can create custom view from this layout
    @Override
    public GuidanceStylist onCreateGuidanceStylist() {
        return new GuidanceStylist() {
            @Override
            public int onProvideLayoutId() {
                return R.layout.guidedstep_second_guidance;
            }
        };
    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        String title = getString(R.string.guidedstep_second_title);
        String breadcrumb = getString(R.string.guidedstep_second_breadcrumb);
        String description = getString(R.string.guidedstep_second_description);
        Drawable icon = getActivity().getDrawable(R.drawable.ic_main_icon);
        return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        String desc = getResources().getString(R.string.guidedstep_action_description);
        actions.add(new GuidedAction.Builder()
                .title(getResources().getString(R.string.guidedstep_action_title))
                .description(desc)
                .multilineDescription(true)
                .infoOnly(true)
                .enabled(false)
                .build());
        for (int i = 0; i < OPTION_NAMES.length; i++) {
            addCheckedAction(actions,
                    OPTION_DRAWABLES[i],
                    getActivity(),
                    OPTION_NAMES[i],
                    OPTION_DESCRIPTIONS[i],
                    OPTION_CHECKED[i]);
        }
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        FragmentManager fm = getFragmentManager();
        ThirdStepFragment next = ThirdStepFragment.newInstance(getSelectedActionPosition() - 1);
        GuidedStepFragment.add(fm, next);
    }

}
