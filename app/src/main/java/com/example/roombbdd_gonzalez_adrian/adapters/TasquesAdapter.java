package com.example.roombbdd_gonzalez_adrian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roombbdd_gonzalez_adrian.entities.Tasques;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TasquesAdapter extends ArrayAdapter<Tasques> {
    private LayoutInflater inflater;
    private SimpleDateFormat dateFormat;
    
    public TasquesAdapter(@NonNull Context context, @NonNull List<Tasques> tasques) {
        super(context, 0, tasques);
        this.inflater = LayoutInflater.from(context);
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        
        Tasques tasques = getItem(position);
        if (tasques != null) {
            TextView text1 = convertView.findViewById(android.R.id.text1);
            TextView text2 = convertView.findViewById(android.R.id.text2);
            
            text1.setText(tasques.titol);
            String dataText = dateFormat.format(new Date(tasques.dataCreacio)) + " - " + tasques.estat;
            text2.setText(dataText);
        }
        
        return convertView;
    }
}
