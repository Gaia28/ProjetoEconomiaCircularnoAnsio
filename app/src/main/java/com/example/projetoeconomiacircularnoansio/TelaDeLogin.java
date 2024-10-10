package com.example.projetoeconomiacircularnoansio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class TelaDeLogin extends AppCompatActivity {
    private EditText Email, Senha;
    String[] mensagens = {"Preencha Todos os Campos", "Login efetuado com Sucesso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_de_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        IniciarComponents();
    }

    public void Entrar(View view) {
        String email = Email.getText().toString();
        String senha = Senha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()){
            Snackbar BarraDeAviso = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT);
            BarraDeAviso.setBackgroundTint(Color.WHITE);
            BarraDeAviso.setTextColor(Color.BLACK);
            BarraDeAviso.show();
        }else{
            AutenticarUser(view);

        }
    }

    private void AutenticarUser(View view){
        String email = Email.getText().toString();
        String senha = Senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaPrincipal();
                        }
                    }, 1500);


                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        erro = "E-mail ou senha incorretos";
                        Snackbar BarraDeAviso = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                        BarraDeAviso.setBackgroundTint(Color.WHITE);
                        BarraDeAviso.setTextColor(Color.BLACK);
                        BarraDeAviso.show();
                    }

                }
            }
        });

    }
    private void TelaPrincipal(){
        Intent MudarTela = new Intent(TelaDeLogin.this, TelaPrincipal.class);
        startActivity(MudarTela);
        finish();

    }

    public void TelaDeCadastro(View view) {
        Intent NovaTela = new Intent(TelaDeLogin.this, TelaDeCadastro.class);
        startActivity(NovaTela);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser UsuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if (UsuarioAtual != null){

            TelaPrincipal();
        }

    }
    private void IniciarComponents(){
        Email = findViewById(R.id.email);
        Senha = findViewById(R.id.senha);

    }

}
