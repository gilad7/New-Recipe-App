package com.parse.starter;

import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by Dahan on 08/05/2016.
 */
public class Recipe implements Serializable {

    public String id;
    public String title;
    public String type;
    public String level;
    public String imageFileURL;
    public String imageName;
    public int cookTime;
    public int prepTime;
    public ParseObject directions;

    public ParseObject getDirections() {
        return directions;
    }

    public void setDirections(ParseObject directions) {
        this.directions = directions;
    }


    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImageFileURL() {
        return imageFileURL;
    }

    public void setImageFileURL(String imageFileURL) {
        this.imageFileURL = imageFileURL;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }


}
