package com.grv;

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
            validarDados();
        } catch (InterfaceException e) {
            mostrarAviso(e.getIdString());
            return;
        }
        mostrarAviso(R.string.cadastradoComSucesso);
        limparDadosTela();
    }

    private void mostrarAviso(int idString) {
        Toast.makeText(CadastroActivity.this, getString(idString), Toast.LENGTH_LONG).show();
    }

    private void validarDados() throws InterfaceException {
        if (isNullOrEmpty(txtNome.getText().toString()) && cbConheceNome.isChecked()) {
            throw new InterfaceException(R.string.nomeNaoInformado);
        }
        if (isNullOrEmpty(txtContato.getText().toString())) {
            throw new InterfaceException(R.string.contatoNaoInformado);
        }
        if (isNullOrEmpty(txtEndereco.getText().toString())) {
            throw new InterfaceException(R.string.enderecoNaoInformado);
        }
        if (rbTipoContato.getCheckedRadioButtonId() == -1) {
            throw new InterfaceException(R.string.tipoContatoNaoInformado);
        }
    }

    private boolean isNullOrEmpty(String v) {
        return v == null || v.isEmpty();
    }
}
