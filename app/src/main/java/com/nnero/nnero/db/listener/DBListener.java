package com.nnero.nnero.db.listener;


import com.nnero.nnero.bean.Audio;

import java.util.List;

/**
 * Created by NNERO on 16/1/20.
 */
public interface DBListener {

    interface OnAudioQueryFinishListener {
        void onAudioQueryFinish(List<Audio> audios);
    }
}
