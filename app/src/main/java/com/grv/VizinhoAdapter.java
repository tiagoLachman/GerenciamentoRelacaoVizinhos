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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_adapter_list_view_5, parent, false);
        }
        if (vizinho == null) {
            return convertView;
        }
        TextView txtTitulo1 = convertView.findViewById(R.id.txtTitulo1);
        TextView txt1 = convertView.findViewById(R.id.txt1);

        TextView txtTitulo2 = convertView.findViewById(R.id.txtTitulo2);
        TextView txt2 = convertView.findViewById(R.id.txt2);

        TextView txtTitulo3 = convertView.findViewById(R.id.txtTitulo3);
        TextView txt3 = convertView.findViewById(R.id.txt3);

        TextView txtTitulo4 = convertView.findViewById(R.id.txtTitulo4);
        TextView txt4 = convertView.findViewById(R.id.txt4);

        TextView txtTitulo5 = convertView.findViewById(R.id.txtTitulo5);
        TextView txt5 = convertView.findViewById(R.id.txt5);


        txtTitulo1.setText(R.string.txtTituloNome);
        txt1.setText(vizinho.getNome());

        txtTitulo2.setText(R.string.txtTituloEndereco);
        txt2.setText(vizinho.getEndereco());

        txtTitulo3.setText(R.string.txtTituloContato);
        txt3.setText(vizinho.getContato());

        txtTitulo4.setText(R.string.txtTituloNivelConfianca);
        txt4.setText(vizinho.getNivelConfianca().getDesc());

        txtTitulo5.setText(R.string.txtTituloObservacao);
        txt5.setText(vizinho.getObservacao());

        return convertView;
    }
}
