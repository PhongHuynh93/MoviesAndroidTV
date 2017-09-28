package com.gabilheri.moviestmdb.ui.onboard;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabilheri.moviestmdb.R;

/**
 * Created by CPU11112-local on 9/28/2017.
 * <a href="https://developer.android.com/reference/android/support/v17/leanback/app/OnboardingFragment.html"></a>
 * info Onboarding screen has three kinds of animations:
 * 1. Logo Splash Animation
 * 2. Page enter animation
 * 3. Page change animation
 */
public class OnboardingFragment extends android.support.v17.leanback.app.OnboardingFragment{
    // step - 1. Logo Splash Animation: THE LOGO ANIMATION CAN NOT APPEARED IF WE DIDN'T SUPPLY setLogoResourceId OR onCreateLogoAnimation
    /**
     * In most cases, the logo animation needs to be customized because the logo images of applications are different from each other, or some applications may want to show their own animations.
     * The logo animation can be customized in two ways:

     The simplest way is to provide the logo image by calling setLogoResourceId(int) to show the default logo animation. This method should be called in onCreateView(LayoutInflater, ViewGroup, Bundle).
     If the logo animation is complex, then override onCreateLogoAnimation() and return the Animator object to run.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLogoResourceId(R.drawable.banner);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Nullable
    @Override
    protected Animator onCreateLogoAnimation() {
        return super.onCreateLogoAnimation();
    }

    /**
     * step 2. Page enter animation
     * DEFAULT: After logo animation finishes, page enter animation starts, which causes the header section - title and description views to fade and slide in.
     *
     * Users can override the default fade + slide animation by overriding onCreateTitleAnimator() & onCreateDescriptionAnimator().
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
    @Nullable
    @Override
    protected Animator onCreateEnterAnimation() {
        return super.onCreateEnterAnimation();
    }

    /**
     * step 3 - Page change animation
     * When the page changes, the default animations of the title and description are played. The inherited class can override onPageChanged(int, int) to start the custom animations.
     * @param newPage
     * @param previousPage
     */
    @Override
    protected void onPageChanged(int newPage, int previousPage) {
        super.onPageChanged(newPage, previousPage);
    }

    /**
     * provide the number of pages.
     * @return
     */
    @Override
    protected int getPageCount() {
        return 0;
    }

    /**
     * provide the title of the page.
     * @param pageIndex
     * @return
     */
    @Override
    protected CharSequence getPageTitle(int pageIndex) {
        return null;
    }

    /**
     * provide the description of the page.
     * @param pageIndex
     * @return
     */
    @Override
    protected CharSequence getPageDescription(int pageIndex) {
        return null;
    }

    /* to provide the background view. Background view has the same size as the screen and the lowest z-order. */
    @Nullable
    @Override
    protected View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    /* to provide the contents view. The content view is located in the content area at the center of the screen. */
    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    /**
     * to provide the foreground view. Foreground view has the same size as the screen and the highest z-order
     * @param inflater
     * @param container
     * @return
     */
    @Nullable
    @Override
    protected View onCreateForegroundView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    /**
     * step - If the user finishes the onboarding screen after navigating all the pages, onFinishFragment() is called.
     *
     * The inherited class can override this method to show another fragment or activity, or just remove this fragment.
     *
     */
    @Override
    protected void onFinishFragment() {
        super.onFinishFragment();
    }
}
