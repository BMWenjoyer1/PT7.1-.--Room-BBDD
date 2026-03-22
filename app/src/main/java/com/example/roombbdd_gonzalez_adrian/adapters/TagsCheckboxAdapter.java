package com.example.roombbdd_gonzalez_adrian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roombbdd_gonzalez_adrian.R;
import com.example.roombbdd_gonzalez_adrian.entities.Tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagsCheckboxAdapter extends ArrayAdapter<Tags> {
    private LayoutInflater inflater;
    private Map<Integer, Boolean> selectedTags;
    
    public TagsCheckboxAdapter(@NonNull Context context, @NonNull List<Tags> tags) {
        super(context, 0, tags);
        this.inflater = LayoutInflater.from(context);
        this.selectedTags = new HashMap<>();
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_tag_checkbox, parent, false);
        }
        
        Tags tag = getItem(position);
        if (tag != null) {
            CheckBox checkBox = convertView.findViewById(R.id.checkbox_tag);
            TextView textView = convertView.findViewById(R.id.text_tag);
            
            checkBox.setText("");
            textView.setText(tag.nom);
            checkBox.setChecked(selectedTags.getOrDefault(tag.id, false));
            
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                selectedTags.put(tag.id, isChecked);
            });
        }
        
        return convertView;
    }
    
    public Map<Integer, Boolean> getSelectedTags() {
        return selectedTags;
    }
    
    public void clear() {
        selectedTags.clear();
    }
}

