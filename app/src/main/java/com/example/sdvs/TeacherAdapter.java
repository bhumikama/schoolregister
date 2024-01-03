package com.example.sdvs;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class TeacherAdapter  extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    Context context;
    ArrayList<TeacherBean> teacherBeans;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{

        public void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public TeacherAdapter(Context context, ArrayList<TeacherBean> teacherBeans) {
        this.context = context;
        this.teacherBeans = teacherBeans;

    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator =LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.teacher_row,parent,false);
        return new TeacherViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        holder.faculty_id.setText(String.valueOf(teacherBeans.get(position).getStudent_roll()));
        holder.name.setText(String.valueOf(teacherBeans.get(position).getName()));
        holder.status.setText(String.valueOf(teacherBeans.get(position).getStatus()));

        holder.teacherLayout.setCardBackgroundColor(getColor(position));

    }

    private int getColor(int position) {
        String status= teacherBeans.get(position).getStatus();
        if(status.equals("P"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
        else if (status.equals("A"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.absent)));

        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.normal)));
    }

    @Override
    public int getItemCount() {
        return teacherBeans.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder  {

        TextView faculty_id,name;
        TextView status;
        CardView teacherLayout;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            faculty_id=itemView.findViewById(R.id.txt_id);
            name=itemView.findViewById(R.id.txt_name);
            status=itemView.findViewById(R.id.txt_status);

            teacherLayout=itemView.findViewById(R.id.teacher_layout);
            //teacherLayout.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(v-> onItemClickListener.onClick(getAdapterPosition()));
        }
    }
}
