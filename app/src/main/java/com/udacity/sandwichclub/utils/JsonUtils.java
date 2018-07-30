package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        if (json == null) {
            return null;
        }

        try{

            JSONObject jsonObject = new JSONObject(json);

            JSONObject nameJsonObject = jsonObject.getJSONObject("name");

            Sandwich sandwich = new Sandwich();
            sandwich.setMainName(nameJsonObject.getString("mainName"));

            sandwich.setImage(jsonObject.getString("image"));

            JSONArray alsoKnownAsNamesArray = nameJsonObject.getJSONArray("alsoKnownAs");
            sandwich.setAlsoKnownAs(jsonToList(alsoKnownAsNamesArray));
            sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            sandwich.setDescription(jsonObject.getString("description"));

            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
            sandwich.setIngredients(jsonToList(ingredientsArray));

            return sandwich;

        } catch (final JSONException e){
            Log.e(TAG, "JSON error: " + e);
            return null;
        }
    }

    /* Converts JSONArray to string List */
    static private List<String> jsonToList(JSONArray jsonArray) throws JSONException {
        List<String> result = new ArrayList<>();

        if ( jsonArray != null){
            for (int i=0; i< jsonArray.length(); i++){
                result.add(jsonArray.getString(i));
            }
        }
        return result;
    }
}

