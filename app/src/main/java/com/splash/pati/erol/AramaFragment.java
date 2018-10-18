package com.splash.pati.erol;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class AramaFragment extends Fragment {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private ImageButton mlikeBtn,mdlikeBtn;

    private int mCurrentPage;


    public AramaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_arama, container, false);
        mSlideViewPager = (ViewPager)view.findViewById(R.id.slideViewPager);


        mlikeBtn = (ImageButton)view.findViewById(R.id.like);
        mdlikeBtn = (ImageButton)view.findViewById(R.id.disslike);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mdlikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage +1);
            }
        });

        mlikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage +1);
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            mCurrentPage = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
