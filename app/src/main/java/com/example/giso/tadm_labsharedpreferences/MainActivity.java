package com.example.giso.tadm_labsharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String nombreCliente;
    private String celCliente;
    private String dirEvento;
    private String fechaEvento;
    private String horaIni;
    private String horaFin;
    private String numPlatillos;
    private String numPostres;
    private String manteleria;
    private String meseros;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText editCli,editCelCli,editDir,editFec, editHoraI,editHoraF,editNPlat,editNPos;
    TextView txtMeseros;
    CheckBox basica,lujo;
    Button saveMe,getMe;
    SeekBar seekMeseros;
    int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        editCli = findViewById(R.id.txtCliente);
        editCelCli= findViewById(R.id.txtCelCliente);
        editDir= findViewById(R.id.txtDirEvento);
        editFec = findViewById(R.id.txtFechaEvento);
        editHoraI= findViewById(R.id.txtHoraInicio);
        editHoraF= findViewById(R.id.txtHoraFin);
        editNPlat= findViewById(R.id.txtNoPlatillos);
        editNPos= findViewById(R.id.txtNoPostres);
        txtMeseros=findViewById(R.id.txtMeseros);

        basica = findViewById(R.id.basica);
        lujo = findViewById(R.id.lujo);
        seekMeseros = findViewById(R.id.seekbar);
        seekMeseros.setMax(10);

        saveMe = findViewById(R.id.saveMe);
        getMe = findViewById(R.id.getMe);

        seekMeseros.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                num = progress;
                txtMeseros.setText("Meseros: "+ seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //onClickListeners
        basica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });
        lujo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });
        saveMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    savePreferences();
                }catch (Exception e){
                    Log.d("","Error al guardar");
                }
            }
        });
        getMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadPreferences();
                }catch (Exception e){
                    Log.d("","Error al cargar");
                }
            }
        });
    }

    //checkBox
    public void onCheckboxClicked(View view) {

        StringBuilder stringBuilder = new StringBuilder();

        if (basica.isChecked()) {
            stringBuilder.append(", " + basica.getText());
        }

        if (lujo.isChecked()) {
            stringBuilder.append(", " + lujo.getText());
        }

        if (stringBuilder.length() > 0) {
            manteleria = stringBuilder.deleteCharAt(stringBuilder.indexOf(",")).toString();
        } else {
            manteleria = "";
        }
    }

    public void savePreferences(){
        editor = getPreferences(MODE_PRIVATE).edit();

        nombreCliente = editCli.getText().toString();
        celCliente= editCelCli.getText().toString();
        dirEvento= editDir.getText().toString();
        fechaEvento= editFec.getText().toString();
        horaIni= editHoraI.getText().toString();
        horaFin= editHoraF.getText().toString();
        numPlatillos= editNPlat.getText().toString();
        numPostres= editNPos.getText().toString();

        editor.putString("cliente", nombreCliente);
        editor.putString("celular", celCliente);
        editor.putString("direccion", dirEvento);
        editor.putString("fecha", fechaEvento);
        editor.putString("horaInicio", horaIni);
        editor.putString("horaFinal", horaFin);
        editor.putString("numeroPlatillos", numPlatillos);
        editor.putString("numeroPostres", numPostres);
        editor.putInt("meseros", num);
        //check
        editor.putString("manteleria", manteleria);
        //manteleria;
        editor.apply();
        //editor.apply();
        Toast.makeText(this, "Se Han guardado los datos", Toast.LENGTH_SHORT).show();
    }

    public void loadPreferences(){

        nombreCliente = sharedPreferences.getString("cliente","");
        celCliente = sharedPreferences.getString("celular","");
        dirEvento = sharedPreferences.getString("direccion","");
        fechaEvento = sharedPreferences.getString("fecha","");
        horaIni = sharedPreferences.getString("horaInicio","");
        horaFin = sharedPreferences.getString("horaFinal","");
        numPlatillos = sharedPreferences.getString("numeroPlatillos","");
        numPostres = sharedPreferences.getString("numeroPostres","");
        manteleria = sharedPreferences.getString("manteleria","");

        editCli.setText(nombreCliente);
        editCelCli.setText(celCliente);
        editDir.setText(dirEvento);
        editFec.setText(fechaEvento);
        editHoraI.setText(horaIni);
        editHoraF.setText(horaFin);
        editNPlat.setText(numPlatillos);
        editNPos.setText(numPostres);
        seekMeseros.setProgress(sharedPreferences.getInt("meseros",0));

        basica.setChecked(false);
        lujo.setChecked(false);
        if (manteleria.contains("Básica")) {
            basica.setChecked(true);
        }
        if (manteleria.contains("Lujo")) {
            lujo.setChecked(true);
        }

        setPreferences();
    }

    public void setPreferences(){
        editCli.setText(nombreCliente);
        editCelCli.setText(celCliente);
        editDir.setText(dirEvento);
        editFec.setText(fechaEvento);
        editHoraI.setText(horaIni);
        editHoraF.setText(horaFin);
        editNPlat.setText(numPlatillos);
        editNPos.setText(numPostres);

        basica.setChecked(false);
        lujo.setChecked(false);
        if (manteleria.contains("Básica")) {
            basica.setChecked(true);
        }
        if (manteleria.contains("Lujo")) {
            lujo.setChecked(true);
        }
    }
}
