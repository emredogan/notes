    package com.note.notepad.notes;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

    public class MainActivity extends AppCompatActivity {

        ListView listView;

        static ArrayList<String> notes = new ArrayList<String>();
        static ArrayAdapter arrayAdapter;

        SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }

        } else {
            // do nothing
        }



        listView = findViewById(R.id.listView);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.note.notepad.notes", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        if (set== null) {

         //   notes.add("Example Note");

        } else {
            notes = new ArrayList<>(set);
        }



        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, notes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure")
                        .setMessage("Do you want to delete this note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();



                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes", set).apply();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                return true;
            }
        });
    }

        @Override
        public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case 1: {

                    if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show();
                    }

                    return;

                }
            }
        }


        // Menu Code

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.add_note_menu,menu);

            return super.onCreateOptionsMenu(menu);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            super.onOptionsItemSelected(item);

            if(item.getItemId() == R.id.add_note) {

                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                startActivity(intent);

                return true;
            }

            return false;
        }
    }
