package com.example.hm.myanimation;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends AppCompatActivity implements WaveListener {
    private WaveLoadingView mWaveLoadingView;
    private CustomBackground mCustom;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mCustom = (CustomBackground) findViewById(R.id.customBackground);
        mTextView = (TextView) findViewById(R.id.tv);
        mCustom.setListener(this);

    }

    @Override
    public void setWaveProgress() {
        int numPlatforms = mCustom.titles.size();
        if (numPlatforms == 1){
            mWaveLoadingView.setProgressValue(42);
            mTextView.setVisibility(View.INVISIBLE);
            mWaveLoadingView.setBottomTitle(mCustom.titles.get(0).getPlatform());
            mWaveLoadingView.setTopTitle("");
            mWaveLoadingView.setCenterTitle("");
        }
        if(numPlatforms == 2){
            mWaveLoadingView.setProgressValue(58);
            mWaveLoadingView.setBottomTitle(mCustom.titles.get(0).getPlatform());
            mWaveLoadingView.setCenterTitle(mCustom.titles.get(1).getPlatform());
            mWaveLoadingView.setTopTitle("");
        }
        if(numPlatforms == 3){
            mWaveLoadingView.setProgressValue(100);
            mWaveLoadingView.setBottomTitle(mCustom.titles.get(0).getPlatform());
            mWaveLoadingView.setCenterTitle(mCustom.titles.get(1).getPlatform());
            mWaveLoadingView.setTopTitle(mCustom.titles.get(2).getPlatform());
        }
        if(numPlatforms == 0){
            mWaveLoadingView.setProgressValue(0);
            mTextView.setVisibility(View.VISIBLE);
            mWaveLoadingView.setBottomTitle("");
            mWaveLoadingView.setTopTitle("");
            mWaveLoadingView.setCenterTitle("");
        }
    }
}
