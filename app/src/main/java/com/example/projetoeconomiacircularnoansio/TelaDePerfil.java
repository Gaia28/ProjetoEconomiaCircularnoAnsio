package com.example.projetoeconomiacircularnoansio;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TelaDePerfil extends AppCompatActivity {
    private TextView textNome, TextTelefone, TextEmail, TextSaldo;
    EditText textvalor, textIduser;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    String Userid;
    String UserRecebedor;
    ImageView IconeMinimizar;
    ImageView MenuIcone;
    View ViewMenu;
    Button Abauser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_de_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponents();
    }
/**---------------------------BOTÔES---------------------------*/
    public void AbaInformaçoes(View view) {
        Intent AbrirTelaInfo = new Intent(TelaDePerfil.this, TelaDeInformacao.class);
        startActivity(AbrirTelaInfo);
        finish();
        }


    public void AbaHome(View view) {
        Intent AbrirTelaInicial = new Intent(TelaDePerfil.this, TelaPrincipal.class);
        startActivity(AbrirTelaInicial);
        finish();
    }

    public void AbaDoaçao(View view) {
        Intent AbrirTelaInfo = new Intent(TelaDePerfil.this, TelaDeDoacao.class);
        startActivity(AbrirTelaInfo);
        finish();

    }

    public void deslogar(View view) {

        FirebaseAuth.getInstance().signOut();
        Intent TelaDelogin = new Intent(TelaDePerfil.this, TelaDeLogin.class);
        startActivity(TelaDelogin);
        finish();
    }


    public void MostrarOpçoes(View view) {
        ViewMenu.setVisibility(View.VISIBLE);
        MenuIcone.setVisibility(View.INVISIBLE);
        IconeMinimizar.setVisibility(View.VISIBLE);
        Abauser.setVisibility(View.VISIBLE);
    }
    public void FecharOpçoes(View view) {
        IconeMinimizar.setVisibility(View.INVISIBLE);
        MenuIcone.setVisibility(View.VISIBLE);
        ViewMenu.setVisibility(View.INVISIBLE);
        Abauser.setVisibility(View.INVISIBLE);
    }

    public void AbaListUser(View view) {
        Intent AbrirListaUsr = new Intent(TelaDePerfil.this, ListaUsuers.class);
        startActivity(AbrirListaUsr);
        finish();
    }
    /**-----------------------FIM-------------------------*/



    @Override
    protected void onStart() {
        super.onStart();

        Userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference referencia = database.collection("Usuários").document(Userid);
        referencia.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot Documento, @Nullable FirebaseFirestoreException error) {
                if (Documento != null){
                    textNome.setText(Documento.getString("Nome"));
                    TextEmail.setText(Documento.getString("Email"));
                    TextTelefone.setText(Documento.getString("Telefone"));
                    TextSaldo.setText(Documento.getData().get("Saldo").toString());
                }
            }
        });

    }
    //BOTÃO PARA REALIZAR A DOAÇÃO

    public void ConcluirTransferencia(View view) {

        //VERIFICAÇÃO CASO OS CAMPOS DE TEXTO ESTEJAM VAZIOS - MOSTRAR BARRA DE AVISO

            String getSaldo = textvalor.getText().toString();
            String getId = textIduser.getText().toString();
            if (getSaldo.isEmpty() || getId.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();

                //CASO ESTEJAM PREENCHIDOS - INICIAR A TRANSAÇÃO
            }else{

                //VARIAVEL NUMÉRICA - RECEBE O VALOR DO CAMPO DE TEXTO ("textvalor") STRING -> DOUBLE
                double ValorDoacao = Double.parseDouble(textvalor.getText().toString());

                //VARIAVEL DO BANCO DE DADOS - RECEBE AS COLEÇÕES E DOCUMENTOS QUE SERÃO TRATADOS
                final DocumentReference referencia = database.collection("Usuários").document(Userid);


                //INICIAR A FUNÇÃO DE TRANSAÇÃO DENTRO DO BANCO DE DADOS -> VARIAVEL "database"
            database.runTransaction(new Transaction.Function<Void>() {
                @Nullable
                @Override
                public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                    DocumentSnapshot snapshot = transaction.get(referencia);
                    double NovoSaldo = snapshot.getDouble("Saldo") - ValorDoacao;

                    //VERIFICAÇÃO DE SALDO DO USUÁRIO - MOSTRA UMA BARRA DE AVISO

                    if (ValorDoacao > snapshot.getDouble("Saldo")) {
                        Snackbar snackbar = Snackbar.make(view, "Saldo insuficiente", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();

                        // CASO TENHA SALDO SUFICIENTE - UPAR A TRANSAÇÃO PARA O BANCO DE DADOS - MOSTRAR  BARRA  DE AVISO
                    }else {
                        transaction.update(referencia, "Saldo", NovoSaldo);

                        ReceberTranferencia(); //Função para o segundo usuário receber a transação

                        Snackbar snackbar = Snackbar.make(view, "Tranferência realizada com sucesso", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();

                    }
                    return null;
                }
            });
}
        }
//FUNÇÃO PARA O SEGUNDO USUÁRIO RECEBER A TRANSFERÊNCIA - CHAMA EM "ConcluirTransferência"
    public void ReceberTranferencia() {
        double ValorDoacao = Double.parseDouble(textvalor.getText().toString());
        UserRecebedor = textIduser.getText().toString();
        DocumentReference documentReference = database.collection("Usuários").document(UserRecebedor);
        database.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot documentSnapshot = transaction.get(documentReference);
                double NovoSaldo = documentSnapshot.getDouble("Saldo") + ValorDoacao;
                transaction.update(documentReference, "Saldo", NovoSaldo);
                return null;


            }
        });
    }


    public void IniciarComponents(){
        textNome = findViewById(R.id.textNome);
        TextTelefone = findViewById(R.id.TextTelefone);
        TextEmail = findViewById(R.id.textEmail);
        TextSaldo = findViewById(R.id.textSaldo);
        IconeMinimizar = findViewById(R.id.ViewIconeMinimizar);
        MenuIcone = findViewById(R.id.MenuIcone);
        ViewMenu = findViewById(R.id.ViewMenu);
        Abauser = findViewById(R.id.AbaUsuarios);
        textIduser = findViewById(R.id.textIdUser);
        textvalor = findViewById(R.id.textValor);

    }


}
