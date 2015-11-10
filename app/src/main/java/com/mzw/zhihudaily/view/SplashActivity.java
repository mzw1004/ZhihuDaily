package com.mzw.zhihudaily.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzw.zhihudaily.ApiClient;
import com.mzw.zhihudaily.ApiService;
import com.mzw.zhihudaily.App;
import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.bean.StartImage;
import com.mzw.zhihudaily.util.L;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by M on 2015/11/10.
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = L.makeLogTag(SplashActivity.class);

    private ImageView mImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mImageView = (ImageView) findViewById(R.id.iv_splash);
        mTextView = (TextView) findViewById(R.id.tv_splash);

        loadImage();
        startAnimation();
    }

    private void loadImage() {
        ApiService service = ApiClient.getApiService();
        final Call<StartImage> imageCall = service.startImage();
        imageCall.enqueue(new Callback<StartImage>() {
            @Override
            public void onResponse(Response<StartImage> response, Retrofit retrofit) {
                L.d(TAG, response.body().getImgUrl());
                mTextView.setText(response.body().getText());
                Picasso.with(App.getContext()).load(response.body().getImgUrl()).into(mImageView);
            }

            @Override
            public void onFailure(Throwable t) {
                L.e(TAG, t, "failure");
            }
        });
    }

    private void startAnimation() {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mImageView, scaleX, scaleY);
        animator.setDuration(3000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startMainActivity();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int action = event.getAction();
        return !(action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) && super.onKeyDown(keyCode, event);
    }
}
