package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dahan on 08/05/2016.
 */


public class RecipeSingleItemView extends AppCompatActivity {

    //Declare variables
    Recipe recipe;
    List<ParseObject> ob;
    String directions;
    String ingredients;

    //ImageLoader imageLoader = new ImageLoader(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get view from singleitemview.xml
        setContentView(R.layout.singleitemview);

//        Iconify.with(new FontAwesomeModule());

       /* TextView timer = (TextView) findViewById(R.id.timerFont);
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont");
        timer.setTypeface(font);*/
//        timer.setText(new IconDrawable(this, FontAwesomeIcons.fa_clock_o));


/*
        //Make the App in Hebrew
        String languageToLoad  = "he";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.singleitemview);

        //Get the selected recipe from the main acivity class
        Intent i = getIntent();
        recipe = (Recipe) i.getSerializableExtra("recipe");
        directions =  i.getStringExtra("directions");
        ingredients =  i.getStringExtra("ingredients");


        //Locate textviews in singleitemview.xml
        TextView txtlevel = (TextView) findViewById(R.id.level);
        TextView txttype = (TextView) findViewById(R.id.type);
        TextView txttitle = (TextView) findViewById(R.id.title);
        TextView txtOverallTime = (TextView) findViewById(R.id.overallTime);
        TextView txtDirections = (TextView) findViewById(R.id.directionsText);
        TextView txtIngredients = (TextView) findViewById(R.id.ingredientsText);

        //Convert JSON to string - Directions
        try {
            JSONObject jsonObject = new JSONObject(directions);
             JSONArray general = (JSONArray) jsonObject.get("general");
            for (int j = 0; j < general.length(); j++) {

                Log.i("directions", (String) general.get(j));

                txtDirections.setText((String) general.get(3));

            }

//            txtDirections.setText(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Convert JSON to string - Ingredients
        try {
            JSONObject jsonObject = new JSONObject(ingredients);
            JSONArray general = (JSONArray) jsonObject.get("general");
            for (int j = 0; j < general.length(); j++) {

                Log.i("ingredients", (String) general.get(j));

                txtIngredients.setText((String) general.get(3));

            }

//            txtDirections.setText(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Set results into Textviews
        txtlevel.setText(recipe.level);
        txttype.setText(recipe.type);
        txttitle.setText(recipe.title);
        txtOverallTime.setText(Integer.toString(recipe.getOverallTime()));
        txtDirections.setText(directions);
        txtIngredients.setText(ingredients);


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

*/
    }
}
