package com.example.sdvs;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
  ArrayList<ClassBean> classBeans;

    public CustomAdapter(Context context, ArrayList<ClassBean> classBeans) {
        this.context = context;
        this.classBeans = classBeans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator =LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.designation_show.setText(String.valueOf(classBeans.get(position).getClass_name()));
        holder.institute_show.setText(String.valueOf(classBeans.get(position).getSubject_name()));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TeacherActivity.class);
                intent.putExtra("DESIGNATION",classBeans.get(position).getClass_name());
                intent.putExtra("INSTITUTION",classBeans.get(position).getSubject_name());
                intent.putExtra("CID",        classBeans.get(position).getCid());
                intent.putExtra("POSITION",position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return classBeans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener {

        TextView designation_show,institute_show;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            designation_show=itemView.findViewById(R.id.row_designation);
            institute_show=itemView.findViewById(R.id.row_institute);
            mainLayout=itemView.findViewById(R.id.row_layout);
            mainLayout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),101,0,"Edit");
            menu.add(getAdapterPosition(),102,0,"Delete");
        }
    }
}
