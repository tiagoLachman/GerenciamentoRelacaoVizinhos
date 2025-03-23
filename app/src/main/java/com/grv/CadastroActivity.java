package com.grv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {
    public static final String RESULT_VIZINHO = "vizinho";
    private CheckBox cbConheceNome;
    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtContato;
    private EditText txtObservacao;
    private RadioGroup rbTipoContato;
    private Spinner spNivelConfianca;
    private Button btnSalvar;
    private Button btnLimpar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        carregarComponentes();
        carregarEventos();

        atualizarDadosTela();
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

    private void atualizarDadosTela() {
        txtNome.setEnabled(cbConheceNome.isChecked());
        ArrayAdapter<NivelConfianca> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                NivelConfianca.values()
        );
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spNivelConfianca.setAdapter(adapter);
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
        return new Vizinho(
                nome,
                endereco,
                contato,
                observacao,
                nc
        );
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
