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
    private List<Recipe> recipelist = null;
    private ArrayList<Recipe> arrayList;

    String directions;
    String ingredients;


    public SingleRecipeAdapter(Context context, List<Recipe> recipeslist){

        this.context = context;
        this.recipelist = recipeslist;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Recipe>();
        this.arrayList.addAll(recipeslist);

    }
    @Override
    public int getCount() {
        return recipelist.size();
    }

    @Override
    public Object getItem(int position) {
        return recipelist.get(position);
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
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if(view == null){

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.single_item_try_adapter, null);
            //Locate TextViews in listview_recipes_itemspes-items.xml
            holder.title = (TextView)view.findViewById(R.id.title);
            holder.level = (TextView)view.findViewById(R.id.level);
            holder.type = (TextView)view.findViewById(R.id.type);
            holder.overallTime = (TextView) view.findViewById(R.id.overallTime);

//            holder.directions = (TextView) view.findViewById(R.id.directionsText);



            //Locate ImageView in listview_recipes-items.xmlitems.xml
            holder.image = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final Recipe recipe = recipelist.get(position);
        //set results in TextView
        holder.level.setText(recipe.getLevel());
        holder.title.setText(recipe.getTitle());
        holder.type.setText(recipe.getType());
        holder.overallTime.setText(Integer.toString(recipe.getOverallTime()));

//        holder.directions.setText(recipe.getDirections().toString());


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
