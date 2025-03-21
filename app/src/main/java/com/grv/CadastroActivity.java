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
            mostrarAviso("Tela limpada");
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
        rbTipoContato.clearCheck();
        spNivelConfianca.setSelection(0);
        cbConheceNome.setChecked(false);
        atualizarDadosTela();
    }

    private void salvarDados() {
        try {
            validarDados();
        } catch (InterfaceException e) {
            mostrarAviso(e.getMessage());
            return;
        }
        mostrarAviso("Cadastrado com sucesso");
        limparDadosTela();
    }

    private void mostrarAviso(String text) {
        Toast.makeText(CadastroActivity.this, text, Toast.LENGTH_LONG).show();
    }

    private void validarDados() throws InterfaceException {
        if (isNullOrEmpty(txtNome.getText().toString()) && cbConheceNome.isChecked()) {
            throw new InterfaceException("Nome não informado");
        }
        if (isNullOrEmpty(txtContato.getText().toString())) {
            throw new InterfaceException("Contato não informado");
        }
        if (isNullOrEmpty(txtEndereco.getText().toString())) {
            throw new InterfaceException("Endereço não informado");
        }
        if (rbTipoContato.getCheckedRadioButtonId() == -1) {
            throw new InterfaceException("Tipo contato não informado");
        }
    }

    private boolean isNullOrEmpty(String v) {
        return v == null || v.isEmpty();
    }
}
