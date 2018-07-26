package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mDescriptionTv;
    private TextView mPlaceOfOriginTv;
    private TextView mAlsoKnownAsTv;
    private TextView mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mDescriptionTv =   findViewById(R.id.description_tv);
        mPlaceOfOriginTv = findViewById(R.id.origin_tv);
        mAlsoKnownAsTv =  findViewById(R.id.also_known_tv);
        mIngredientsTv =  findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.broken)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mDescriptionTv.setText(sandwich.getDescription());

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            mPlaceOfOriginTv.setText(R.string.no_value);
        }else {
            mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs().size() == 0){
            mAlsoKnownAsTv.setText(R.string.no_value);
        }else {
            String formattedAliases = listToString(sandwich.getAlsoKnownAs());
            mAlsoKnownAsTv.setText(formattedAliases);
        }

        String formattedIngredients = listToString(sandwich.getIngredients());
        mIngredientsTv.setText(formattedIngredients);

    }

    /* Converts List to String of items separated with commas */
    private String listToString(List<String> items){
        String result = "";

        StringBuilder t = new StringBuilder();
        for(int i=0; i <= items.size()-1; i++) {
            t.append(items.get(i));
            if(i != items.size()-1)
                t.append(", ");
        }
        result = t.toString();

        return result;
    }
}
