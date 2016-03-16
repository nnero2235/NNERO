package com.nnero.nnero.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nnero.nnero.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nnero on 16/3/16.
 * 菜单item 组合封装
 */
public class MenuView extends RelativeLayout {

    @InjectView(R.id.menu_name) TextView nameView;
    @InjectView(R.id.image) ImageView imageView;

    public MenuView(Context context) {
        super(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(),R.layout.menu_view,this);
        ButterKnife.inject(this);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MenuView,
                0, 0);
        nameView.setText(a.getString(R.styleable.MenuView_menuName));
        imageView.setImageResource(a.getResourceId(R.styleable.MenuView_menuIcon,R.drawable.ic_camera));
        a.recycle();

        setPadding(0,30,0,30);
    }
}
