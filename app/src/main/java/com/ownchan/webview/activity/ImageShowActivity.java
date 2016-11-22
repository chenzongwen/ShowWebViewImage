package com.ownchan.webview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ownchan.webview.R;

/**
 * Author: Owen Chan
 * DATE: 2016-11-22.
 */

public class ImageShowActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imgUrl = getIntent().getStringExtra("IMG_URL");
        setContentView(R.layout.activity_image_show);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Glide.with(this).load(imgUrl).into(imageView);
    }

}
