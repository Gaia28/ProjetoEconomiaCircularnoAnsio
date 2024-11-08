package com.example.projetoeconomiacircularnoansio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    ImageView IconeMinimizar;
    ImageView MenuIcone;
    View ViewMenu;
    Button Abauser;
    int saldo;


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
    public void ConcluirTransferencia(View view) {

        textIduser.setText(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

        double ValorDoacao = Double.parseDouble(textvalor.getText().toString());

        final DocumentReference referencia = database.collection("Usuários").document(Userid);

        //ERRO AQUI
        database.collection("Usuários").document(String.valueOf(textIduser));
        //

        database.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(referencia);
                double NovoSaldo = snapshot.getDouble("Saldo") - ValorDoacao;
                transaction.update(referencia, "Saldo",NovoSaldo);
                //ERRO AQUI
                double SaldoRecebido = snapshot.getDouble("Saldo") + ValorDoacao;
                transaction.update(referencia, "Saldo", SaldoRecebido);
                //
                return null;
            }
        });
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