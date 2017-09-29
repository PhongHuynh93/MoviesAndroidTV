package com.gabilheri.moviestmdb.ui.onboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.dagger.modules.FragmentModule;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by CPU11112-local on 9/28/2017.
 * <a href="https://developer.android.com/reference/android/support/v17/leanback/app/OnboardingFragment.html"></a>
 * info Onboarding screen has three kinds of animations:
 * 1. Logo Splash Animation
 * 2. Page enter animation
 * 3. Page change animation
 */
public class OnboardingFragment extends android.support.v17.leanback.app.OnboardingFragment {
    @Inject
    SharedPreferences mSharedPreferences;

    public static final String COMPLETED_ONBOARDING = "completed_onboarding";

    private static final int[] pageTitles = {
            R.string.onboarding_title_welcome,
            R.string.onboarding_title_design,
            R.string.onboarding_title_simple,
            R.string.onboarding_title_project
    };

    private static final int[] pageDescriptions = {
            R.string.onboarding_description_welcome,
            R.string.onboarding_description_design,
            R.string.onboarding_description_simple,
            R.string.onboarding_description_project
    };

    private final int[] pageImages = {
            R.drawable.tv_animation_a,
            R.drawable.tv_animation_b,
            R.drawable.tv_animation_c,
            R.drawable.tv_animation_d
    };

    private static final long ANIMATION_DURATION = 500;
    private ImageView mContentView;
    private Animator mContentAnimator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().appComponent().newSubFragmentComponent(new FragmentModule(this)).inject(this);
    }

    /* to provide the contents view. The content view is located in the content area at the center of the screen. */
    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return createNormalImageView();
    }

    private View createNormalImageView() {
        mContentView = new ImageView(getActivity());
        mContentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mContentView.setPadding(0, 32, 0, 32);
        return mContentView;

    }

    // step - 1. Logo Splash Animation: THE LOGO ANIMATION CAN NOT APPEARED IF WE DIDN'T SUPPLY setLogoResourceId OR onCreateLogoAnimation

    /**
     * In most cases, the logo animation needs to be customized because the logo images of applications are different from each other, or some applications may want to show their own animations.
     * The logo animation can be customized in two ways:
     * <p>
     * The simplest way is to provide the logo image by calling setLogoResourceId(int) to show the default logo animation. This method should be called in onCreateView(LayoutInflater, ViewGroup, Bundle).
     * If the logo animation is complex, then override onCreateLogoAnimation() and return the Animator object to run.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLogoResourceId(R.drawable.banner);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // only do this if the icon is complex
    @Nullable
    @Override
    protected Animator onCreateLogoAnimation() {
//        return AnimatorInflater.loadAnimator(getActivity(),
//                R.animator.onboarding_logo_screen_animation);
        return super.onCreateLogoAnimation();
    }

    /**
     * step 2. Page enter animation
     * DEFAULT: After logo animation finishes, page enter animation starts, which causes the header section - title and description views to fade and slide in.
     * <p>
     * Users can override the default fade + slide animation by overriding onCreateTitleAnimator() & onCreateDescriptionAnimator().
     *
     * @return
     */
    @Override
    protected Animator onCreateTitleAnimator() {
        return super.onCreateTitleAnimator();
    }

    @Override
    protected Animator onCreateDescriptionAnimator() {
        return super.onCreateDescriptionAnimator();
    }

    //  By default we don't animate the custom views but users can provide animation by overriding onCreateEnterAnimation().
    // step - note - only apply for the first page
    @Nullable
    @Override
    protected Animator onCreateEnterAnimation() {
        // fixme - if not override this method, the first image is not shown?
        // step - add the animation list and start it
        mContentView.setImageDrawable(getResources().getDrawable(pageImages[0]));
        ((AnimationDrawable) mContentView.getDrawable()).start();

        mContentAnimator = createFadeInAnimator(mContentView);
        return mContentAnimator;
    }

    /**
     * step 3 - Page change animation
     * When the page changes, the default animations of the title and description are played. The inherited class can override onPageChanged(int, int) to start the custom animations.
     *
     * @param newPage
     * @param previousPage
     */
    @Override
    protected void onPageChanged(int newPage, int previousPage) {
        if (mContentAnimator != null) {
            mContentAnimator.end();
        }
        ArrayList<Animator> animators = new ArrayList<>();
        Animator fadeOut = createFadeOutAnimator(mContentView);

        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // when fade in done - change the image and start animation
                mContentView.setImageDrawable(getResources().getDrawable(pageImages[newPage]));
                ((AnimationDrawable) mContentView.getDrawable()).start();
            }
        });
        animators.add(fadeOut);
        animators.add(createFadeInAnimator(mContentView));
        AnimatorSet set = new AnimatorSet();
        // step - play the fade out first of this view - and then fade in this view
        set.playSequentially(animators);
        set.start();
        mContentAnimator = set;
    }

    private Animator createFadeInAnimator(View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(ANIMATION_DURATION);
    }

    private Animator createFadeOutAnimator(View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(ANIMATION_DURATION);
    }

    /**
     * provide the number of pages.
     *
     * @return
     */
    @Override
    protected int getPageCount() {
        return pageTitles.length;
    }

    /**
     * provide the title of the page.
     *
     * @param pageIndex
     * @return
     */
    @Override
    protected CharSequence getPageTitle(int pageIndex) {
        return getString(pageTitles[pageIndex]);
    }

    /**
     * provide the description of the page.
     *
     * @param pageIndex
     * @return
     */
    @Override
    protected CharSequence getPageDescription(int pageIndex) {
        return getString(pageDescriptions[pageIndex]);
    }

    /* to provide the background view. Background view has the same size as the screen and the lowest z-order. */
    @Nullable
    @Override
    protected View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container) {
        View bgView = new View(getActivity());
        bgView.setBackgroundColor(getResources().getColor(R.color.onboard_background));
        return bgView;
    }

    /**
     * to provide the foreground view. Foreground view has the same size as the screen and the highest z-order
     *
     * @param inflater
     * @param container
     * @return
     */
    @Nullable
    @Override
    protected View onCreateForegroundView(LayoutInflater inflater, ViewGroup container) {
//        View fgView = new View(getActivity());
//        fgView.setBackgroundColor(getResources().getColor(R.color.onboard_foreground));
//        return fgView;

//        ImageView fgView = new ImageView(getActivity());
//        fgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        fgView.setPadding(0, 32, 0, 32);
//
//        return fgView;

        return null;
    }

    /**
     * step - If the user finishes the onboarding screen after navigating all the pages, onFinishFragment() is called.
     * <p>
     * The inherited class can override this method to show another fragment or activity, or just remove this fragment.
     */
    @Override
    protected void onFinishFragment() {
        super.onFinishFragment();
        // Our onboarding is done
        // Update the shared preferences
//        SharedPreferences.Editor sharedPreferencesEditor = mSharedPreferences.edit();
//        sharedPreferencesEditor.putBoolean(COMPLETED_ONBOARDING, true);
//        sharedPreferencesEditor.apply();
        // Let's go back to the MainActivity
        getActivity().finish();
    }
}
