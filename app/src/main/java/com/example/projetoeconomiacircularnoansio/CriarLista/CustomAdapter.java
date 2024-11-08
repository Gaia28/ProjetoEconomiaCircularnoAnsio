package com.example.projetoeconomiacircularnoansio.CriarLista;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoeconomiacircularnoansio.ListaUsuers;
import com.example.projetoeconomiacircularnoansio.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    ListaUsuers listaUsuers;
    ListActivity listActivity;
    List<Model> modelList;
    Context context;


    public CustomAdapter(ListaUsuers listaUsuers, List<Model> modelList) {
        this.listaUsuers = listaUsuers;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_aluno, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnclickListener(new ViewHolder.ClickListiner() {
            @Override
            public void onIntemClick(View view, int position) {

                String Nome = modelList.get(position).getNome();
                String Email = modelList.get(position).getEmail();
                Toast.makeText(listaUsuers,Nome+"/ "+Email,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.NomeText.setText(modelList.get(position).getNome());
        holder.EmailText.setText(modelList.get(position).getEmail());
        holder.SaldoText.setText(modelList.get(position).getSaldo() + " R$");
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
