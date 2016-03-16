package com.nnero.nnero.buisness.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nnero.nnero.R;
import com.nnero.nnero.base.BaseFragment;
import com.nnero.nnero.buisness.CameraActivity;
import com.nnero.nnero.views.MenuView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nnero on 16/3/16.
 */
public class LeftMenuFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.menu_camera) MenuView mCameraMenu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.left_menu_fragment,container,false);
        ButterKnife.inject(this,v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCameraMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.menu_camera:
            CameraActivity.startCameraAct(getActivity());
            break;
        }
    }
}
