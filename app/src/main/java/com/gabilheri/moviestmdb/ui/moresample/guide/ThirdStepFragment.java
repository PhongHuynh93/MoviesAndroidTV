package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;

import com.gabilheri.moviestmdb.R;

import java.util.List;

import static com.gabilheri.moviestmdb.util.Constant.BACK;
import static com.gabilheri.moviestmdb.util.Constant.CONTINUE;
import static com.gabilheri.moviestmdb.util.Constant.OPTION_NAMES;

/**
 * Created by user on 9/30/2017.
 */

public class ThirdStepFragment extends BaseGuideStepFragment {
    private final static String ARG_OPTION_IDX = "arg.option.idx";

    public static ThirdStepFragment newInstance(final int option) {
        final ThirdStepFragment f = new ThirdStepFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_OPTION_IDX, option);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void onProvideFragmentTransitions() {
        super.onProvideFragmentTransitions();
    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        String title = getString(R.string.guidedstep_third_title);
        String breadcrumb = getString(R.string.guidedstep_third_breadcrumb);
        String description = getString(R.string.guidedstep_third_command)
                + OPTION_NAMES[getArguments().getInt(ARG_OPTION_IDX)];
        Drawable icon = getActivity().getDrawable(R.drawable.ic_main_icon);
        return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        addAction(actions, CONTINUE, "Done", "All finished");
        addAction(actions, BACK, "Back", "Forgot something...");
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        if (action.getId() == CONTINUE) {
            getActivity().finishAfterTransition();
        } else {
            getFragmentManager().popBackStack();
        }
    }


}
