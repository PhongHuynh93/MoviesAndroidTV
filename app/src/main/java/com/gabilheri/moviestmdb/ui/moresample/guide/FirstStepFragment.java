package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;

import com.gabilheri.moviestmdb.R;

import java.util.List;

import static com.gabilheri.moviestmdb.util.Constant.BACK;
import static com.gabilheri.moviestmdb.util.Constant.CONTINUE;
import static com.gabilheri.moviestmdb.util.Constant.SHOW_MORE;

/**
 * Created by user on 9/30/2017.
 * <a href="https://developer.android.com/training/tv/playback/guided-step.html"></a>
 */

public class FirstStepFragment extends BaseGuideStepFragment {
    @Override
    public int onProvideTheme() {
        return R.style.Theme_Example_Leanback_GuidedStep_First;
    }

    // step to provide instructions to the user
    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
//        return super.onCreateGuidance(savedInstanceState);

        // return a new GuidanceStylist.Guidance that contains context information, such as the step title, description, and icon.
//        String title = getString(R.string.guidedstep_first_title);
//        String breadcrumb = getString(R.string.guidedstep_first_breadcrumb);
//        String description = getString(R.string.guidedstep_first_description);
//        Drawable icon = getActivity().getDrawable(R.drawable.guidedstep_main_icon_1);
//        return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);

        return createLeftGuidance(R.string.guidedstep_first_breadcrumb, R.string.guidedstep_first_title, R.string.guidedstep_first_description, R.drawable.ic_main_icon);
    }

    // step provide a set of GuidedActions the user can take
    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
//        super.onCreateActions(actions, savedInstanceState);

        // step - add big action
        // add a new GuidedAction for each action item, and provide the action string, description, and ID.
        // Add "Continue" user action for this stepp
//        actions.add(new GuidedAction.Builder()
//                .id(CONTINUE)
//                .title(getString(R.string.guidedstep_continue))
//                .description(getString(R.string.guidedstep_letsdoit))
//                .hasNext(true)
//                .build());

        // we can create date dialog
        // Add a date-picker action by using GuidedDatePickerAction.Builder

        // step - add big action with sub actions, mean click one option, show lsit of sub actions
//        List<GuidedAction> subActions = new ArrayList<GuidedAction>();
//        subActions.add(new GuidedAction.Builder()
//                .id(SUBACTION1)
//                .title(getString(R.string.guidedstep_subaction1_title))
//                .description(getString(R.string.guidedstep_subaction1_desc))
//                .build());
//
//        actions.add(new GuidedAction.Builder()
//                .id(SUBACTIONS)
//                .title(getString(R.string.guidedstep_subactions_title))
//                .description(getString(R.string.guidedstep_subactions_desc))
//                .subActions(subActions)
//                .build());

        addAction(actions, CONTINUE,
                getResources().getString(R.string.guidedstep_continue),
                getResources().getString(R.string.guidedstep_letsdoit));
        addAction(actions, BACK,
                getResources().getString(R.string.guidedstep_cancel),
                getResources().getString(R.string.guidedstep_nevermind));
        addActionHasNext(getActivity(), actions, CONTINUE,
        "Title has next",
        "Description");
        addActionSubAction(getActivity(), actions, SHOW_MORE, CONTINUE,
                "Title has subaction",
                "Press to show subaction");
    }

    /**
     * Use button actions for simple actions, such as navigation actions between steps.
     * Don't use the date-picker action or other editable actions as button actions. Also, button actions cannot have subactions.
     * @param actions
     * @param savedInstanceState
     */
    @Override
    public void onCreateButtonActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        super.onCreateButtonActions(actions, savedInstanceState);
    }

    // step - if we have subaction, listen clicking
    @Override
    public boolean onSubGuidedActionClicked(GuidedAction action) {
        // Check for which action was clicked, and handle as needed
        if (action.getId() == CONTINUE) {
            // Subaction 1 selected
            GuidedStepFragment.add(getFragmentManager(), new SecondStepFragment());
        }
        // Return true to collapse the subactions drop-down list, or
        // false to keep the drop-down list expanded.
        return true;
    }

    // step to respond to those actions
    @Override
    public void onGuidedActionClicked(GuidedAction action) {
//        super.onGuidedActionClicked(action);

        // step - add the next step in the sequence to the fragment stack.
        // If the user presses the Back button on the TV remote, the device shows the previous GuidedStepFragment on the fragment stack.
//        FragmentManager fm = getFragmentManager();
//        if (action.getId() == CONTINUE) {
//            GuidedStepFragment.add(fm, new SecondStepFragment());
//        }

        if (action.getId() == CONTINUE) {
            addNextStep(new SecondStepFragment());
        } else if (action.getId() == BACK) {
            getActivity().finishAfterTransition();
        }
    }
}
