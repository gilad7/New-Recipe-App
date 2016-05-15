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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
  //Declare variables

  ListView listView;
  List<ParseObject> ob;
  ProgressDialog mProgressDialog;
  RecipesListView adapter;
  private List<Recipe> recipeList = null;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Get the view from listview_main.xml
    setContentView(R.layout.listview_main);
    // Execute RemoteDataTask AsyncTask
     new RemoteDataTask().execute();

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
        // Locate the column named "ranknum" in Parse.com and order list
        // by ascending
        //query.orderByAscending("recipenum");
        ob = query.find();

        for (ParseObject parseRecipe : ob) {

          Recipe recipe = new Recipe();
          recipe.setLevel((String) parseRecipe.get("level"));
          recipe.setTitle((String) parseRecipe.get("title"));
          recipe.setType((String) parseRecipe.get("type"));

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

      //locate list view in listview_main.xml
      listView = (ListView)findViewById(R.id.listview);
      //pass the results to ArrayAdapter
      adapter = new RecipesListView(MainActivity.this, recipeList);
      //Bind the adapter to the listview
      listView.setAdapter(adapter);
      //close progress dialog
      if(mProgressDialog != null && mProgressDialog.isShowing()){
       mProgressDialog.dismiss();
       }

    }


  }
}
