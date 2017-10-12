package com.example.ana.trabalhopratico1;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class EventoCadastradoActivity extends AppCompatActivity {

    TextView nomeEventoTV;
    TextView dataEventoTV;
    TextView horaEventoTV;
    TextView nomeOrganizadorTV;
    TextView numeroOrganizadorTV;
    TextView emailOrganizadorTV;
    public static final int RESULT_OK = 10;

    ArrayList<Convidado> listaDeConvidado;

    String nomeEvento,
            nomeOrganizador,
            numeroOrganizador,
            emailOrganizador,
            dataEventoIni,
            dataEventoFim,
            horaIniEvento,
            horaEndEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_cadastrado);

        listaDeConvidado = new ArrayList<Convidado>();

        nomeEventoTV = (TextView) findViewById(R.id.textViewNomeEvento);
        dataEventoTV = (TextView) findViewById(R.id.textViewDataEvento);
        nomeOrganizadorTV = (TextView) findViewById(R.id.nomeOrganizadorEvento);
        numeroOrganizadorTV = (TextView) findViewById(R.id.textViewNumero);
        emailOrganizadorTV = (TextView) findViewById(R.id.textViewEmail);
        horaEventoTV = (TextView) findViewById(R.id.textViewHoraEvento);

        Intent intent = getIntent();
        if ( intent != null ) {
            Bundle params = intent.getExtras();
            if (params != null ) {
                nomeEvento = params.getString("nomeEvento");
                nomeOrganizador = params.getString("nomeOrganizador");
                numeroOrganizador = params.getString("numeroOrganizador");
                emailOrganizador = params.getString("emailOrganizador");
                dataEventoIni = params.getString("dataEventoIni");
                dataEventoFim = params.getString("dataEventoFim");
                horaIniEvento = params.getString("horaIniEvento");
                horaEndEvento = params.getString("horaEndEvento");

                nomeEventoTV.setText(nomeEvento);
                String [] dataIni = dataEventoIni.split("/");
                String [] dataFim = dataEventoFim.split("/");
                String mesIni = retornaMes(Integer.parseInt(dataIni[1]));
                String mesFim = retornaMes(Integer.parseInt(dataFim[1]));

                horaEventoTV.setText(dataIni[0] + " de " + mesIni + " às "+ horaIniEvento+ " a " + dataFim[0] + " de " + mesFim + " às " + horaEndEvento);
                nomeOrganizadorTV.setText(nomeOrganizador);
                numeroOrganizadorTV.setText(numeroOrganizador);
                emailOrganizadorTV.setText(emailOrganizador);
            }
        }


    }


    public String retornaMes ( int mes ) {
        String retorno = "";
        if ( mes == 1 ) {
            retorno = "janeiro";
        } else if ( mes == 2 ) {
            retorno = "fevereiro";
        } else if ( mes == 3 ) {
            retorno = "março";
        } else if ( mes == 4 ) {
            retorno = "abril";
        } else if ( mes == 5 ) {
            retorno = "maio";
        } else if ( mes == 6 ) {
            retorno = "junho";
        } else if ( mes == 7 ) {
            retorno = "julho";
        } else if ( mes == 8 ) {
            retorno = "agosto";
        } else if ( mes == 9 ){
            retorno = "setembro";
        } else if ( mes == 10 ) {
            retorno = "outubro";
        } else if ( mes == 11 ) {
            retorno = "novembro";
        } else  {
            retorno = "dezembro";
        }
        return retorno;
    }

    public void cadastrarNaAgenda ( View view ) {

        String [] infosDataIni = dataEventoIni.split("/");
        int anoIni = Integer.parseInt(infosDataIni[0]);
        int mesIni = Integer.parseInt(infosDataIni[1]);
        int diaIni = Integer.parseInt(infosDataIni[2]);

        String [] infosHoraIni = horaIniEvento.split(":");
        int horaIni = Integer.parseInt(infosHoraIni[0]);
        int minutoIni = Integer.parseInt(infosHoraIni[1]);

        String [] infosDataFim = dataEventoFim.split("/");
        int anoFim = Integer.parseInt(infosDataFim[0]);
        int mesFim = Integer.parseInt(infosDataFim[1]);
        int diaFim = Integer.parseInt(infosDataFim[2]);

        String [] infosHoraFim = horaEndEvento.split(":");
        int horaFim = Integer.parseInt(infosHoraFim[0]);
        int minutoFim = Integer.parseInt(infosHoraFim[1]);



        Calendar calendarIni = Calendar.getInstance();
        calendarIni.set(anoIni,mesIni,diaIni,horaIni,minutoIni);

        Calendar calendarFim = Calendar.getInstance();
        calendarFim.set(anoFim,mesFim,diaFim,horaFim,minutoFim);

        Intent intent2 = new Intent(Intent.ACTION_INSERT);
        //intent2.setType("vnd.android.cursor.item/event");
        intent2.setData(CalendarContract.Events.CONTENT_URI);
        intent2.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,calendarIni.getTimeInMillis());
        intent2.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendarFim.getTimeInMillis());
        intent2.putExtra(CalendarContract.Events.TITLE, nomeEvento);

        startActivity(intent2);
    }

    public void verConvidados ( View view ) {
        Intent intent = new Intent(this, ListaConvidadosActivity.class);
        //Convidado novo = new Convidado(nomeOrganizador, emailOrganizador, numeroOrganizador);
        //listaDeConvidado.add(novo);
        intent.putExtra("listaDeConvidado", listaDeConvidado);
        intent.putExtra("nomeEvento", nomeEvento);
        intent.putExtra("dataEventoIni", dataEventoIni);
        intent.putExtra("dataEventoFim", dataEventoFim);
        intent.putExtra("horaIniEvento", horaIniEvento);
        intent.putExtra("horaEndEvento", horaEndEvento);
        startActivityForResult(intent,RESULT_OK);
        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        this.listaDeConvidado = (ArrayList<Convidado>) data.getExtras().getSerializable("listaAtualizada");
    }

    public void enviarLinkedin ( View view ) {
        String mensagem= "";
        Intent intent = ShareCompat.IntentBuilder.from(EventoCadastradoActivity.this).setType("text/plain")
                .setText(mensagem).getIntent();
        intent.setPackage("com.linkedin.android");
        intent.setAction(Intent.ACTION_SEND);
        if ( intent.resolveActivity(getPackageManager()) != null ) {
            startActivity(intent);
        }

    }
}
