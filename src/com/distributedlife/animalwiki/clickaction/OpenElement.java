package com.distributedlife.animalwiki.clickaction;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.distributedlife.animalwiki.activities.AnimalDisplay;

public class OpenElement implements View.OnClickListener {
    private final String name;
    private final Activity owner;

    public OpenElement(String name, Activity owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(owner, AnimalDisplay.class);
        intent.putExtra("name", name);
        owner.startActivity(intent);
    }
}