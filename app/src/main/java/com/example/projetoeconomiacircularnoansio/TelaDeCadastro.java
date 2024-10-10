package com.example.projetoeconomiacircularnoansio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projetoeconomiacircularnoansio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class TelaDeCadastro extends AppCompatActivity {
    private EditText NomeCadastro, EmailCadastro, SenhaCadastro;
    private Button bt_cadastrar;
    String[] mensagens = {"Preencha Todos os Campos","Cadastro Realizado com Sucesso"};
    public int AnisioCoin = 0;
    String UserId;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_de_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        IniciarComponentes();
    }
    public void ConcluirCadastro(View view) {
        String Nome = NomeCadastro.getText().toString();
        String Email = EmailCadastro.getText().toString();
        String Senha = SenhaCadastro.getText().toString();

        if (Nome.isEmpty() || Email.isEmpty() || Senha.isEmpty()){
            Snackbar BarraDeAviso = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT);
            BarraDeAviso.setBackgroundTint(Color.WHITE);
            BarraDeAviso.setTextColor(Color.BLACK);
            BarraDeAviso.show();
        }else{
            CadastrarUser(view);
            Logar();

        }

    }
    private void  CadastrarUser(View v){
        String Nome = NomeCadastro.getText().toString();
        String Email = EmailCadastro.getText().toString();
        String Senha = SenhaCadastro.getText().toString();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email,Senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    SalvarDadosUser();
                    Snackbar BarraDeAviso = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    BarraDeAviso.setBackgroundTint(Color.WHITE);
                    BarraDeAviso.setTextColor(Color.BLACK);
                    BarraDeAviso.show();
                }else{
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "Sua senha deve conater no minímo 6 caractéres";
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "E-mail já cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail invalido";

                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }
                    Snackbar BarraDeAviso = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    BarraDeAviso.setBackgroundTint(Color.WHITE);
                    BarraDeAviso.setTextColor(Color.BLACK);
                    BarraDeAviso.show();

                }
            }
        });

    }
    private void  SalvarDadosUser(){
        String Nome = NomeCadastro.getText().toString();
        String Email = EmailCadastro.getText().toString();
        String Senha = SenhaCadastro.getText().toString();
        int anisioCoin = AnisioCoin;


        FirebaseFirestore DataBase = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("Nome", Nome);
        usuarios.put("Email", Email);
        usuarios.put("Senha", Senha);
        usuarios.put("Saldo", anisioCoin);
        UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference Referencia = DataBase.collection("Usuários").document(UserId);
        Referencia.set(usuarios);

    }
    private void Logar(){
        Intent TelaDeLogin = new Intent(TelaDeCadastro.this,TelaDeLogin.class);
        startActivity(TelaDeLogin);
        finish();
    }

    private void IniciarComponentes(){
        NomeCadastro = findViewById(R.id.NomeCadastro);
        EmailCadastro = findViewById(R.id.EmailCadastro);
        SenhaCadastro = findViewById(R.id.SenhaCadastro);


    }
}
