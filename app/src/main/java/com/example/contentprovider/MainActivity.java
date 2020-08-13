package com.example.contentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchView;
    TextView name, number;
    AdapterRecycle adapterRecycle;
    ArrayList<Contacts> arrayList = new ArrayList<>();
    DBHelper db;
    static final int request1 = 101;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        searchView = findViewById(R.id.searchView);
        name = findViewById(R.id.name);
        number = findViewById(R.id.phone);
        db = new DBHelper(MainActivity.this);

        adapterRecycle = new AdapterRecycle(MainActivity.this, arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.setItemAnimator(new DefaultItemAnimator());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (arrayList.contains(query)) {
                    adapterRecycle.getFilter().filter(query);
                } else {
                    Toast.makeText(MainActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterRecycle.getFilter().filter(newText);
                return false;
            }
        });
        recyclerView.setAdapter(adapterRecycle);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        }).attachToRecyclerView(recyclerView);
        // recyclerView.setAdapter(adapterRecycle);
        getContact();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("search")) {


        } else if (item.getTitle().equals("Add new Contacts")) {
            Intent i = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(i, request1);
            Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
        }
        return true;


    }

    private void getContact() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String Name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String Number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                boolean b = db.InsertEMP(Name, Number);
                Contacts contacts = new Contacts();
                contacts.setName(Name);
                contacts.setNumber(Number);
                arrayList.add(contacts);
//                Toast.makeText(MainActivity.this,Name+"",Toast.LENGTH_LONG).show();
//               Toast.makeText(MainActivity.this,Number+"",Toast.LENGTH_LONG).show();
            }
            Log.e("name", cursor.toString());

        } else {
            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
        }
    }
}
