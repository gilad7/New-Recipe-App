package com.parse.starter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Dahan on 08/05/2016.
 */
public class SingleItemView extends AppCompatActivity {

    //Declare variables
    Recipes recipe;
    //ImageLoader imageLoader = new ImageLoader(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get view from singleitemview.xml
        setContentView(R.layout.singleitemview);


        Intent i = getIntent();
        recipe = (Recipes) i.getSerializableExtra("recipe");

        //Locate textviews in singleitemview.xml
        TextView txtlevel = (TextView) findViewById(R.id.level);
        TextView txttype = (TextView) findViewById(R.id.type);
        TextView txttitle = (TextView) findViewById(R.id.title);



        //Set results into Textviews
        txtlevel.setText(recipe.level);
        txttype.setText(recipe.type);
        txttitle.setText(recipe.title);

        //Locate ImageView in singleitemview.xml
        ImageView imageView = (ImageView)findViewById(R.id.image);


        String imageURL = "";

        if(recipe.imageName == null){

            imageURL = recipe.getImageFileURL();

        }else{
            imageURL = "https://googledrive.com/host/0B1tVd-X6T988bE1yUDdJa0FMRTQ/"+recipe.getImageName();
        }

        Picasso.with(getApplicationContext()).load(imageURL).fit().centerCrop().into(imageView);

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        //imageLoader.DisplayImage(image, imageView);


    }
}
