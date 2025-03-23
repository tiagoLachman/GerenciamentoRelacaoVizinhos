package com.grv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    private Button btnAdicionar;
    private Button btnSobre;
    private ListView listViewVizinhos;
    private VizinhoAdapter vizinhoAdapter;
    private List<Vizinho> listaVizinhos;

    private ActivityResultCallback<ActivityResult> arc;
    private ActivityResultLauncher<Intent> arl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        carregarComponentes();
        carregarEventos();
        carregarChamadasActivity();

        atualizarDadosTela();
    }

    private void carregarComponentes() {
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnSobre = findViewById(R.id.btnSobre);
        listViewVizinhos = findViewById(R.id.listViewVizinhos);
    }

    private void carregarEventos() {
        btnSobre.setOnClickListener(v -> inicializarAuditoriaActivity());

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializarCadastroActivity();
            }
        });

        listViewVizinhos.setOnItemClickListener((parent, view, position, id) -> {
            Vizinho v = (Vizinho) parent.getItemAtPosition(position);
            String identificacao = v.getNome();
            if(identificacao == null || identificacao.isEmpty()){
                identificacao = "de contato" + v.getContato();
            }
            String msg = String.format("Clicou no vizinho %s,", identificacao);
            mostrarAviso(msg);
        });
    }

    private void atualizarDadosTela() {
        if (listaVizinhos == null) {
            listaVizinhos = new ArrayList<>();
        }
        if (vizinhoAdapter == null) {
            vizinhoAdapter = new VizinhoAdapter(this, listaVizinhos);
            listViewVizinhos.setAdapter(vizinhoAdapter);
        } else {
            vizinhoAdapter.notifyDataSetChanged();
        }

    }

    private void carregarChamadasActivity() {
        arc = o -> {
            if (o.getResultCode() == RESULT_OK && o.getData() != null) {
                Vizinho v;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    v = o.getData().getSerializableExtra("vizinho", Vizinho.class);
                } else {
                    v = (Vizinho) o.getData().getSerializableExtra(CadastroActivity.RESULT_VIZINHO);
                }
                adicionarVizinho(v);
                atualizarDadosTela();
            }
        };
        arl = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                arc
        );
    }

    private void mostrarAviso(String msg) {
        Toast.makeText(ListagemActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void inicializarAuditoriaActivity() {
        Intent i = new Intent(ListagemActivity.this, AuditoriaActivity.class);
        startActivity(i);
    }

    private void inicializarCadastroActivity() {
        Intent i = new Intent(ListagemActivity.this, CadastroActivity.class);
        arl.launch(i);
    }

    private void adicionarVizinho(Vizinho v) {
        if (listaVizinhos == null) {
            listaVizinhos = new ArrayList<>();
        }
        if (v != null) {
            listaVizinhos.add(v);
        }
    }
}
