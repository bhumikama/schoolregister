package com.example.sdvs;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class AddEmployee extends Activity {

    ArrayList<ClassBean> classBeans  = new ArrayList<>();
    CustomAdapter adapter;
     RecyclerView recyclerview;
     FloatingActionButton add_button;
     EditText designation,institute;
     Button add , cancel;
     DbHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee);


        recyclerview = (RecyclerView) findViewById(R.id.add_recycle);
        dbhelper=new DbHelper(this);
        add_button = (FloatingActionButton) findViewById(R.id.float_add);
        add_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        loadclassData();


        adapter = new CustomAdapter(AddEmployee.this, classBeans);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(AddEmployee.this));


    }

    private void loadclassData() {
        Cursor cursor = dbhelper.getClassTable();
        Log.d("bhumika","in addclass method");
        //classBeans.clear();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No DATA",Toast.LENGTH_LONG).show();
        }
        //classBeans.clear();
       else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                //String classN = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.KEY_CLASS_NAME));
                String classN = cursor.getString(1);
                String subjectN = cursor.getString(2);
                //classBeans.add(new ClassBean(id, classN, subjectN));
                ClassBean bean = new ClassBean(id,classN,subjectN);
                classBeans.add(bean);
            }
            cursor.close();
        }

    }

    private void showDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater =LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.add_dialog,null);
            builder.setView(view);

            AlertDialog dialog = builder.create();
            dialog.show();

            add=(Button)view.findViewById(R.id.yes_button);
            cancel=(Button)view.findViewById(R.id.no_button);

            designation = (EditText)view.findViewById(R.id.edt_01);
            institute=(EditText)view.findViewById(R.id.edt_02);

            cancel.setOnClickListener(v->dialog.dismiss());
            add.setOnClickListener(v->{
                additems();
                dialog.dismiss();
            });
        }
        private void additems() {
            String className= designation.getText().toString();
            String subjectName= institute.getText().toString();

            long cid = dbhelper.addClass(className,subjectName);
            ClassBean bean = new ClassBean(cid,className,subjectName);
            classBeans.add(bean);
            adapter.notifyDataSetChanged();

        }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case 101:
                   updateDialog(item.getGroupId());
                   break;
            case 102:
                deleteClass(item.getGroupId());
                break;

        }

        return super.onContextItemSelected(item);
    }

    private void updateDialog(int position) {

        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(AddEmployee.this).inflate(R.layout.add_dialog,null,false);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        TextView title = view.findViewById(R.id.title_view);
        title.setText("UPDATE CLASS INFO");
        Button add1=(Button)view.findViewById(R.id.yes_button);
        Button cancel1=(Button)view.findViewById(R.id.no_button);

       EditText edt_01 = (EditText)view.findViewById(R.id.edt_01);
        EditText edt_02 = (EditText)view.findViewById(R.id.edt_02);

        cancel1.setOnClickListener(v->dialog.dismiss());
        add1.setOnClickListener(v -> {
            String class_name= edt_01.getText().toString();
            String subject_name= edt_02.getText().toString();
            dbhelper.updateClass(classBeans.get(position).getCid(),class_name,subject_name);
            classBeans.get(position).setClass_name(class_name);
            classBeans.get(position).setSubject_name(subject_name);
            adapter.notifyItemChanged(position);
            dialog.dismiss();
        });


    }




    private void deleteClass(int position) {
        //dbhelper = new DbHelper(AddEmployee.this);
        dbhelper.deleteClass(classBeans.get(position).getCid());
        classBeans.remove(position);
        adapter.notifyItemRemoved(position);

    }
}


