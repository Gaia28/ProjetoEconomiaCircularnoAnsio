package com.example.projetoeconomiacircularnoansio.CriarLista;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoeconomiacircularnoansio.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView NomeText, EmailText,SaldoText;
    View ItemV;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        ItemV = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclickListener.onIntemClick(v,getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mclickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });
        NomeText = itemView.findViewById(R.id.NomeUser);
        EmailText = itemView.findViewById(R.id.EmailUser);
        SaldoText = itemView.findViewById(R.id.SaldoUser);
    }
    private ViewHolder.ClickListiner mclickListener;

    public interface ClickListiner{
        void onIntemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnclickListener(ViewHolder.ClickListiner clickListiner){
        mclickListener = clickListiner;
    }
}
