package com.mzw.zhihudaily.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mzw.zhihudaily.App;
import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.bean.StartImage;
import com.mzw.zhihudaily.util.L;
import com.mzw.zhihudaily.view.base.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by M on 2015/11/10.
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = L.makeLogTag(SplashActivity.class);

    @Bind(R.id.iv_splash)
    ImageView mImageView;
    @Bind(R.id.tv_splash)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        loadImage();
        startAnimation();
    }

    private void loadImage() {
        mZhihuService.startImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StartImage>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SplashActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(StartImage startImage) {
                        mTextView.setText(startImage.author);
                        Picasso.with(App.getContext()).load(startImage.imageUrl).into(mImageView);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
