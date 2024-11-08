package com.example.projetoeconomiacircularnoansio;

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

public class TelaDeInformacao extends AppCompatActivity {

    ImageView IconeMinimizar;
    ImageView MenuIcone;
    View ViewMenu;
    Button Abauser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, 0);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_de_informacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponents();
    }
    public void AbaPerfil(View view) {
        Intent AbrirTelaPerfil = new Intent(TelaDeInformacao.this, TelaDePerfil.class);
        startActivity(AbrirTelaPerfil);
        finish();
    }

    public void AbaHome(View view) {
        Intent AbrirTelaInicial = new Intent(TelaDeInformacao.this, TelaPrincipal.class);
        startActivity(AbrirTelaInicial);
        finish();
    }
    public void AbaDoaçao(View view) {
        Intent AbrirTelaInfo = new Intent(TelaDeInformacao.this, TelaDeDoacao.class);
        startActivity(AbrirTelaInfo);
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
        Intent AbrirListaUsr = new Intent(TelaDeInformacao.this, ListaUsuers.class);
        startActivity(AbrirListaUsr);
        finish();
    }

    public void IniciarComponents(){
    IconeMinimizar = findViewById(R.id.ViewIconeMinimizar);
    MenuIcone = findViewById(R.id.MenuIcone);
    ViewMenu = findViewById(R.id.ViewMenu);
    Abauser = findViewById(R.id.AbaUsuarios);
}

}