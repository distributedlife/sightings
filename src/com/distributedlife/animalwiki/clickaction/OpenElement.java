package com.distributedlife.animalwiki.clickaction;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.distributedlife.animalwiki.activities.AnimalDisplay;
import com.distributedlife.animalwiki.model.Animal;

public class OpenElement implements View.OnClickListener {
    private final Animal animal;
    private final Activity owner;

    public OpenElement(Animal animal, Activity owner) {
        this.animal = animal;
        this.owner = owner;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(owner, AnimalDisplay.class);
        intent.putExtra("key", animal.getKey());
        owner.startActivity(intent);
    }
}