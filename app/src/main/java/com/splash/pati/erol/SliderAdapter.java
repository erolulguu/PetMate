package com.splash.pati.erol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Aceer on 22.04.2018.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(AramaFragment context){
        this.context = context.getContext();
    }

    //Arrays
    public int[] slide_images = {

            R.drawable.baatmaan,
            R.drawable.supermaan,
            R.drawable.ironmaan,
            R.drawable.camerica,
            R.drawable.hulk,


    };

    public String[] slide_headings = {
            "BATMAN",
            "SUPERMAN",
            "İRONMAN",
            "CAPTAİN AMERİCA",
            "HULK"


    };

    public String[] slide_descs = {
            "MARVEL",
            "MARVEL",
            "MARVEL",
            "MARVEL",
            "MARVEL"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.image);
        TextView slideHeading = (TextView) view.findViewById(R.id.adsoyad);
        TextView slideDescription = (TextView) view.findViewById(R.id.icerik);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);


        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object) ;
    }
}
