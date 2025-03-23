package com.grv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VizinhoAdapter extends ArrayAdapter<Vizinho> {
    public VizinhoAdapter(@NonNull Context context, @NonNull List<Vizinho> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Vizinho vizinho = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView txt1 = convertView.findViewById(android.R.id.text1);
//        TextView txt2 = convertView.findViewById(android.R.id.text2);

        txt1.setText(vizinho.toString());
        return convertView;
    }
}
