package com.raykgeneer.evilgeniuses.blueartstudio;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<TatuadoresCardView> list = new ArrayList<>();
    public CardAdapter(List<TatuadoresCardView> list) {
        this.list = list;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public TatuadoresCardView getItem(int i) {
        return list.get(i);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tatuadore_card_view, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tatuadoresCardView = getItem(position);
        holder.Identificador.setText(list.get(position).Identificador);
        holder.Valor.setText(list.get(position).Valor);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Identificador;
        TextView Valor;
        TatuadoresCardView tatuadoresCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            Identificador = (TextView) itemView.findViewById(R.id.TextIdentificador);
            Valor = (TextView) itemView.findViewById(R.id.TextValor);
        }
    }
}