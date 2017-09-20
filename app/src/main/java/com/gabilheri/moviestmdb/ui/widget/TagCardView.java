package com.gabilheri.moviestmdb.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabilheri.moviestmdb.R;

import butterknife.BindView;

/**
 * Created by CPU11112-local on 9/20/2017.
 */

public class TagCardView extends BaseCustomCardView {
    @BindView(R.id.text_tag_name)
    TextView mTagNameText;

    @BindView(R.id.image_icon)
    ImageView mResultImage;

    public TagCardView(Context context) {
        super(context, null);
    }

//    public TagCardView(Context context, AttributeSet attrs) {
//        this(context, attrs, android.support.v17.leanback.R.attr.imageCardViewStyle);
//    }
//
//    public TagCardView(Context context, int styleResId) {
//        super(new ContextThemeWrapper(context, styleResId), null, 0);
//        buildLoadingCardView(styleResId);
//    }
//
//    public TagCardView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(getStyledContext(context, attrs, defStyleAttr), attrs, defStyleAttr);
//        buildLoadingCardView(getImageCardViewStyle(context, attrs, defStyleAttr));
//    }
//
//    private static Context getStyledContext(Context context, AttributeSet attrs, int defStyleAttr) {
//        int style = getImageCardViewStyle(context, attrs, defStyleAttr);
//        return new ContextThemeWrapper(context, style);
//    }
//
//    private static int getImageCardViewStyle(Context context, AttributeSet attrs, int defStyleAttr) {
//        // Read style attribute defined in XML layout.
//        int style = null == attrs ? 0 : attrs.getStyleAttribute();
//        if (0 == style) {
//            // Not found? Read global ImageCardView style from Theme attribute.
//            TypedArray styledAttrs =
//                    context.obtainStyledAttributes(
//                            android.support.v17.leanback.R.styleable.LeanbackTheme);
//            style = styledAttrs.getResourceId(
//                    android.support.v17.leanback.R.styleable.LeanbackTheme_imageCardViewStyle, 0);
//            styledAttrs.recycle();
//        }
//        return style;
//    }
//
//    private void buildLoadingCardView(int styleResId) {
//        setFocusable(false);
//        setFocusableInTouchMode(false);
//        setCardType(CARD_TYPE_MAIN_ONLY);
//        setBackgroundResource(R.color.primary_light);
//
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View view = inflater.inflate(R.layout.view_tag_card, this);
//        ButterKnife.bind(view);
//        TypedArray cardAttrs =
//                getContext().obtainStyledAttributes(
//                        styleResId, android.support.v17.leanback.R.styleable.lbImageCardView);
//        cardAttrs.recycle();
//    }


    @Override
    protected int getCardLayout() {
        return R.layout.view_tag_card;
    }

    public void setCardText(String string) {
        mTagNameText.setText(string);
    }

    public void setCardIcon(int resource) {
        mResultImage.setImageDrawable(ContextCompat.getDrawable(getContext(), resource));
    }

    // increase the performance
    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

}
