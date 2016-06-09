package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dahan on 16/05/2016.
 */
public class SingleRecipeAdapter extends BaseAdapter {
    //Declare variables
    Context context;
    Recipe recipe;

    JSONObject directions;
    JSONObject ingredients;

    private LayoutInflater inflater;

    private static final int TYPE_DETAILS = 0;
    private static final int TYPE_TITLE = 1;
    private static final int TYPE_INGREDIENT = 2;
    private static final int TYPE_DIRECTION = 3;
    private static final int TYPE_INGREDIENT_SUBTITLE = 4;
    private static final int TYPE_DIRECTION_SUBTITLE = 5;


    public SingleRecipeAdapter(Context context, Recipe recipe, JSONObject ingredients, JSONObject directions){

        this.context = context;
        this.recipe = recipe;
        inflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
        this.directions = directions;

    }


    @Override
    public int getCount() {

        int ret = 1 + 1 + getIngredientsCount() + 1 + getDirectionsCount();
//        Log.i("myTag count", String.valueOf(ret));
        return ret;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return recipe;
    }

    @Override
    public int getItemViewType(int position) {
      //  Log.i("myTag Type position", String.valueOf(position));
        if (position == 0) {
            // Details type
            return TYPE_DETAILS;
        }
        if (isIngredientsTitlePosition(position)) {
            // ingredients title type
            return TYPE_TITLE;
        }
        if (isIngredientPosition(position)) {
            // ingredient type
            return TYPE_INGREDIENT;
        }
        if (isDirectionsTitlePosition(position)) {
            // directions title type
            return TYPE_TITLE;
        }
        if (isDirectionPosition(position)) {
            // directions type
            return TYPE_DIRECTION;
        }

        Log.i("myTag WARNING", "myTag WARNING");

            return 0;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        // details
        ImageView image;
        TextView title;
        TextView type;
        TextView level;
        TextView overallTime;
        TextView prepTime;
        TextView cookTime;

        // data title
        TextView dataTitle;

        //ingredients section
        TextView ingredientText;
        TextView ingredientAmount;

        //direction section
        TextView directionText;
        TextView directionAmount;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

      //  Log.i("myTag view position", String.valueOf(position));
       final ViewHolder holder;
        int type = getItemViewType(position);

        if(view == null){

            holder = new ViewHolder();
            switch (type) {
                case TYPE_DETAILS:
                    view = inflater.inflate(R.layout.singleitemview2try, null);
                    //Locate TextViews in singlitemview2try.xml
                    holder.image = (ImageView) view.findViewById(R.id.image);
                    holder.title = (TextView) view.findViewById(R.id.title);
                    holder.level = (TextView) view.findViewById(R.id.level);
                    holder.type = (TextView) view.findViewById(R.id.type);
                    holder.overallTime = (TextView) view.findViewById(R.id.overallTime);
                    holder.prepTime = (TextView) view.findViewById(R.id.numberPrepTime);
                    holder.cookTime = (TextView) view.findViewById(R.id.numberCookTime);
                    break;
                case TYPE_TITLE:
                    view = inflater.inflate(R.layout.single_recipe_data_title, null);
                    if(isIngredientsTitlePosition(position)){
                        holder.dataTitle = (TextView)view.findViewById(R.id.titleTextView);
                    }
                    if(isDirectionsTitlePosition(position)){
                        holder.dataTitle = (TextView)view.findViewById(R.id.titleTextView);
                    }
                    break;
                case TYPE_INGREDIENT:
                    view = inflater.inflate(R.layout.single_recipe_ingredient, null);
                    holder.ingredientAmount = (TextView)view.findViewById(R.id.ingredientAmount);
                    holder.ingredientText = (TextView)view.findViewById(R.id.ingredientText);
                    break;
                case TYPE_DIRECTION:
                    view = inflater.inflate(R.layout.single_recipe_direction, null);
                    holder.directionAmount = (TextView) view.findViewById(R.id.directionAmount);
                    holder.directionText = (TextView) view.findViewById(R.id.directionText);
                    break;
                default:
                    break;

            }
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }



        switch (type) {
            case TYPE_DETAILS:
                //set results in TextView
                holder.level.setText(recipe.getLevel());
                holder.title.setText(recipe.getTitle());
                holder.type.setText(recipe.getType());
                holder.overallTime.setText(Integer.toString(recipe.getOverallTime()));
                holder.prepTime.setText(Integer.toString(recipe.getPrepTime()));
                holder.cookTime.setText(Integer.toString(recipe.getCookTime()));
                //Set image
                String imageURL = "";

                if(recipe.getImageName() == null){
                    imageURL = recipe.getImageFileURL();
                }else {

                    imageURL = "https://googledrive.com/host/0B1tVd-X6T988bE1yUDdJa0FMRTQ/"+recipe.getImageName();
                }
                Picasso.with(context).load(imageURL).fit().centerCrop().into(holder.image);
                break;

            case TYPE_TITLE:
                //set both titles ingredients and directions
                if(isIngredientsTitlePosition(position)){
                    holder.dataTitle.setText(context.getString(R.string.ingredients));
                }
                if(isDirectionsTitlePosition(position)){
                    holder.dataTitle.setText(context.getString(R.string.directions));
                }
                break;

            case TYPE_INGREDIENT:
                int ingredientIndex = position - 2;
//                Log.i("myTag index ingredients", String.valueOf(ingredientIndex));
                try {
                    String ingText = ingredients.getJSONArray("general").get(ingredientIndex).toString();

//                    Log.i("myTag ingTXT", ingText);

                    Pattern p = Pattern.compile("(\\d+)?\\|(.+)");
                    Matcher m = p.matcher(ingText);
                    if (m.matches()) {

                        holder.ingredientText.setText(m.group(2));
                        holder.ingredientAmount.setText(m.group(1));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case TYPE_DIRECTION:
                    int directionIndex = position - 2 - getIngredientsEndPosition();

                    try {
                        String dirText = directions.getJSONArray("general").get(directionIndex).toString();

                        holder.directionAmount.setText(String.valueOf(directionIndex + 1));
                        holder.directionText.setText(dirText);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                break;

            default:
                break;

        }


        return view;
    }


    private Boolean isIngredientsTitlePosition(int position) {
        return position == 1;
    }

    private Boolean isIngredientPosition(int position) {
        return position >= 2 && position <= getIngredientsEndPosition();
    }

    private int getIngredientsEndPosition (){
        return getIngredientsCount() + 1;
    }

    private int getDirectionsTitlePosition (){
        return getIngredientsEndPosition() + 1;
    }

    private Boolean isDirectionsTitlePosition (int position){
        return position == getDirectionsTitlePosition();
    }

    private Boolean isDirectionPosition(int position){


        return position >= getDirectionsTitlePosition() + 1 &&
               position <= getDirectionsCount() + getDirectionsTitlePosition();
    }

    private int getIngredientsCount (){
        try {
            int ret = ingredients.getJSONArray("general").length();


            return  ret;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getDirectionsCount (){
        try {
            int ret =  directions.getJSONArray("general").length();

          //  Log.i("Tag dir", String.valueOf(ret));

            return ret;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
