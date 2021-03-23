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
    private final int[] layoutIds = {R.layout.activity_page1, R.layout.activity_page2, R.layout.activity_page3};
    private List<View> guidePages = new ArrayList<>();
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

    private void initViewPager() {
        for (int layoutId:layoutIds) {
            guidePages.add(getLayoutInflater().inflate(layoutId, null));
        }
        viewPager = findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return layoutIds.length;
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

    private void initDotView() {
        LinearLayout dotLayout =  findViewById(R.id.dot_layout);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(20, 20);
        mParams.setMargins(10, 0, 10,0);
        mDotView = new ImageView[layoutIds.length];
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

    private void initBtn() {
        enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                SpUtils.saveGuideInfo(GuideActivity.this);
                GuideActivity.this.finish();
            }
        });

        skipBtn = findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                SpUtils.saveGuideInfo(GuideActivity.this);
                GuideActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        viewPager.removeOnPageChangeListener(mPageChangeListener);
        super.onDestroy();
    }
}