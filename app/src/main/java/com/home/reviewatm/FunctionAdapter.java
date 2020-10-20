package com.home.reviewatm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.zip.Inflater;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionHolder> {
    private final String[] functions;
    Context context;
    public FunctionAdapter(Context context){
        this.context = context;
        functions = context.getResources().getStringArray(R.array.functions);
    }
    @NonNull
    @Override
    public FunctionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new FunctionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionHolder holder, int position) {
        holder.nameText.setText(functions[position]);
    }


    @Override
    public int getItemCount() {
        return functions.length;
    }

    public class FunctionHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        public FunctionHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(android.R.id.text1);
        }
    }
}
