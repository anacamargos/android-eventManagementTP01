package com.example.ana.trabalhopratico1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ana on 23/09/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Convidado> listaDeConvidados;
    View view;
    ViewHolder viewHolder;

    public RecyclerViewAdapter(Context context, ArrayList<Convidado> listaDeConvidados) {
        this.context = context;
        this.listaDeConvidados = listaDeConvidados;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
        Convidado convidado = listaDeConvidados.get(position);
        holder.nomeConvidadoTV.setText(convidado.getNome());
        holder.numeroConvidadoTV.setText(convidado.getNumero());
        holder.emailConvidadoTV.setText(convidado.getEmail());

        /*holder.botaoWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(sendIntent, ""));
                holder.teste.setText("Cliquei");
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listaDeConvidados.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomeConvidadoTV;
        TextView numeroConvidadoTV;
        TextView emailConvidadoTV;
        //TextView teste;
        //ImageButton botaoWhats;

        public ViewHolder(View itemView) {
            super(itemView);

            nomeConvidadoTV = (TextView) itemView.findViewById(R.id.nomeConvidado);
            numeroConvidadoTV = (TextView) itemView.findViewById(R.id.numeroConvidado);
            emailConvidadoTV = (TextView) itemView.findViewById(R.id.emailConvidado);
            //botaoWhats = (ImageButton) itemView.findViewById(R.id.image_button);
            //teste = (TextView) itemView.findViewById(R.id.teste);
        }

    }
}
