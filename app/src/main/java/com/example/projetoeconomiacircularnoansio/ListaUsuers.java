package com.example.projetoeconomiacircularnoansio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoeconomiacircularnoansio.CriarLista.CustomAdapter;
import com.example.projetoeconomiacircularnoansio.CriarLista.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;



public class ListaUsuers extends AppCompatActivity {

    List<Model>modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore DataBase;
    CustomAdapter adapter;
    ImageView IconeMinimizar;
    ImageView MenuIcone;
    View ViewMenu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_usuers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
        DataBase = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        Mostrardados();
        IniciarComponents();
    }
/**--------FUNÇÃO PARA BSUCAR OS DADOS NO DATABASE E INSERIR NA LISTA---------**/
    private void Mostrardados() {
        DataBase.collection("Usuários").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc: task.getResult()){
                            Model model = new Model(doc.getString("id"),
                                    doc.getString("Nome"), doc.getString("Email"),doc.getData().get("Saldo").toString());
                            modelList.add(model);
                        }
                        adapter = new CustomAdapter(ListaUsuers.this, modelList);
                        mRecyclerView.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    /**------------------------FIM--------------------------**/


    /**----------FUNCIONALIDADE DOS BOTÕES------------**/
    public void AbaInformaçoes(View view) {
        Intent AbrirTelaInfo = new Intent(ListaUsuers.this, TelaDeInformacao.class);
        startActivity(AbrirTelaInfo);
        finish();
    }


    public void AbaHome(View view) {
        Intent AbrirTelaInicial = new Intent(ListaUsuers.this, TelaPrincipal.class);
        startActivity(AbrirTelaInicial);
        finish();
    }

    public void AbaDoaçao(View view) {
        Intent AbrirTelaInfo = new Intent(ListaUsuers.this, TelaDeDoacao.class);
        startActivity(AbrirTelaInfo);
        finish();

    }
    public void AbaPerfil(View view) {
        Intent AbrirTelaPerfil = new Intent(ListaUsuers.this, TelaDePerfil.class);
        startActivity(AbrirTelaPerfil);
        finish();
    }

    public void MostrarOpçoes(View view) {
        ViewMenu.setVisibility(View.VISIBLE);
        MenuIcone.setVisibility(View.INVISIBLE);
        IconeMinimizar.setVisibility(View.VISIBLE);
    }
    public void FecharOpçoes(View view) {
        IconeMinimizar.setVisibility(View.INVISIBLE);
        MenuIcone.setVisibility(View.VISIBLE);
        ViewMenu.setVisibility(View.INVISIBLE);
    }
    /** ----------------------FIM--------------------------**/

    public void IniciarComponents(){
        IconeMinimizar = findViewById(R.id.ViewIconeMinimizar);
        MenuIcone = findViewById(R.id.MenuIcone);
        ViewMenu = findViewById(R.id.ViewMenu);
    }

}