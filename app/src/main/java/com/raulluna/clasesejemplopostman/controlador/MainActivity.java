package com.raulluna.clasesejemplopostman.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.raulluna.clasesejemplopostman.R;
import com.raulluna.clasesejemplopostman.modelos.Publicacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> datos=new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.ListViewDatosd);
        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, datos);
        listView.setAdapter(arrayAdapter);
        obtenerDatos();

        Button info1 = findViewById(R.id.btnCrear);
        info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), crear.class);
                startActivityForResult(intent, 0);
            }
        });

        Button infolis = findViewById(R.id.btnListar);
        infolis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(arrayAdapter);
                obtenerDatos();
            }
        });

        Button infoel = findViewById(R.id.btnEliminar);
        infoel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText dat=findViewById(R.id.edtxtBuscar);
                String busq=dat.getText().toString();
                eliminar(busq);
                arrayAdapter.clear();
                // Notificar al ListView de que los datos han cambiado
                arrayAdapter.notifyDataSetChanged();
            }
        });

        Button infobus = findViewById(R.id.btnBuscar);
        infobus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText dat=findViewById(R.id.edtxtBuscar);
                String busq=dat.getText().toString();
                buscar(busq);
                arrayAdapter.clear();
                // Notificar al ListView de que los datos han cambiado
                arrayAdapter.notifyDataSetChanged();

            }
        });
    }

    private void obtenerDatos(){
        String url="https://jsonplaceholder.typicode.com/todos";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //obtengo el json respuesta
                //MANEJAMOS EL JSON
                manejaJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //obtengo un error si es que se da
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void buscar(String id){
        String url = "https://jsonplaceholder.typicode.com/todos/"+id;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println(url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta exitosa del servidor
                        Toast.makeText(getApplicationContext(), "se ha buscado el usuario: \n"+id, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar el error de la respuesta del servidor
            }
        });
        requestQueue.add(request);
    }

    private void manejaJson(JSONArray jsonArray){
        for (int i=0; i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            Publicacion publicacion=new Publicacion();
            try {
                jsonObject=jsonArray.getJSONObject(i);
                publicacion.setUserId(jsonObject.getInt("userId"));
                publicacion.setId(jsonObject.getInt("id"));
                publicacion.setTitle(jsonObject.getString("title"));
                publicacion.setCompleted(jsonObject.getString("completed"));
                 datos.add("Usuario ID:\n                                              "+publicacion.getUserId()
                        +"\nID:\n                                              "+publicacion.getId()
                        +"\ntitulo:\n                            "+publicacion.getTitle()
                        +"\ncompleted:\n                                          "+publicacion.getCompleted());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void eliminar(String id){
        String url = "https://jsonplaceholder.typicode.com/todos/"+id;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println(url);

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta exitosa del servidor
                        Toast.makeText(getApplicationContext(), "se ha eliminado el usuario: \n "+id+"\ncorrectamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar el error de la respuesta del servidor
            }
        });
        requestQueue.add(request);
    }


}