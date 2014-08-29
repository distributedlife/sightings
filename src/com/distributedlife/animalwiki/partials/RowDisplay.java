package com.distributedlife.animalwiki.partials;

import android.view.View;
import android.view.ViewGroup;

public interface RowDisplay {
    View display(View convertView, ViewGroup parent, Object data);
}
