package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dahan on 08/05/2016.
 */
public class SingleItemView extends AppCompatActivity {

    //Declare variables
    String title;
    String level;
    String type;
    String image;
    ImageLoader imageLoader = new ImageLoader(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get view from singleitemview.xml
        setContentView(R.layout.singleitemview);

        Intent i = getIntent();
        //get type
        type = i.getStringExtra("type");
        //get level
        level = i.getStringExtra("level");
        //get title
        title = i.getStringExtra("title");
        //get image
        image = i.getStringExtra("image");

        //Locate textviews in singleitemview.xml
        TextView txtlevel = (TextView) findViewById(R.id.level);
        TextView txttype = (TextView) findViewById(R.id.type);
        TextView txttitle = (TextView) findViewById(R.id.title);



        //Set results into Textviews
        txtlevel.setText(level);
        txttype.setText(type);
        txttitle.setText(title);

        //Locate ImageView in singleitemview.xml
        ImageView imageView = (ImageView)findViewById(R.id.image);

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(image, imageView);


    }
}
