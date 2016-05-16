package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dahan on 08/05/2016.
 */
public class RecipesMainAdapter extends BaseAdapter {
    //Declare variables
    Context context;
    LayoutInflater inflater;
    private List<Recipe> recipeslist = null;
    private ArrayList<Recipe> arrayList;
    //ImageLoader imageLoader;

    public RecipesMainAdapter(Context context, List<Recipe> recipeslist){

        this.context = context;
        this.recipeslist = recipeslist;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Recipe>();
        this.arrayList.addAll(recipeslist);
        //imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {

        ImageView image;
        TextView title;
        TextView type;
        TextView level;
        TextView overallTime;
        TextView prepTime;
        TextView cookTime;
    }

    @Override
    public int getCount() {
        return recipeslist.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if(view == null){

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.recipes_main_listview, null);
            //Locate TextViews in listview_recipes_itemspes-items.xml
            holder.title = (TextView)view.findViewById(R.id.title);
            holder.level = (TextView)view.findViewById(R.id.level);
            holder.type = (TextView)view.findViewById(R.id.type);
            holder.overallTime = (TextView) view.findViewById(R.id.overallTime);
            //Locate ImageView in listview_recipes-items.xmlitems.xml
            holder.image = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final Recipe recipe = recipeslist.get(position);
        //set results in TextView
        holder.level.setText(recipe.getLevel());
        holder.title.setText(recipe.getTitle());
        holder.type.setText(recipe.getType());
        holder.overallTime.setText(Integer.toString(recipe.getCookTime()+recipe.getPrepTime()));
        //set results into ImageView
        //imageLoader.DisplayImage(recipeslist.get(position).getImage(), holder.image);


        String imageURL = "";

        if(recipe.getImageName() == null){

            imageURL = recipe.getImageFileURL();

        }else {
            imageURL = "https://googledrive.com/host/0B1tVd-X6T988bE1yUDdJa0FMRTQ/"+recipe.getImageName();

        }

        Picasso.with(context).load(imageURL).fit().centerCrop().into(holder.image);

        //ListView item click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send single item click data to RecipeSingleItemView Class
                Intent intent = new Intent(context, RecipeSingleItemView.class);

                intent.putExtra("recipe",  recipe);
                //Start RecipeSingleItemView.class
                context.startActivity(intent);

            }
        });


        return view;
    }
}
