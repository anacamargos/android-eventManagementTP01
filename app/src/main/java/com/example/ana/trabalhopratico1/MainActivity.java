package com.example.ana.trabalhopratico1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.Calendar;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class MainActivity extends AppCompatActivity {

    EditText nomeEvento;
    EditText nomeOrganizador;
    EditText numeroOrganizador;
    EditText emailOrganizador;
    EditText nDataEndEditText;
    EditText nTimeEndEditText;
    EditText nTimeEditText;
    EditText nDataEditText;
    Calendar nCurrentDate = Calendar.getInstance();
    /*ImageGenerator nImageGenerator;*/

    int  year, month, day, hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeEvento = (EditText) findViewById(R.id.nomeEvento);
        nomeOrganizador = (EditText) findViewById(R.id.nomeOrganizador);
        numeroOrganizador = (EditText) findViewById(R.id.numeroOrganizador);
        emailOrganizador = (EditText) findViewById(R.id.emailOrganizador);
        nDataEndEditText = (EditText) findViewById(R.id.txtDataFim);
        nDataEditText = (EditText) findViewById(R.id.txtDataInicio);
        nTimeEditText = (EditText) findViewById(R.id.txtHoraInicio);
        nTimeEndEditText = (EditText) findViewById(R.id.txtHoraFim);

        MaskEditTextChangedListener maskTEL = new MaskEditTextChangedListener("(##)####-####", numeroOrganizador);
        numeroOrganizador.addTextChangedListener(maskTEL);


        /*nImageGenerator = new ImageGenerator(this);

        nImageGenerator.setIconSize(50,50);
        nImageGenerator.setDateSize(30);
        nImageGenerator.setMonthSize(10);

        nImageGenerator.setDatePosition(42);
        nImageGenerator.setMonthPosition(14);

        nImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        nImageGenerator.setMonthColor(Color.WHITE);

        nImageGenerator.setStorageToSDCard(true);*/

        nDataEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDate(1);
            }
        });

        nDataEndEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDate(2);
            }
        });

        nTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTime(1);
            }
        });

        nTimeEndEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTime(2);
            }
        });


    }

    private void updateDate ( int aux ) {
        year = nCurrentDate.get(Calendar.YEAR);
        month = nCurrentDate.get(Calendar.MONTH);
        day = nCurrentDate.get(Calendar.DAY_OF_MONTH);
        if ( aux == 1 ) {
            new DatePickerDialog(this, g, year, month, day).show();
        } else {
            new DatePickerDialog(this, d, year, month, day).show();
        }
    }


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
            nDataEndEditText.setText(selectedDay+"/"+selectedMonth+"/"+selectedYear);
            nCurrentDate.set(selectedYear,selectedMonth,selectedDay);
        }
    };
    DatePickerDialog.OnDateSetListener g = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
            nDataEditText.setText(selectedDay+"/"+selectedMonth+"/"+selectedYear);
            nCurrentDate.set(selectedYear,selectedMonth,selectedDay);
        }
    };

    private void updateTime ( int horario ) {

        hour = nCurrentDate.get(Calendar.HOUR_OF_DAY);
        minute = nCurrentDate.get(Calendar.MINUTE);

        /*if (hour < 10 ) {
            String aux = "0" + Integer.toString(hour);
            hour = Integer.parseInt(aux);
            if (minute < 10) {
                aux = "0" + Integer.toString(minute);
                minute = Integer.parseInt(aux);
            }
        }*/
        if ( horario == 1 ) {
            new TimePickerDialog(this, t, hour, minute, true).show();
        } else {
            new TimePickerDialog(this, s, hour, minute, true).show();
        }


    }
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {

            if (hourOfDay < 10 ) {
                String aux = "0" + Integer.toString(hourOfDay);
                hourOfDay = Integer.parseInt(aux);
            }
            if (minuteOfDay < 10) {
                String aux = "0" + Integer.toString(minuteOfDay);
                minuteOfDay = Integer.parseInt(aux);
            }
            nTimeEditText.setText(hourOfDay+":"+minuteOfDay);

        }
    };

    TimePickerDialog.OnTimeSetListener s = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {
            if (hourOfDay < 10 ) {
                String aux = "0" + Integer.toString(hourOfDay);
                hourOfDay = Integer.parseInt(aux);
            }
            if (minuteOfDay < 10) {
                String aux = "0" + Integer.toString(minuteOfDay);
                minuteOfDay = Integer.parseInt(aux);
            }
            nTimeEndEditText.setText(hourOfDay+":"+minuteOfDay);

        }
    };

    public void cadastrarEvento ( View view ) {

        if (validaCampo() == false) {

            Bundle params = new Bundle();
            params.putString("nomeEvento", nomeEvento.getText().toString());
            params.putString("nomeOrganizador", nomeOrganizador.getText().toString());
            params.putString("numeroOrganizador", numeroOrganizador.getText().toString());
            params.putString("emailOrganizador", emailOrganizador.getText().toString());
            params.putString("dataEventoIni", nDataEditText.getText().toString());
            params.putString("dataEventoFim", nDataEndEditText.getText().toString());
            params.putString("horaIniEvento", nTimeEditText.getText().toString());
            params.putString("horaEndEvento", nTimeEndEditText.getText().toString());


            Intent intent = new Intent(this, EventoCadastradoActivity.class);
            intent.putExtras(params);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Favor preencher todos os campos!", Toast.LENGTH_LONG).show();
        }

    }

    public boolean validaCampo ( ) {
        boolean retorno = false;
        if ( nomeEvento.getText().toString().trim().equals("") ||
                nomeOrganizador.getText().toString().trim().equals("") ||
                numeroOrganizador.getText().toString().trim().equals("") ||
                emailOrganizador.getText().toString().trim().equals("") ||
                nDataEditText.getText().toString().trim().equals("") ||
                nDataEndEditText.getText().toString().trim().equals("") ||
                nTimeEditText.getText().toString().trim().equals("") ||
                nTimeEndEditText.getText().toString().trim().equals("")) {
            retorno = true;
        }
        return retorno;
    }
}
