package com.example.nickson.e_kwaderno;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GettingStartedActivity extends AppCompatActivity {

    private ViewPager __viewPager;
    private LinearLayout __dotLayout;
    private TextView[] __dots;
    private int[] __layouts;
    private Button __btnPrevious;
    private Button __btnNext;
    private Button __btnSkip;
    private Button __btnFinish;

    private SliderAdapter __sliderAdapter;

    private int __currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        if (!isFirstTimeStartApp()) {
            startMainActivity();
            finish();
        }

        __viewPager = findViewById(R.id.viewPager);
        __dotLayout = findViewById(R.id.dotLayout);
        __btnNext = findViewById(R.id.btnNext);
        __btnPrevious = findViewById(R.id.btnPrev);
        __btnSkip = findViewById(R.id.btnSkip);
        __btnFinish = findViewById(R.id.btnFinish);

        setStatusBarTransparent();

        // Instantiate Slider Adapter
        __sliderAdapter = new SliderAdapter(this);
        __viewPager.setAdapter(__sliderAdapter);

        // addOnPageChangeListener
        addDotsIndicator(0);
        __viewPager.addOnPageChangeListener(viewListener);

        /*
        * - START -
        * OnClickListeners.
        * Button Next = go to next page.
        * Button Previous = go to previous page.
        * Button Skip = skips the Getting Started course and redirect to main page.
        * Button Finish = when Getting Started course is finished, go to main page.
        */
        __btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                __viewPager.setCurrentItem(__currentPage + 1);
            }
        });

        __btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                __viewPager.setCurrentItem(__currentPage - 1);
            }
        });

        __btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });

        __btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
        /*
        * - END -
        */
    }

    private boolean isFirstTimeStartApp() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return preferences.getBoolean("firstTimeFlag", true);
    }

    private void setFirstTimeStartApp(boolean status) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstTimeFlag", status);
        editor.commit();
    }

    private void addDotsIndicator (int position) {
        __dots = new TextView[3];
        __dotLayout.removeAllViews();

        for (int i = 0; i < __dots.length; i++) {
            __dots[i] = new TextView(this);
            __dots[i].setText(Html.fromHtml("&#8226;"));
            __dots[i].setTextSize(35);
            __dots[i].setTextColor(getResources().getColor(R.color.colorDirtyWhite));

            __dotLayout.addView(__dots[i]);
        }

        if (__dots.length > 0) {
            __dots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            __currentPage = position;

            if (position == 0) {
                __btnSkip.setEnabled(true);
                __btnPrevious.setEnabled(false);
                __btnNext.setEnabled(true);
                __btnFinish.setEnabled(false);

                __btnSkip.setVisibility(View.VISIBLE);
                __btnPrevious.setVisibility(View.INVISIBLE);
                __btnNext.setVisibility(View.VISIBLE);
                __btnFinish.setVisibility(View.INVISIBLE);

                __btnPrevious.setText("");
                __btnNext.setText("Next");
            } else if (position == __dots.length - 1) {
                __btnSkip.setEnabled(false);
                __btnPrevious.setEnabled(true);
                __btnNext.setEnabled(false);
                __btnFinish.setEnabled(true);

                __btnSkip.setVisibility(View.INVISIBLE);
                __btnPrevious.setVisibility(View.VISIBLE);
                __btnNext.setVisibility(View.INVISIBLE);
                __btnFinish.setVisibility(View.VISIBLE);

                __btnPrevious.setText("Back");
                __btnNext.setText("");
            } else {
                __btnSkip.setEnabled(false);
                __btnPrevious.setEnabled(true);
                __btnNext.setEnabled(true);
                __btnFinish.setEnabled(false);

                __btnSkip.setVisibility(View.INVISIBLE);
                __btnPrevious.setVisibility(View.VISIBLE);
                __btnNext.setVisibility(View.VISIBLE);
                __btnFinish.setVisibility(View.INVISIBLE);

                __btnPrevious.setText("Back");
                __btnNext.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void startMainActivity() {
        setFirstTimeStartApp(false);
        startActivity(new Intent(GettingStartedActivity.this, LoginActivity.class));
        finish();
    }

    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
