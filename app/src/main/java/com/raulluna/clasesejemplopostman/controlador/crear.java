package com.raulluna.clasesejemplopostman.controlador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.raulluna.clasesejemplopostman.R;

import org.json.JSONException;
import org.json.JSONObject;

public class crear extends AppCompatActivity {

    EditText Tuserid, Tid, Ttitle, Tbody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear);

        Tuserid = findViewById(R.id.edtxtuserid);
        Tid = findViewById(R.id.edtxtid);
        Ttitle = findViewById(R.id.edtxttitulo);
        Tbody = findViewById(R.id.edtxtbody);

        Button aceptar=findViewById(R.id.brnAceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer useridlay = Integer.parseInt(Tuserid.getText().toString()) ;
                Integer idlay = Integer.parseInt(Tid.getText().toString());
                String titlelay = Ttitle.getText().toString();
                String completedley = Tbody.getText().toString();
                try {
                    enviarSolicitudRegistroUsuario(useridlay, idlay, titlelay, completedley);
                    /*Toast.makeText(crear.this, "Registro exitoso",Toast.LENGTH_SHORT).show();*/
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        ImageButton info3 = findViewById(R.id.btnRegreso1);
        info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void enviarSolicitudRegistroUsuario(Integer userid, Integer id, String title, String completed) throws JSONException {

        String url = "https://jsonplaceholder.typicode.com/posts";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userid);
        jsonObject.put("id", id);
        jsonObject.put("title", title);
        jsonObject.put("completed", completed);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                response -> {
                    // Aquí manejas la respuesta exitosa de la API REST
                    Toast.makeText(this, "Se ha registrado exitosamente\n"+userid+" | "+title+" | "+completed+" | "+id, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Aquí manejas la respuesta de error de la API REST
                    Toast.makeText(this, "Error al resgistro, por favor vea los datos", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
