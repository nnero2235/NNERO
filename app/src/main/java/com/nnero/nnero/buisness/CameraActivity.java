package com.nnero.nnero.buisness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.nnero.nnero.base.BaseFragmentActivity;

/**
 * Created by nnero on 16/3/16.
 */
public class CameraActivity extends BaseFragmentActivity {

    public static void startCameraAct(Context context){
        Intent intent = new Intent(context,CameraActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
