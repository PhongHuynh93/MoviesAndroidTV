package com.gabilheri.moviestmdb.ui.moresample;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.ErrorFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.gabilheri.moviestmdb.R;

/**
 * Created by CPU11112-local on 9/29/2017.
 */
// step - build a simple error fragment that des
public class BrowseErrorFragment extends ErrorFragment {
    private static final boolean TRANSLUCENT = true;
    private static final int TIMER_DELAY = 1000;

    private final Handler mHandler = new Handler();
    private SpinnerFragment mSpinnerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.app_name));

        mSpinnerFragment = new SpinnerFragment();
        // this is the id of the frame in layout in BaseTvActivity
        getFragmentManager().beginTransaction().add(R.id.tv_frame_content, mSpinnerFragment).commit();
    }

    // step - show progress bar and then remove it after 2 secs
    @Override
    public void onStart() {
        super.onStart();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
                setErrorContent();
            }
        }, TIMER_DELAY);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
        getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
    }

    private void setErrorContent() {
        setImageDrawable(getResources().getDrawable(R.drawable.lb_ic_sad_cloud, null));
        setMessage(getResources().getString(R.string.error_fragment_message));
        setDefaultBackground(TRANSLUCENT);

        setButtonText(getResources().getString(R.string.dismiss_error));
        setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getFragmentManager().beginTransaction().remove(BrowseErrorFragment.this).commit();
                getFragmentManager().popBackStack();
            }
        });
    }

    // step 0 create spinner
    public static class SpinnerFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ProgressBar progressBar = new ProgressBar(container.getContext());
            if (container instanceof FrameLayout) {
                Resources res = getResources();
                int width = res.getDimensionPixelSize(R.dimen.spinner_width);
                int height = res.getDimensionPixelSize(R.dimen.spinner_height);
                FrameLayout.LayoutParams layoutParams =
                        new FrameLayout.LayoutParams(width, height, Gravity.CENTER);
                progressBar.setLayoutParams(layoutParams);
            }
            return progressBar;
        }
    }
}
