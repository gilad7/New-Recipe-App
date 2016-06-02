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

/**
 * Created by Dahan on 16/05/2016.
 */
public class SingleRecipeAdapter extends BaseAdapter {
    //Declare variables
    Context context;
    LayoutInflater inflater;
//    private List<Recipe> recipelist = null;
//    private ArrayList<Recipe> arrayList;
    Recipe recipe;

    JSONObject directions;
    JSONObject ingredients;

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
//        this.arrayList = new ArrayList<Recipe>();
//        this.arrayList.addAll(recipeslist);

    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return recipe;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // Details type
            return TYPE_DETAILS;
        }
        if (isIngredientsTitlePosition(position)) {
            // ingredients title type
            return TYPE_TITLE;
        }
        if (isIngredientPosition(position)) {
            // infgredient type
            return TYPE_INGREDIENT;
        }
        if (isDirectionsTitlePosition(position)) {
            // directions title type
            return TYPE_TITLE;
        }
        if (isDirectionPosition(position)) {
            // directions title type
            return TYPE_DIRECTION;
        }
            return 0;
    }
    

    private Boolean isIngredientsTitlePosition(int position) {
        return position == 1;
    }

    private Boolean isIngredientPosition(int position) {
        return position >= 2 && position < getIngredientsEndPosition(position);
    }

    private int getIngredientsEndPosition (int position){
        try {
            return recipe.ingredients.getJSONArray("general").length() + 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getDirectionsTitlePosition (int position){
        return getIngredientsEndPosition(position) + 1;
    }

    private Boolean isDirectionsTitlePosition (int position){
        return position == getDirectionsTitlePosition(position);
    }

    private  Boolean isDirectionPosition(int position){

        return position == getDirectionsTitlePosition(position) +1;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        ImageView image;
        TextView title;
        TextView type;
        TextView level;
        TextView overallTime;
        TextView prepTime;
        TextView cookTime;
        TextView directions;
        TextView ingredients;
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if(view == null){

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.singleitemview2try, null);
            //Locate TextViews in listview_recipes_itemspes-items.xml
            holder.title = (TextView)view.findViewById(R.id.title);
            holder.level = (TextView)view.findViewById(R.id.level);
            holder.type = (TextView)view.findViewById(R.id.type);
            holder.overallTime = (TextView) view.findViewById(R.id.overallTime);
            holder.prepTime = (TextView) view.findViewById(R.id.numberPrepTime);
            holder.cookTime = (TextView) view.findViewById(R.id.numberCookTime);
            holder.ingredients = (TextView) view.findViewById(R.id.ingredientsText);
//            holder.directions = (TextView) view.findViewById(R.id.directionsText);

            //Locate ImageView in listview_recipes-items.xmlitems.xml
            holder.image = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

//        final Recipe recipe;
        //set results in TextView
        holder.level.setText(recipe.getLevel());
        holder.title.setText(recipe.getTitle());
        holder.type.setText(recipe.getType());
        holder.overallTime.setText(Integer.toString(recipe.getOverallTime()));
        holder.prepTime.setText(Integer.toString(recipe.getPrepTime()));
        holder.cookTime.setText(Integer.toString(recipe.getCookTime()));
//        holder.ingredients.setText((CharSequence) recipe.getIngredients());
//        holder.directions.setText(recipe.getDirections().toString());

//        Log.i("ingredients", String.valueOf(recipe.getIngredients()));


        String imageURL = "";

        if(recipe.getImageName() == null){

            imageURL = recipe.getImageFileURL();

        }else {
            imageURL = "https://googledrive.com/host/0B1tVd-X6T988bE1yUDdJa0FMRTQ/"+recipe.getImageName();

        }

        Picasso.with(context).load(imageURL).fit().centerCrop().into(holder.image);

        return view;
    }




}
