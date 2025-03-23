package com.grv;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    private ListView listViewVizinhos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        carregarComponentes();
        carregarEventos();

        atualizarDadosTela();
    }

    private void carregarComponentes() {
        listViewVizinhos = findViewById(R.id.listViewVizinhos);
    }

    private void carregarEventos() {
        listViewVizinhos.setOnItemClickListener((parent, view, position, id) -> {
            Vizinho v = (Vizinho) parent.getItemAtPosition(position);
            String msg = String.format("Clicou no vizinho %s,", v.getNome());
            mostrarAviso(msg);
        });
    }

    private void atualizarDadosTela() {
        String[] nomes = getResources().getStringArray(R.array.nomes_vizinhos);
        String[] enderecos = getResources().getStringArray(R.array.enderecos_vizinhos);
        String[] contatos = getResources().getStringArray(R.array.contatos_vizinhos);
        String[] obs = getResources().getStringArray(R.array.observacoes_vizinhos);
        String[] niveisConfianca = getResources().getStringArray(R.array.niveis_confianca_vizinhos);

        List<Vizinho> listaVizinhos = new ArrayList<>();
        int qtd = nomes.length;

        for (int i = 0; i < qtd; i++) {
            NivelConfianca nc = NivelConfianca.fromNivel(niveisConfianca[i]);
            listaVizinhos.add(new Vizinho(nomes[i], enderecos[i], contatos[i], obs[i], nc));
        }

        VizinhoAdapter va = new VizinhoAdapter(this, listaVizinhos);
        listViewVizinhos.setAdapter(va);
    }
    private void mostrarAviso(String msg) {
        Toast.makeText(ListagemActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
