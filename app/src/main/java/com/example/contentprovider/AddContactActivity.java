package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {
    TextView name, number;
    Button btndone;
    DBHelper db;
    ArrayList<Contacts> arrayList = new ArrayList<Contacts>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        db = new DBHelper(AddContactActivity.this);
        name = findViewById(R.id.Ename);
        number = findViewById(R.id.Enumber);
        btndone = findViewById(R.id.btndone);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Number = number.getText().toString();
                boolean b = db.InsertEMP(Name, Number);
                addDataToList();
                if (b == true) {
                    if (Name != null && name != null) {
                        Toast.makeText(AddContactActivity.this, Name + "", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddContactActivity.this, Number + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddContactActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }

                name.setText("");
                number.setText("");
            }
        });



    }

    ArrayList<Contacts> addDataToList() {
        Cursor cs = db.getData();
        cs.moveToFirst();
        do {
            Contacts contacts = new Contacts();
            String name = cs.getString(cs.getColumnIndex(DBHelper.COL_1));
            String number = cs.getString(cs.getColumnIndex(DBHelper.COL_2));

            contacts.setName(name);
            contacts.setNumber(number);
            arrayList.add(contacts);

        } while (cs.moveToNext());

        return arrayList;
    }
}
