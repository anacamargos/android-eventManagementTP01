package com.example.ana.trabalhopratico1;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaConvidadosActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ArrayList<Convidado> listaDeConvidados;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    public static final int RESULT_OK = 0;
    public static final int RESULT_OK2 = 10;

    String nomeEvento, dataEventoIni, dataEventoFim, horaIniEvento, horaEndEvento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_convidados);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        Intent intent = getIntent();
        if ( intent != null ) {
            listaDeConvidados = (ArrayList<Convidado>) intent.getExtras().getSerializable("listaDeConvidado");
            nomeEvento = intent.getExtras().getString("nomeEvento");
            dataEventoIni = intent.getExtras().getString("dataEventoIni");
            dataEventoFim = intent.getExtras().getString("dataEventoFim");
            horaIniEvento = intent.getExtras().getString("horaIniEvento");
            horaEndEvento = intent.getExtras().getString("horaEndEvento");

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerViewAdapter = new RecyclerViewAdapter(this, listaDeConvidados);
            recyclerView.setAdapter(recyclerViewAdapter);
        } else {
            listaDeConvidados = new ArrayList<Convidado>();
        }
    }

    public void onClick (View view) {
        Intent intent = new Intent(ListaConvidadosActivity.this, CadastroConvidadoActivity.class);
        intent.putExtra("listaDeConvidado", listaDeConvidados);
        startActivityForResult(intent,RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        this.listaDeConvidados = (ArrayList<Convidado>) data.getExtras().getSerializable("listaAtualizada");
        recyclerViewAdapter = new RecyclerViewAdapter(this, listaDeConvidados);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                Intent intent = new Intent(this, EventoCadastradoActivity.class);
                intent.putExtra("listaAtualizada", listaDeConvidados);
                setResult(RESULT_OK2,intent);
                finish();
                return true;
            case R.id.whatsapp:
                String mensagem = "Não se esqueça do meu evento no dia " + dataEventoIni + ", que começa às " + horaIniEvento + ", e vai até " + dataEventoFim + " às " + horaEndEvento + ".";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mensagem);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(sendIntent, ""));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_button, menu);
        return true;
    }

    /*public void enviarWhatsApp ( View view ) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(sendIntent, ""));
    }*/


}
