/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ListView;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

  //Declare variables
  ListView listView;
  List<ParseObject> ob;
  ProgressDialog mProgressDialog;
  RecipesMainAdapter adapter;
  private List<Recipe> recipeList = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //Get the view from listview_main-activity.xmlivity.xml
    setContentView(R.layout.listview_main_activity);
    // Execute RemoteDataTask AsyncTask
    new RemoteDataTask().execute();

//    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
//    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);


    String languageToLoad  = "he";
    Locale locale = new Locale(languageToLoad);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    getBaseContext().getResources().updateConfiguration(config,
            getBaseContext().getResources().getDisplayMetrics());
    this.setContentView(R.layout.listview_main_activity);


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  // RemoteDataTask AsyncTask
  private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      // Create a progressdialog
      mProgressDialog = new ProgressDialog(MainActivity.this);
      // Set progressdialog title
      mProgressDialog.setTitle("Recipe");
      // Set progressdialog message
      mProgressDialog.setMessage("Loading...");
      mProgressDialog.setIndeterminate(false);
      // Show progressdialog
      mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
      // Create the array
      recipeList = new ArrayList<Recipe>();

      try {
        // Locate the class table named "Recipe" in Parse.com
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Recipe");

        ob = query.find();

        for (final ParseObject parseRecipe : ob) {


          final Recipe recipe = new Recipe();

          recipe.setLevel((String) parseRecipe.get("level"));
          recipe.setTitle((String) parseRecipe.get("title"));
          recipe.setType((String) parseRecipe.get("type"));
          recipe.setCookTime((Integer) parseRecipe.get("cookTime"));
          recipe.setPrepTime((Integer) parseRecipe.get("prepTime"));

         recipe.setDirections(parseRecipe.getJSONObject("directions"));
         recipe.setIngredients(parseRecipe.getJSONObject("ingredients"));


          String imageName = (String)parseRecipe.get("imageName");

          if(imageName == null) {
            // Locate images in image column
            ParseFile image = (ParseFile) parseRecipe.get("image");
            recipe.setImageFileURL(image.getUrl());
          }else{
            recipe.setImageName(imageName);
          }

          recipeList.add(recipe);
        }
      } catch (ParseException e) {
        Log.e("Error", e.getMessage());
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {

      //locate list view in listview_main-activity.xmlivity.xml
      listView = (ListView)findViewById(R.id.listview);
      //pass the results to ArrayAdapter
      adapter = new RecipesMainAdapter(MainActivity.this, recipeList);
      //Bind the adapter to the listview
      listView.setAdapter(adapter);
      //close progress dialog
      if(mProgressDialog != null && mProgressDialog.isShowing()){
       mProgressDialog.dismiss();
       }

    }


  }
}
