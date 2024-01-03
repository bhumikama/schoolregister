package com.example.sdvs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class TeacherActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Toolbar tool;
    String className,subjectName;

    private TeacherAdapter adapter;
    private EditText id_edit,name_edit;
    private DbHelper dbHelper;
    private MyDialog mycalendar;
    private int position;
    private long cid;
    TextView subtitle ;
     Calendar cal;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TeacherBean> teacherBeans=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_list);

        Intent intent = getIntent();
        className = intent.getStringExtra("DESIGNATION");
        subjectName= intent.getStringExtra("INSTITUTION");
        position= intent.getIntExtra("POSITION",-1);
        cid = intent.getLongExtra("CID",-1);
        mycalendar = new MyDialog();
        dbHelper = new DbHelper(this);
        tool = findViewById(R.id.tool_id);
        subtitle = tool.findViewById(R.id.subtitle_tool);
        setToolBar();
        //mycalendar = new MyDialog();
        cal = Calendar.getInstance();
        loadData();
        //setToolBar();

        recyclerView=findViewById(R.id.teacher_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeacherAdapter(TeacherActivity.this,teacherBeans);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TeacherAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                changeStatus(position);
            }
        });
    }

    private void loadData() {
        Cursor cursor = dbHelper.getStudentTable(cid);

        if(cursor.getCount() == 0)
        {
            Toast.makeText(this,"no data ", Toast.LENGTH_LONG).show();
        }
        else {
            while(cursor.moveToNext())
            {
                long sid = Long.parseLong(cursor.getString(0));
                long cid = cursor.getLong(1);
                int roll = cursor.getInt(2);
                String name = cursor.getString(3);
                TeacherBean teacher= new TeacherBean(sid,roll,name);
                teacherBeans.add(teacher);

            }
           cursor.close();

        }

    }

    private void changeStatus(int position) {
        String status = teacherBeans.get(position).getStatus();
        if(status.equals("P"))
            status="A";
        else
            status="P";

        teacherBeans.get(position).setStatus(status);
        adapter.notifyItemChanged(position);

    }

    private void setToolBar() {


        TextView title = tool.findViewById(R.id.title_tool);

        ImageButton back = tool.findViewById(R.id.back_tool);
        ImageButton save = tool.findViewById(R.id.save_tool);

        title.setText(className);

        subtitle.setText(subjectName+ "|" +mycalendar.getDate());
        back.setOnClickListener(v -> onBackPressed());
        tool.inflateMenu(R.menu.teacher_menu);
        tool.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.add_teach)
                {
                    showAddDialog();
                }
                if(item.getItemId() == R.id.change_date)
                {
                    showCalendar();
                }

                return true;
            }
        });
    }

    public void showCalendar() {

           mycalendar.show(getSupportFragmentManager(),"datepicker");
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater =LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.add_dialog,null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button add=(Button)view.findViewById(R.id.yes_button);
        Button cancel=(Button)view.findViewById(R.id.no_button);

        id_edit= (EditText)view.findViewById(R.id.edt_01);
        name_edit=(EditText)view.findViewById(R.id.edt_02);
        id_edit.setHint("ID");
        name_edit.setHint("NAME");

        cancel.setOnClickListener(v->dialog.dismiss());
        add.setOnClickListener(v->{
            addTeacheritems();
            dialog.dismiss();
        });


    }

    private void addTeacheritems() {
        String faculty_id = id_edit.getText().toString();
        String name= name_edit.getText().toString();
        int roll = Integer.parseInt(faculty_id);
        long sid = dbHelper.addStudent(cid,roll,name);
        TeacherBean teacher= new TeacherBean(sid,roll,name);
        teacherBeans.add(teacher);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String date = DateFormat.format("dd.MM.yyyy",calendar).toString();
        subtitle.setText(date);
    }
}
