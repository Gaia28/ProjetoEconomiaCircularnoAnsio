package com.example.projetoeconomiacircularnoansio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import android.graphics.ImageFormat;
import android.widget.Toast;

public class TelaDeDoacao extends AppCompatActivity {
    /**-------------VARIAVEIS USADAS----------*/

    ImageView IconeMinimizar;
    ImageView MenuIcone;
    View ViewMenu;
    Button Abauser;
    private EditText NomeDoacao, DescricaoDoacao, TipoDoacao;
    String[] mensagens = {"Preencha Todos os Campos","Cadastro Realizado com Sucesso"};
    ImageView ImageDoacao;
    String[] Opcoes = {"Calçado", "Roupas", "Eletrônicos", "Utilidades"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapteritens;
    TextInputLayout DownList;
    String UserId;
    FirebaseFirestore Database = FirebaseFirestore.getInstance();



    /**----------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, 0);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_de_doacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });


        IniciarComponents();
        MostrarOpcoes();
    }

    /**---------------BOTÕES DE MENU----------------*/

    public void AbaPerfil(View view) {
        Intent AbrirTelaPerfil = new Intent(TelaDeDoacao.this, TelaDePerfil.class);
        startActivity(AbrirTelaPerfil);
        finish();
    }
    public void AbaInformaçoes(View view) {
        Intent AbrirTelaInfo = new Intent(TelaDeDoacao.this, TelaDeInformacao.class);
        startActivity(AbrirTelaInfo);
        finish();
    }
    public void AbaHome(View view) {
        Intent AbrirTelaInicial = new Intent(TelaDeDoacao.this, TelaPrincipal.class);
        startActivity(AbrirTelaInicial);
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
        Intent AbrirListaUsr = new Intent(TelaDeDoacao.this, ListaUsuers.class);
        startActivity(AbrirListaUsr);
        finish();
    }
    /**-------------------------FIM-----------------------------*/


    /**--------------CONCLUIR DOAÇÃO----------------*/

    public void DoarProduto(View view) {
        String Nome = NomeDoacao.getText().toString();
        String Descricao = DescricaoDoacao.getText().toString();
        DocumentReference reference = Database.collection("Usuários").document(UserId);


        if (Nome.isEmpty() || Descricao.isEmpty()){
            Snackbar BarraDeAviso = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT);
            BarraDeAviso.setBackgroundTint(Color.WHITE);
            BarraDeAviso.setTextColor(Color.BLACK);
            BarraDeAviso.show();
        }else{
            SalvarDoacao();
            Snackbar BarraDeAviso = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_SHORT);
            BarraDeAviso.setBackgroundTint(Color.WHITE);
            BarraDeAviso.setTextColor(Color.BLACK);
            BarraDeAviso.show();
        }
    }

    //SALVAR NO BANCO DE DADOS

    public void SalvarDoacao(){
        String Nome = NomeDoacao.getText().toString();
        String Descricao = DescricaoDoacao.getText().toString();
        String Item = DownList.getHint().toString();
        ImageView Foto = ImageDoacao;

        FirebaseFirestore DataBase = FirebaseFirestore.getInstance();

        Map<String,Object> Doacao = new HashMap<>();
        Doacao.put("Nome",Nome);
        Doacao.put("Descrição", Descricao);
        Doacao.put("Tipo",Item);
        Doacao.put("Doador",UserId);

        DocumentReference Referencia = DataBase.collection("Doações").document();
        Referencia.set(Doacao);
    }


    @Override
    protected void onStart() {
        super.onStart();
        UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    /**------------------------------FIM-------------------------------------*/



    /**----------------FUNÇÃO PARA IMPORTAR IMAGENS DA GALERIA---------------**/

    public void InserirImagem(View view) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            String[] Permissao = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(Permissao, 1001);
        }else{
            Escolherimagem();

        }
    }
    private void Escolherimagem(){
        Intent galeria = new Intent(Intent.ACTION_PICK);
        galeria.setType("image/*");
        startActivityForResult(galeria,1000);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1000){
            ImageDoacao.setImageURI(data.getData());
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1001:{
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }else{
                Toast.makeText(this,"Permissão negada",Toast.LENGTH_SHORT).show();
            }
            }
        }
    }
    /**---------------------------FIM-----------------------------------*/

    public void MostrarOpcoes(){
        adapteritens = new ArrayAdapter<String>(this,R.layout.item_opcoes,Opcoes);
        autoCompleteTextView.setAdapter(adapteritens);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(TelaDeDoacao.this,"Item" + item, Toast.LENGTH_SHORT).show();
                DownList.getHint();
                DownList.setHint(item);

            }
        });

    }

    public void IniciarComponents(){
        IconeMinimizar = findViewById(R.id.ViewIconeMinimizar);
        MenuIcone = findViewById(R.id.MenuIcone);
        ViewMenu = findViewById(R.id.ViewMenu);
        NomeDoacao = findViewById(R.id.NomeDoacao);
        DescricaoDoacao = findViewById(R.id.DescricaoDoacao);
        ImageDoacao = findViewById(R.id.imageDoacao);
        autoCompleteTextView = findViewById(R.id.TextAutoComplete);
        DownList = findViewById(R.id.DownList);
        Abauser = findViewById(R.id.AbaUsuarios);


    }



}