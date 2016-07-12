package com.parse.starter;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

/**
 * Created by Dahan on 23/05/2016.
 */
public class RecipeSingleItemView extends AppCompatActivity {
    //Declare variables
    Recipe recipe;
    ListView listView;
    SingleRecipeAdapter adapter;
    private List<Recipe> recipeList = null;

    JSONObject directions;
    JSONObject ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_single_item_new);

        //Make the App in Hebrew
        String languageToLoad  = "he";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.listview_single_item_new);

        Typeface dataTitleIcon = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("recipe");

        String directionsString =  intent.getStringExtra("directions");
        String ingredientsString =  intent.getStringExtra("ingredients");


        try {
            ingredients = new JSONObject(ingredientsString);
            directions = new JSONObject(directionsString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //locate list view in listview_main-activity.xmlivity.xml
        listView = (ListView)findViewById(R.id.listview);
        //pass the results to ArrayAdapter
        adapter = new SingleRecipeAdapter(RecipeSingleItemView.this, recipe, ingredients, directions, dataTitleIcon);
        //Bind the adapter to the listview
        listView.setAdapter(adapter);



    }


}
