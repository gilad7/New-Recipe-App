/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainRecipesView extends AppCompatActivity {

  //Declare variables
  ListView listView;
  List<ParseObject> ob;
  ProgressDialog mProgressDialog;
  RecipesMainAdapter adapter;
  private List<Recipe> recipeList = null;
  static Typeface optionIcon;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //Get the view from listview_main-activity.xmlivity.xml
    setContentView(R.layout.listview_main_activity);

    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    getSupportActionBar().setCustomView(R.layout.actionbar_main);


//    //set typeface
//    optionIcon = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
//    //Options Icons
//    TextView iconAbout;
//    iconAbout = (TextView)findViewById(R.id.iconAbout);
//    String infoIcon = getApplicationContext().getString(R.string.info);
//    Log.i("Context string", infoIcon);
//    iconAbout.setText(infoIcon);
//    iconAbout.setTypeface(optionIcon);


    // Execute RemoteDataTask AsyncTask
    new RemoteDataTask().execute();

    //App in Hebrew
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
      mProgressDialog = new ProgressDialog(MainRecipesView.this);
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
      adapter = new RecipesMainAdapter(MainRecipesView.this, recipeList);
      //Bind the adapter to the listview
      listView.setAdapter(adapter);
      //close progress dialog
      if(mProgressDialog != null && mProgressDialog.isShowing()){
       mProgressDialog.dismiss();
       }

    }


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    switch(id){

//    case R.id.action_settings:
//      return false;

//      case R.id.action_search:
//        return true;
    }
    return super.onOptionsItemSelected(item);
  }


}
