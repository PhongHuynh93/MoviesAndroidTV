package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.GuidedStepFragment;

/**
 * Created by CPU11112-local on 9/29/2017.
 * <a href="https://developer.android.com/reference/android/support/v17/leanback/app/GuidedStepFragment.html"></a>
 */
public class GuidedStepActivity extends Activity {

    /**
     * Clients use following helper functions to add GuidedStepFragment to Activity or FragmentManager:
     * step - addAsRoot(): adds GuidedStepFragment as the first Fragment in activity.
     * step - add(): add GuidedStepFragment on top of existing Fragments or replacing existing GuidedStepFragment when moving forward to next step.
     * addAsRoot(): press back then actiivty is destroy too.
     * step - finishGuidedStepFragments(): either finish the activity or pop all GuidedStepFragment from stack.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // first add first guidestep
        if (null == savedInstanceState) {
            GuidedStepFragment.addAsRoot(this, new FirstStepFragment(), android.R.id.content);
        }
    }
}
