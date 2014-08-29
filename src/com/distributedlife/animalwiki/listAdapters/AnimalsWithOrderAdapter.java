package com.distributedlife.animalwiki.listAdapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.clickaction.OpenElement;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.formatting.AnimalFormatting;
import com.distributedlife.animalwiki.model.Animal;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class AnimalsWithOrderAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private List<String> headers;
    private Map<String, List<Animal>> animals;
    private List<Animal> allAnimals;
    private Sightings sightings;
    private Activity owner;

    public AnimalsWithOrderAdapter(Context context, List<String> headers, Map<String, List<Animal>> animals, Sightings sightings, Activity owner) {
//        super(context, R.id.place_to_put_list, allAnimals);
        this.context = context;
        this.headers = headers;
        this.animals = animals;
        this.sightings = sightings;
        this.owner = owner;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Animal animal = allAnimals.get(position);
//
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.animal_list_item, parent, false);
//        }
//
//        convertView.setOnClickListener(new OpenElement(animal.getWikiFileName(), owner));
//        convertView.setOnLongClickListener(new ToggleSeenIt(animal.getCommonName(), owner));
//
//        ((TextView) convertView.findViewById(R.id.label)).setText(animal.getCommonName());
//
//        TextView conservationStatus = (TextView) convertView.findViewById(R.id.conservationStatus);
//        conservationStatus.setText(animal.getConservationStatus().toAbbreviation());
//        conservationStatus.setTextColor(AnimalFormatting.getTextColourForConservationStatus(animal.getConservationStatus()));
//        conservationStatus.setBackgroundColor(AnimalFormatting.getBackgroundColourForConservationStatus(animal.getConservationStatus()));
//
////        for (int i = 0; i < animal.getColours().size(); i++) {
////            String colour = animal.getColour(i);
////            if (colour == null) {
////                View swatch = convertView.findViewById(AnimalFormatting.swatches().get(i));
////                if (swatch != null) {
////                    swatch.setVisibility(View.GONE);
////                }
////
////                continue;
////            }
////
////            View swatch = convertView.findViewById(AnimalFormatting.swatches().get(i));
////            if (swatch != null) {
////                swatch.setBackgroundColor(Color.parseColor(colour));
////            }
////        }
////
////        for (int i = animal.getColours().size(); i < 9; i++) {
////            convertView.findViewById(AnimalFormatting.swatches().get(i)).setVisibility(View.GONE);
////        }
//
//        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageIcon);
//        displayImage(animal, imageView, owner);
//
//
//        if (sightings.hasSighting(animal.getCommonName().toLowerCase())) {
//            ((TextView) convertView.findViewById(R.id.sightings)).setText("I've seen it!");
//        } else {
//            ((TextView) convertView.findViewById(R.id.sightings)).setText("");
//        }
//
//        return convertView;
//    }

    private void displayImage(Animal animal, ImageView imageView, Activity owner1) {
        if (animal.hasImage()) {
            try {
                InputStream ims = owner1.getAssets().open("html/" + animal.getFilename());
                Drawable d = Drawable.createFromStream(ims, null);
                imageView.setImageDrawable(d);
            } catch (IOException e) {
                imageView.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    public void setFilter(List<String> headers, Map<String, List<Animal>> animals) {
        this.headers = headers;
        this.animals = animals;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return animals.get(headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return animals.get(headers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {
        String order = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.order_heading, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.label);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(order);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Animal animal = (Animal) getChild(groupPosition, childPosition);

        AnimalListViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.animal_list_item, parent, false);

            holder = new AnimalListViewHolder();
            holder.setLabel((TextView) convertView.findViewById(R.id.label));
            holder.setConservationStatus((TextView) convertView.findViewById(R.id.conservationStatus));
            holder.setImage((ImageView) convertView.findViewById(R.id.imageIcon));
            holder.setOfficialName((TextView) convertView.findViewById(R.id.officialName));
            holder.setAnimalFrame(convertView.findViewById(R.id.animalFrame));

            convertView.setTag(holder);
        } else {
            holder = (AnimalListViewHolder) convertView.getTag();
        }

        convertView.setOnClickListener(new OpenElement(animal, owner));

        if (animal.isEndemic()) {
            holder.getLabel().setText(String.format("*%s", animal.getCommonName()));
        } else {
            holder.getLabel().setText(animal.getCommonName());
        }

        TextView conservationStatus = holder.getConservationStatus();
        conservationStatus.setText(animal.getConservationStatus().toAbbreviation());
        conservationStatus.setTextColor(AnimalFormatting.getTextColourForConservationStatus(animal.getConservationStatus()));
        conservationStatus.setBackgroundColor(AnimalFormatting.getBackgroundColourForConservationStatus(animal.getConservationStatus()));

        ImageView imageView = holder.getImage();
        displayImage(animal, imageView, owner);

        holder.getOfficialName().setText(animal.getOfficialName());
        holder.getOfficialName().setTypeface(null, Typeface.ITALIC);

        if (sightings.hasSighting(animal)) {
            holder.getAnimalFrame().setBackgroundResource(R.color.backgroundAnimalSighted);
        } else {
            holder.getAnimalFrame().setBackgroundResource(R.color.backgroundAnimalNotSighted);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class AnimalListViewHolder {
        private TextView label;
        private TextView conservationStatus;
        private ImageView image;
        private TextView officialName;
        private View animalFrame;

        public void setLabel(TextView label) {
            this.label = label;
        }

        public void setConservationStatus(TextView conservationStatus) {
            this.conservationStatus = conservationStatus;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public void setOfficialName(TextView officialName) {
            this.officialName = officialName;
        }

        public TextView getLabel() {
            return this.label;
        }

        public TextView getConservationStatus() {
            return conservationStatus;
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getOfficialName() {
            return officialName;
        }

        public void setAnimalFrame(View animalFrame) {
            this.animalFrame = animalFrame;
        }

        public View getAnimalFrame() {
            return animalFrame;
        }
    }
}
