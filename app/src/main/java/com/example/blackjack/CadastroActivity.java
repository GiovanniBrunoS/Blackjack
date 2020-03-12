package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextEmail;
    private Button buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
    }

    public void onConcluir(View view) throws NoSuchAlgorithmException {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean fieldsFilled = Boolean.TRUE;

        if(editTextUsername.getText().toString().isEmpty()) {
            editTextUsername.setError("Campo vazio!");
            editTextUsername.setBackgroundResource(R.drawable.edt_error);
            fieldsFilled = Boolean.FALSE;
        }
        else {
            editTextUsername.setBackgroundResource(R.drawable.edt_normal);
        }
        if(editTextPassword.getText().toString().isEmpty()) {
            editTextPassword.setError("Campo vazio!");
            editTextPassword.setBackgroundResource(R.drawable.edt_error);
            fieldsFilled = Boolean.FALSE;
        }
        else {
            editTextPassword.setBackgroundResource(R.drawable.edt_normal);
        }
        if(editTextName.getText().toString().isEmpty()) {
            editTextName.setError("Campo vazio!");
            editTextName.setBackgroundResource(R.drawable.edt_error);
            fieldsFilled = Boolean.FALSE;
        }
        else {
            editTextName.setBackgroundResource(R.drawable.edt_normal);
        }
        if(editTextEmail.getText().toString().isEmpty()) {
            editTextEmail.setError("Campo vazio!");
            editTextEmail.setBackgroundResource(R.drawable.edt_error);
            fieldsFilled = Boolean.FALSE;
        }
        else {
            editTextEmail.setBackgroundResource(R.drawable.edt_normal);
        }
        if(fieldsFilled){
            String password = editTextPassword.getText().toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            password = Base64.encodeToString(hash, Base64.DEFAULT);

            editor.putString("username", editTextUsername.getText().toString());
            editor.putString("password", password);
            editor.putString("name", editTextName.getText().toString());
            editor.putString("email", editTextEmail.getText().toString());
            editor.putString("vitorias", "0");
            editor.putString("derrotas", "0");
            editor.commit();

            finish();
        }

    }
}
