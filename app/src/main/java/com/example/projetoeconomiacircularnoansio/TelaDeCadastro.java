package com.example.projetoeconomiacircularnoansio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.UUID;


public class TelaDeCadastro extends AppCompatActivity {

    //Variaveis usadas nessa tela
    private EditText NomeCadastro, EmailCadastro, SenhaCadastro, CPF,TelefoneCadastro;
    String[] mensagens = {"Preencha Todos os Campos","Cadastro Realizado com Sucesso"};
    public int AnisioCoin = 0;
    String UserId;
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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
        String cpf = CPF.getText().toString();


        if (Nome.isEmpty() || Email.isEmpty() || Senha.isEmpty() || cpf.isEmpty()){
            Toast toast = Toast.makeText(TelaDeCadastro.this, mensagens[0], Toast.LENGTH_SHORT);
            toast.show();

        }else{
            CadastrarUser(view);

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
                    Toast toast = Toast.makeText(TelaDeCadastro.this, mensagens[1], Toast.LENGTH_SHORT);
                    toast.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        //Classe para mudar de tela após o usuário se cadastrar
                        public void run() {
                            Intent TelaLogin = new Intent(TelaDeCadastro.this, TelaDeLogin.class);
                            startActivity(TelaLogin);
                            finish();

                        }
                        //tempo  de carregamento para esse redirecionamento
                    },SPLASH_TIME_OUT);
                    //caso o usuário tente cadastrar algum dado inválido
                }else{
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "Sua senha deve conter no minímo 6 caractéres";
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "E-mail já cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail invalido";

                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }
                    //barra de aviso
                    Toast toast = Toast.makeText(TelaDeCadastro.this, erro, Toast.LENGTH_SHORT);
                    toast.show();


                }
            }
        });

    }
    public void  SalvarDadosUser(){
        String Nome = NomeCadastro.getText().toString();
        String Email = EmailCadastro.getText().toString();
        String Senha = SenhaCadastro.getText().toString();
        String CPFcadastrar = CPF.getText().toString();
        String Telefone = TelefoneCadastro.getText().toString();
        int anisioCoin = AnisioCoin;


        FirebaseFirestore DataBase = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("Nome", Nome);
        usuarios.put("Email", Email);
        usuarios.put("Senha", Senha);
        usuarios.put("CPF", CPFcadastrar);
        usuarios.put("Telefone", Telefone);
        usuarios.put("Saldo", anisioCoin);
        UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference Referencia = DataBase.collection("Usuários").document(UserId);
        Referencia.set(usuarios);

    }

    private void IniciarComponentes() {
        NomeCadastro = findViewById(R.id.NomeCadastro);
        EmailCadastro = findViewById(R.id.EmailCadastro);
        SenhaCadastro = findViewById(R.id.SenhaCadastro);
        CPF = findViewById(R.id.CPF);
        TelefoneCadastro = findViewById(R.id.editTextPhone);
    }
}
