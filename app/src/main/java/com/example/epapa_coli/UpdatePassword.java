package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Confirm.ConfirmUpdatePassword;

import java.util.HashMap;
import java.util.Map;

public class UpdatePassword extends AppCompatActivity {

    EditText edtPassword, edtConfirmPassword;
    Button btnUpdatePassword;
    String user;
    ImageView imgRegress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        imgRegress = findViewById(R.id.imgRegress);
        imgRegress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        edtPassword = findViewById(R.id.newPassword);
        edtConfirmPassword = findViewById(R.id.confirmPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = "https://devtesis.com/tesis-epapacoli/updatePassword.php";
                ActualizarDatosPasword(URL);
            }
        });

    }

    private boolean validarPassword() {
        String passwordP = edtPassword.getText().toString();
        String password2 = edtConfirmPassword.getText().toString();
        if (passwordP.equals(password2)) {
            return true;
        } else {
            return false;
        }
    }

    public void ActualizarDatosPasword(String URL){
        if (edtPassword.getText().toString().equals("") || validarPassword() == false) {
            edtPassword.setError("Las contrase??as no coinciden");
        } else if (edtPassword.getText().toString().length() < 8) {
            edtPassword.setError("La contrase??a requiere m??nimo de 8 caracteres");
        } else if (edtConfirmPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "el campo confirmar contrase??a est?? vac??a est?? vac??o", Toast.LENGTH_SHORT).show();
        } else{
            final RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "La contrase??a se actualiz?? con ??xito", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ConfirmUpdatePassword.class));
                    finish();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("password", edtPassword.getText().toString());
                    params.put("usuario", user);

                    return params;
                }
            };
            queue.add(stringRequest);
            queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    queue.getCache().clear();
                }
            });
        }
    }

}