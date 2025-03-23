package com.grv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {
    public static final String RESULT_VIZINHO = "vizinho";
    public static final String REQUEST_VIZINHO = "vizinho";
    private CheckBox cbConheceNome;
    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtContato;
    private EditText txtObservacao;
    private RadioGroup rbTipoContato;
    private Spinner spNivelConfianca;
    private Button btnSalvar;
    private Button btnLimpar;

    private Vizinho vizinhoPersistido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        carregarComponentes();
        carregarEventos();

        atualizarDadosTela();

        carregarDadosIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_salvar) {
            salvarDados();
            return true;
        } else if (id == R.id.item_limpar) {
            limparDadosTela();
            mostrarAviso(R.string.telaLimpada);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void carregarComponentes() {
        cbConheceNome = findViewById(R.id.cbConheceNome);
        txtNome = findViewById(R.id.txtNome);
        txtEndereco = findViewById(R.id.txtEndereco);
        txtContato = findViewById(R.id.txtContato);
        txtObservacao = findViewById(R.id.txtObservacao);
        rbTipoContato = findViewById(R.id.rbTipoContato);
        spNivelConfianca = findViewById(R.id.spNivelConfianca);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnLimpar = findViewById(R.id.btnLimpar);
    }

    private void carregarEventos() {
        cbConheceNome.setOnCheckedChangeListener((buttonView, isChecked) -> {
            atualizarDadosTela();
        });
        btnLimpar.setOnClickListener(v -> {
            limparDadosTela();
            mostrarAviso(R.string.telaLimpada);
        });
        btnSalvar.setOnClickListener(v -> salvarDados());
    }

    private void carregarDadosIntent(Intent i) {
        Vizinho v;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            v = i.getSerializableExtra(REQUEST_VIZINHO, Vizinho.class);
        } else {
            v = (Vizinho) i.getSerializableExtra(REQUEST_VIZINHO);
        }
        if (v != null) {
            vizinhoPersistido = v;
        } else {
            vizinhoPersistido = null;
        }

        if (vizinhoPersistido != null) {
            String nome = vizinhoPersistido.getNome();
            cbConheceNome.setChecked(nome != null && !nome.isEmpty());
            txtNome.setText(nome);
            txtContato.setText(vizinhoPersistido.getContato());
            txtEndereco.setText(vizinhoPersistido.getEndereco());
            txtObservacao.setText(vizinhoPersistido.getObservacao());
            NivelConfianca nivel = vizinhoPersistido.getNivelConfianca();
            if (nivel != null) {
                int position = ((ArrayAdapter<NivelConfianca>) spNivelConfianca.getAdapter()).getPosition(nivel);
                spNivelConfianca.setSelection(position);
            }
        }
    }

    private void atualizarDadosTela() {
        txtNome.setEnabled(cbConheceNome.isChecked());
        ArrayAdapter<NivelConfianca> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                NivelConfianca.values()
        );
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spNivelConfianca.setAdapter(adapter);

        rbTipoContato.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                atualizarInputTypeTela();
            }
        });
        atualizarInputTypeTela();
    }

    private void atualizarInputTypeTela() {
        int checkedId = rbTipoContato.getCheckedRadioButtonId();
        if (checkedId == R.id.rbTipoContatoCelular) {
            txtContato.setInputType(InputType.TYPE_CLASS_PHONE);
            txtContato.setText(txtContato.getText().toString().replaceAll("[^0-9]", ""));
        } else if (checkedId == R.id.rbTipoContatoEmail) {
            txtContato.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
    }

    private void limparDadosTela() {
        txtNome.setText("");
        txtEndereco.setText("");
        txtContato.setText("");
        txtObservacao.setText("");
        rbTipoContato.clearCheck();
        spNivelConfianca.setSelection(0);
        cbConheceNome.setChecked(false);
        atualizarDadosTela();
    }

    private void salvarDados() {
        try {
            retornarObjetoVizinho(validarDados());
        } catch (InterfaceException e) {
            mostrarAviso(e.getIdString());
        }
    }

    private void mostrarAviso(int idString) {
        Toast.makeText(CadastroActivity.this, getString(idString), Toast.LENGTH_LONG).show();
    }

    private Vizinho validarDados() throws InterfaceException {
        String nome = txtNome.getText().toString();
        String contato = txtContato.getText().toString();
        String endereco = txtEndereco.getText().toString();
        String observacao = txtObservacao.getText().toString();
        NivelConfianca nc = (NivelConfianca) spNivelConfianca.getSelectedItem();
        if (isNullOrEmpty(nome) && cbConheceNome.isChecked()) {
            throw new InterfaceException(R.string.nomeNaoInformado);
        }
        if (isNullOrEmpty(contato)) {
            throw new InterfaceException(R.string.contatoNaoInformado);
        }
        if (isNullOrEmpty(endereco)) {
            throw new InterfaceException(R.string.enderecoNaoInformado);
        }
        if (rbTipoContato.getCheckedRadioButtonId() == -1) {
            throw new InterfaceException(R.string.tipoContatoNaoInformado);
        }
        Vizinho rvalue = new Vizinho(
                nome,
                endereco,
                contato,
                observacao,
                nc
        );
        if (vizinhoPersistido != null) {
            rvalue.setId(vizinhoPersistido.getId());
        }
        return rvalue;
    }

    private boolean isNullOrEmpty(String v) {
        return v == null || v.isEmpty();
    }

    private void retornarObjetoVizinho(Vizinho vizinho) {
        Intent i = new Intent();
        i.putExtra(RESULT_VIZINHO, vizinho);
        setResult(RESULT_OK, i);
        finish();
    }
}
