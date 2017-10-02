package com.gabilheri.moviestmdb.ui.moresample.authen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.widget.Toast;

import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.moresample.guide.BaseGuideStepFragment;

import java.util.List;

import static com.gabilheri.moviestmdb.util.Constant.CONTINUE;

/**
 * Created by CPU11112-local on 10/2/2017.
 */

public class FirstStepAuthenFragment extends BaseGuideStepFragment {

    @Override
    public int onProvideTheme() {
        return R.style.Theme_Example_Leanback_GuidedStep_First;
    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        return createLeftGuidance(
                -1,
                R.string.pref_title_screen_signin,
                R.string.pref_title_login_description,
                R.drawable.ic_main_icon
        );
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        addEditableAction(actions);
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        if (action.getId() == CONTINUE) {
            // TODO Authenticate your account
            // Assume the user was logged in
            Toast.makeText(getActivity(), "Welcome!", Toast.LENGTH_SHORT).show();
            getActivity().finishAfterTransition();
        }
    }
}
