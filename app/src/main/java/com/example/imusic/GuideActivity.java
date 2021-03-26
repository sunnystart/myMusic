package com.example.imusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.imusic.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private final int[] guideImgIds = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
    private final List<View> guidePages = new ArrayList<>();
    private ViewPager viewPager;
    private ImageView[] mDotView;
    private ViewPager.OnPageChangeListener mPageChangeListener;
    private Button enterBtn;
    private Button skipBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initBtn();
        initDotView();
        initViewPager();
        initPageListener();
    }

    /**
     * 设置引导页viewPager
     */
    private void initViewPager() {
        for (int imgId : guideImgIds) {
            View view = getLayoutInflater().inflate(R.layout.activity_page, null);
            ImageView imgView = view.findViewById(R.id.image);
            imgView.setImageResource(imgId);
            guidePages.add(view);
        }
        viewPager = findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return guideImgIds.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View child = guidePages.get(position);
                container.addView(guidePages.get(position));
                return child;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(guidePages.get(position));
            }
        };
        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * 设置引导点
     */
    private void initDotView() {
        LinearLayout dotLayout =  findViewById(R.id.dot_layout);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(20, 20);
        mParams.setMargins(10, 0, 10,0);
        mDotView = new ImageView[guideImgIds.length];
        for(int i = 0; i < mDotView.length; i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.dot_selector);
            imageView.setSelected(i == 0);
            mDotView[i] = imageView;
            dotLayout.addView(imageView);
        }

        enterBtn.setVisibility(View.INVISIBLE);
        skipBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 设置引导页listener，用于控制在不同上按钮的展示状态
     */
    private void initPageListener() {
        mPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i < mDotView.length; i++)
                {
                    mDotView[i].setSelected(i==position);
                }

                if (position == mDotView.length-1) {
                    enterBtn.setVisibility(View.VISIBLE);
                    skipBtn.setVisibility(View.INVISIBLE);
                } else {
                    enterBtn.setVisibility(View.INVISIBLE);
                    skipBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPager.addOnPageChangeListener(mPageChangeListener);
    }

    /**
     * 设置按钮监听事件
     */
    private void initBtn() {
        enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity();
            }
        });

        skipBtn = findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity();
            }
        });
    }

    /**
     * 按钮事件，拉起主页
     */
    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        SpUtils.saveGuideInfo(this);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        viewPager.removeOnPageChangeListener(mPageChangeListener);
        super.onDestroy();
    }
}