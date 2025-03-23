package com.grv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listagem_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_adicionar) {
            inicializarCadastroActivity(null);
            return true;
        } else if (id == R.id.item_sobre) {
            inicializarAuditoriaActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
                inicializarCadastroActivity(null);
            }
        });

        MenuItem.OnMenuItemClickListener menuItemClickListener = item -> {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            if (info == null) {
                return false;
            }
            int position = info.position;
            Vizinho vizinhoSelecionado = listaVizinhos.get(position);
            int id = item.getItemId();
            if (id == R.id.item_editar) {
                inicializarCadastroActivity(vizinhoSelecionado);
                return true;
            } else if (id == R.id.item_excluir) {
                excluirItem(vizinhoSelecionado);
                atualizarDadosTela();
                return true;
            } else {
                return false;
            }
        };

        listViewVizinhos.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            getMenuInflater().inflate(R.menu.listagem_selecionado_opcoes, menu);

            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
            }
        });

        listViewVizinhos.setOnItemClickListener((parent, view, position, id) -> {
            Vizinho v = (Vizinho) parent.getItemAtPosition(position);
            String identificacao = v.getNome();
            if (identificacao == null || identificacao.isEmpty()) {
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

    private void inicializarCadastroActivity(Vizinho v) {
        Intent i = new Intent(ListagemActivity.this, CadastroActivity.class);
        if(v != null){
            i.putExtra(CadastroActivity.REQUEST_VIZINHO, v);
        }
        arl.launch(i);
    }

    private void excluirItem(Vizinho vizinho) {
        if (listaVizinhos == null) {
            listaVizinhos = new ArrayList<>();
            return;
        }
        if(vizinho == null || vizinho.getId() == null){
            return;
        }
        listaVizinhos.removeIf(item -> Objects.equals(item.getId(), vizinho.getId()));
    }

    private void adicionarVizinho(Vizinho v) {
        if (listaVizinhos == null) {
            listaVizinhos = new ArrayList<>();
        }
        if (v != null) {
            if (v.getId() != null) {
                excluirItem(v);
            }else{
                v.setId((long) listaVizinhos.size());
            }
            listaVizinhos.add(v);
        }
    }
}
