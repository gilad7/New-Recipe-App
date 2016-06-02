package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dahan on 23/05/2016.
 */
public class RecipeSingleItemTRY extends AppCompatActivity {
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

       new  RemoteDataTask().execute();

        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("recipe");

        String directionsString =  intent.getStringExtra("directions");
        String ingredientsString =  intent.getStringExtra("ingredients");


        try {
            ingredients = new JSONObject(ingredientsString);
            directions = new JSONObject(directionsString);

//            JSONArray ingredientsArray = ingredients.getJSONArray("general");



//            Log.i("json", json);





         /*   JSONArray array = jsonObject.getJSONArray("general");
            int count= 0;
                String ingredients;
            while(count<array.length()){

                JSONObject jo = array.getJSONObject(count);
                ingredients = jo.getString("general");

                Log.i("ingredients", ingredients);


            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

/*
        try {
//            JSONObject jsonObject = new JSONObject(ingredients);
//            JSONArray jsonArray = jsonObject.getJSONArray("general");
//            JSONObject recipeJSON = (JSONObject) jsonArray.getJSONObject(0);
//            JSONArray ja = recipeJSON.getJSONArray("general");

            for(int i=0; i<jsonArray.length(); i++ ){
                JSONObject recipe = jsonArray.getJSONObject(i);
                Log.i("ingredients", recipe.toString());
            }
//            Log.i("json", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //Works
//       Log.i("ddd", ingredients);


    }


    class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            recipeList = new ArrayList<Recipe>();


            recipe.getLevel();
            recipe.getPrepTime();
            recipe.getTitle();
            recipe.getType();
            recipe.getCookTime();
            recipe.getOverallTime();
            recipe.getImageName();
            recipe.getImageFileURL();
            //not sure
            recipe.getIngredients();
            recipe.getDirections();
            //not sure
            recipeList.add(recipe);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //super.onPostExecute(result);
            //locate list view in listview_main-activity.xmlivity.xml
            listView = (ListView)findViewById(R.id.listview);
            //pass the results to ArrayAdapter
            adapter = new SingleRecipeAdapter(RecipeSingleItemTRY.this, recipe, ingredients, directions);
            //Bind the adapter to the listview
            listView.setAdapter(adapter);
        }
    }


}
