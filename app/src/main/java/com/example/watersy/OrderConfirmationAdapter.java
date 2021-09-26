package com.example.watersy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watersy.model.WatersyOrderItem;

import java.util.ArrayList;

public class OrderConfirmationAdapter extends RecyclerView.Adapter<OrderConfirmationAdapter.OrderConfirmationViewHolder> {
    public ArrayList<WatersyOrderItem> items;
    public class OrderConfirmationViewHolder extends RecyclerView.ViewHolder{
        public TextView idTV, nameTV, qtyTV, priceTV;

        public OrderConfirmationViewHolder(@NonNull final View itemView) {
            super(itemView);
            idTV = itemView.findViewById(R.id.tv_prod_id);
            nameTV = itemView.findViewById(R.id.tv_prod_name);
            qtyTV = itemView.findViewById(R.id.tv_prod_qty);
            priceTV = itemView.findViewById(R.id.tv_prod_price);

        }
    }

    public OrderConfirmationAdapter(ArrayList<WatersyOrderItem> items){
        this.items = items;
    }

    @NonNull
    @Override
    public OrderConfirmationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_confirmation_item, parent, false);
        return new OrderConfirmationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderConfirmationViewHolder holder, int position) {
        WatersyOrderItem item = items.get(position);

        holder.idTV.setText(item.id);
        holder.nameTV.setText(item.name);
        holder.qtyTV.setText(String.valueOf(item.qty));
        holder.priceTV.setText(String.valueOf(item.itemPrice * item.qty));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
