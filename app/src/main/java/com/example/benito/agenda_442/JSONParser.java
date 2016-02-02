package com.example.benito.agenda_442;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by joseantonio on 29/01/2016.
 */
public class JSONParser {

    static boolean conectado = false;
    static InputStream is = null;
static JSONObject jObj = null;
static String json = "";

        // constructor
        public JSONParser() {

        }

        // function get json from url
        // by making HTTP POST or GET mehtod
        public List<String>  makeHttpRequest(String url, String method,
                                          List params) {
              List<String> array = new ArrayList<String>();
            // Making HTTP request
            try {

                // check for request method
                if(method == "POST"){
                    // request method is POST
                    // defaultHttpClient
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(params));

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();


                }else if(method == "GET"){
                    // request method is GET
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                    HttpGet httpGet = new HttpGet(url);

                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();

                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
              //  StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    array.add(line.split(";") + "\n");
                }
                is.close();

            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            // return JSON String
            return array;

        }

    public static boolean isConectado(String stringUrl) throws Exception {
        try {

            URL url;
            String html;
            url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         //   conn = (HttpURLConnection) url.openConnection(); // Abriendo conexión
            conn.setReadTimeout(10000); // Tiempo de espera en lectura
            conn.setConnectTimeout(15000); // Tiempo de conexión
            //  conn.setRequestMethod("GET"); // Método de conexión
            conn.setDoInput(true); // Indica que la conexión es de entrada (Lectura).
            conn.addRequestProperty("Referer", "www.gmail.com"); // Añade una propiedad
            conn.connect();

            html = readFromBuffer(new BufferedReader(new InputStreamReader(
                    conn.getInputStream())));

            if (conn.getResponseCode() ==(HttpURLConnection.HTTP_OK))

                setConectado(true);
                else
                setConectado(false);

            }catch (IOException e) {
            try {
                throw new Exception("Error de Conexion");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return conectado;
    }

    public static void setConectado(boolean conectado) {
        JSONParser.conectado = conectado;
    }

    private static String readFromBuffer(BufferedReader br){
        StringBuilder text = new StringBuilder();
        try{
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // tratar excepción!!!
        }
        return text.toString();
    }
}
