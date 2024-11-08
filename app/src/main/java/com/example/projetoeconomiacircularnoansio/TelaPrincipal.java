package com.example.projetoeconomiacircularnoansio;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends AppCompatActivity {
    View ViewMenu;
    View ViewPerfil;
    View MenuSobre;
    ImageView IconeMinimizar;
    ImageView MenuIcone;
    Button AbaUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,0);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponents();


    }


    public void MostrarOpçoes(View view) {
        ViewMenu.setVisibility(View.VISIBLE);
        MenuSobre.setVisibility(View.VISIBLE);
        MenuIcone.setVisibility(View.INVISIBLE);
        IconeMinimizar.setVisibility(View.VISIBLE);
        AbaUser.setVisibility(View.VISIBLE);

    }
    public void FecharOpçoes(View view) {
        IconeMinimizar.setVisibility(View.INVISIBLE);
        MenuIcone.setVisibility(View.VISIBLE);
        ViewMenu.setVisibility(View.INVISIBLE);
        AbaUser.setVisibility(View.INVISIBLE);


    }

    public void AbaPerfil(View view) {
        Intent AbrirTelaPerfil = new Intent(TelaPrincipal.this, TelaDePerfil.class);
        startActivity(AbrirTelaPerfil);
        finish();
    }
    public void AbaInformaçoes(View view) {
        Intent AbrirTelaInfo = new Intent(TelaPrincipal.this, TelaDeInformacao.class);
        startActivity(AbrirTelaInfo);
        finish();
    }

    public void AbaDoaçao(View view) {
        Intent AbrirTelaInfo = new Intent(TelaPrincipal.this, TelaDeDoacao.class);
        startActivity(AbrirTelaInfo);
        finish();
    }

    public void AbaListUser(View view) {
        Intent AbrirListaUsr = new Intent(TelaPrincipal.this, ListaUsuers.class);
        startActivity(AbrirListaUsr);
        finish();
    }

    private void IniciarComponents(){
        ViewMenu = findViewById(R.id.ViewMenu);
        ViewPerfil = findViewById(R.id.MenuPerfil);
        MenuSobre = findViewById(R.id.MenuSobre);
        IconeMinimizar = findViewById(R.id.ViewIconeMinimizar);
        MenuIcone = findViewById(R.id.MenuIcone);
        AbaUser = findViewById(R.id.AbaUsuarios);




    }


}
