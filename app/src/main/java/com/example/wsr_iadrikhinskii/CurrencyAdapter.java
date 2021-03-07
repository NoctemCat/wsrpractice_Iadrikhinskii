package com.example.wsr_iadrikhinskii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
   private List<Valute> mValutes;
   private Context mContext;

   CurrencyAdapter(List<Valute> valutes){
       this.mValutes = valutes;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       mContext = parent.getContext();
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.currency_list, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Valute valute = mValutes.get(position);
        holder.textname.setText(valute.getName());
        holder.textCharCode.setText(valute.getCharCode());

        String priceString = valute.getPrice();
        priceString =priceString.replace(",",".");
        float price = Float.parseFloat(priceString) / valute.getNominal();

        DecimalFormat df = new DecimalFormat("#.####");
        holder.textbuy.setText(df.format(price));
        holder.textsell.setText(df.format(price));

        int id = mContext.getResources().getIdentifier(("f_" + valute.getCharCode().toLowerCase()),
                "drawable", mContext.getPackageName());
        if(id != 0){
            holder.imageView.setImageResource(id);
        }
        else{
            holder.imageView.setImageResource(R.drawable.empty);
        }
    }

    @Override
    public int getItemCount() {
       if(mValutes == null){
           return 0;
       }
       return mValutes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textname;
        TextView textCharCode;
        TextView textbuy;
        TextView textsell;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textname = (TextView)itemView.findViewById(R.id.textname);
            textCharCode = (TextView)itemView.findViewById(R.id.charCode);
            textbuy = (TextView)itemView.findViewById(R.id.textbuy);
            textsell = (TextView)itemView.findViewById(R.id.textsell);
            imageView = (ImageView)itemView.findViewById(R.id.itemImageView);
        }
    }
}
