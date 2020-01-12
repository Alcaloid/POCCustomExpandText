package com.poc.custom.expandtext.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.poc.custom.expandtext.R;


public class DividerItemDecorationWithStartMargin extends RecyclerView.ItemDecoration {

    private Drawable drawable;

    private Drawable getDrawable(Context context) {
        if (drawable == null) {
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.divider_line, null);
        }
        return drawable;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int itemPosition = parent.getChildAdapterPosition(view);

        if (itemPosition == RecyclerView.NO_POSITION || itemPosition == 0) {
            return;
        }

        outRect.top = getDrawable(view.getContext()).getIntrinsicHeight();
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();
        Drawable divider = getDrawable(parent.getContext());
        final int leftWithMargin = parent.getContext().getResources().getDimensionPixelSize(R.dimen.default_margin_16);
        final int right = parent.getWidth();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1 ; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + divider.getIntrinsicHeight();

            divider.setBounds(leftWithMargin, dividerTop, right, dividerBottom);
            divider.draw(canvas);
        }
        canvas.restore();
    }


}
