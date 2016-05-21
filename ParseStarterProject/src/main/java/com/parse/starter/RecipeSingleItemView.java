package com.parse.starter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Created by Dahan on 08/05/2016.
 */
public class RecipeSingleItemView extends AppCompatActivity {

    //Declare variables
    Recipe recipe;
    List<ParseObject> ob;
    //ImageLoader imageLoader = new ImageLoader(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get view from singleitemview.xml
        setContentView(R.layout.singleitemview);

        String languageToLoad  = "he";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.singleitemview);


        Intent i = getIntent();
        recipe = (Recipe) i.getSerializableExtra("recipe");

        //Log.i("Json", (recipe.getDirections()).toString());
        //Log.i("Json", String.valueOf(recipe.getIngredients()));



        //Locate textviews in singleitemview.xml
        TextView txtlevel = (TextView) findViewById(R.id.level);
        TextView txttype = (TextView) findViewById(R.id.type);
        TextView txttitle = (TextView) findViewById(R.id.title);
        TextView txtOverallTime = (TextView) findViewById(R.id.overallTime);


        //Set results into Textviews
        txtlevel.setText(recipe.level);
        txttype.setText(recipe.type);
        txttitle.setText(recipe.title);
        txtOverallTime.setText(Integer.toString(recipe.getOverallTime()));


        //Locate ImageView in singleitemview.xml
        ImageView imageView = (ImageView)findViewById(R.id.image);


        String imageURL = "";

        if(recipe.imageName == null){

            imageURL = recipe.getImageFileURL();

        }else{
            imageURL = "https://googledrive.com/host/0B1tVd-X6T988bE1yUDdJa0FMRTQ/"+recipe.getImageName();
        }

        Picasso.with(getApplicationContext()).load(imageURL).fit().centerCrop().into(imageView);

      /* ImageView timerImage = (ImageView) findViewById(R.id.timer);

        Picasso.with(getApplicationContext()).load(R.id.timer).fit().resize(20,20).into(timerImage);*/

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        //imageLoader.DisplayImage(image, imageView);


    }
}
