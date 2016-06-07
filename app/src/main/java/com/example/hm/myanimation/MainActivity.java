package com.example.hm.myanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends AppCompatActivity {
    private WaveLoadingView mWaveLoadingView;
    private CustomBackground mCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWaveLoadingView= (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setAmplitudeRatio(40);
        mWaveLoadingView.setProgressValue(50);
        mCustom= (CustomBackground) findViewById(R.id.customBackground);
    }

}
