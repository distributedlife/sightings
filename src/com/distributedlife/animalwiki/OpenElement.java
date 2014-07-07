package com.distributedlife.animalwiki;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class OpenElement implements View.OnClickListener {
    private final String name;
    private final Activity owner;

    public OpenElement(String name, Activity owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(owner, Element.class);
        intent.putExtra("name", name);
        owner.startActivity(intent);
    }
}