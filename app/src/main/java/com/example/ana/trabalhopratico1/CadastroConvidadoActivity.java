package com.example.ana.trabalhopratico1;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroConvidadoActivity extends AppCompatActivity {

    EditText nomeConvidadoET;
    EditText numeroConvidadoET;
    EditText emailConvidadoET;
    ArrayList<Convidado> novaLista;
    public static final int RESULT_OK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_convidado);

        nomeConvidadoET = (EditText) findViewById(R.id.nomeConvidado);
        numeroConvidadoET = (EditText) findViewById(R.id.numeroConvidado);
        emailConvidadoET = (EditText) findViewById(R.id.emailConvidado);

        MaskEditTextChangedListener maskTEL = new MaskEditTextChangedListener("(##)####-####", numeroConvidadoET);
        numeroConvidadoET.addTextChangedListener(maskTEL);

        Intent intent = getIntent();
        if (intent != null) {
            novaLista = (ArrayList<Convidado>) intent.getExtras().getSerializable("listaDeConvidado");
        }
    }

    public void cadastrarConvidado (View view) {
        if (validaCampo() == false ) {
            String nomeConvidado = nomeConvidadoET.getText().toString();
            String numeroConvidado = numeroConvidadoET.getText().toString();
            String emailConvidado = emailConvidadoET.getText().toString();

            Convidado novo = new Convidado(nomeConvidado,emailConvidado,numeroConvidado);
            novaLista.add(novo);

            Intent intent = new Intent(this, ListaConvidadosActivity.class);
            intent.putExtra("listaAtualizada", novaLista);
            setResult(RESULT_OK,intent);
            finish();
        } else {
            Toast.makeText(CadastroConvidadoActivity.this, "Favor preencher todos os campos!", Toast.LENGTH_LONG).show();
        }


    }

    public boolean validaCampo( ) {
        boolean retorno = false;
        if ( nomeConvidadoET.getText().toString().trim().equals("") ||
                numeroConvidadoET.getText().toString().trim().equals("") ||
                emailConvidadoET.getText().toString().trim().equals("")) {
            retorno = true;
        }
        return retorno;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                /*Intent intent = new Intent(this, ListaConvidadosActivity.class);
                intent.putExtra("listaDeConvidado", novaLista);
                startActivity(intent);*/
                Intent intent = new Intent(this, ListaConvidadosActivity.class);
                intent.putExtra("listaAtualizada", novaLista);
                setResult(RESULT_OK,intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cadastrarConvidadoAgenda ( View view ) {

        if (!validaCampo()) {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

            //mudando o nome
            intent.putExtra(ContactsContract.Intents.Insert.NAME, nomeConvidadoET.getText().toString());

            //mudando o telefone
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, numeroConvidadoET.getText().toString());
            intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);

            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailConvidadoET.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(CadastroConvidadoActivity.this, "Favor preencher todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                Toast.makeText(this, "OOiOi", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    } */
}
