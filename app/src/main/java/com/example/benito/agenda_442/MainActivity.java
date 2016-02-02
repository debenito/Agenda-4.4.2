package com.example.benito.agenda_442;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.StrictMode;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    ProgressBar p;
    TextView text;
    TextView textConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         p = (ProgressBar)findViewById(R.id.progressBar);
        String url = "http://eparlamento.jgpa.es/ws/WSListin.php";
        p.setIndeterminate(true);
        text= (TextView) findViewById(R.id.textBienvenida);
        textConnect= (TextView) findViewById(R.id.textConect);
        //SINcronizacion
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        JSONParser parser = new JSONParser();
        try {
            if(isAvailable()){
                if (parser.isConectado(url) == true) {


                    p.setIndeterminate(false);
                    //Crear un nuevo intent
                    Intent intent = new Intent(this, LoginActivity.class);
                    //Iniciar actividad
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            textConnect.setText("Su conexion a internet esta desconectada");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private Boolean isAvailable() {
        boolean conectado = false;
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1    www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            if(reachable){
                textConnect.setText("Conectado");
               conectado = true;
            }
            else{
               textConnect.setText("No Internet access");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return conectado;
    }
}
