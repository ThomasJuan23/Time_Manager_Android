package com.example.timemanager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class register extends AppCompatActivity {
    private static final String[] gs = {"Study Better", "Bodybuilding", "Work Better", "Business reminder", "Others"};
    int id = 0, scores = 0;
    String occupation = "", password = "";
    String goal = "";
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button back = findViewById(R.id.rBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(register.this, MainActivity.class);
                startActivity(i);
            }
        });
        Intent intent = getIntent();
        int ids[] = intent.getIntArrayExtra("IDS");
        Spinner goals = findViewById(R.id.goals);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goals.setAdapter(adapter);
        goals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                goal = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                goal = null;
            }
        });
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eId = findViewById(R.id.id);
                EditText eOccupation = findViewById(R.id.occupation);
                occupation = eOccupation.getText().toString();
                EditText ePassword1 = findViewById(R.id.password1);
                String password1 = ePassword1.getText().toString();
                EditText ePassword2 = findViewById(R.id.password2);
                String password2 = ePassword2.getText().toString();
                int lengths = eId.getText().length();
                if (lengths != 0) {
                    id = Integer.parseInt(eId.getText().toString());
                    ifID(id, ids);
                    if (password1.equals(password2)) {
                        password = password1;
                    }
                    int length = eId.getText().toString().length();
                    if (length != 6) {
                        Toast.makeText(register.this, "The id number need to be 6 integers, such as 123456", Toast.LENGTH_LONG).show();
                        eId.setText(null);
                    } else if (!password1.equals(password2)) {
                        Toast.makeText(register.this, "Please enter the same password", Toast.LENGTH_LONG).show();
                        ePassword1.setText(null);
                        ePassword2.setText(null);
                    } else if (password1.equals("")) {
                        Toast.makeText(register.this, "Password cannot be null", Toast.LENGTH_LONG).show();
                        ePassword1.setText(null);
                        ePassword2.setText(null);
                    } else {
                        if (k == 1) {
                            Toast.makeText(register.this, "ID has been used", Toast.LENGTH_LONG).show();
                            eId.setText("");
                        } else {
                            DatabaseThread dt = new DatabaseThread(id, 0);
                            dt.start();
                            try {
                                dt.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(register.this, MainActivity.class);
                            intent.putExtra("passID", id);
                            intent.putExtra("passPassword", password);
                            intent.putExtra("passOccupation", occupation);
                            intent.putExtra("passGoal", goal);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(register.this, "Please input id", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    static class DatabaseThread extends Thread {
        int idNumber;
        int score;
        String DRIVER = "com.mysql.jdbc.Driver";
        String URL = "jdbc:mysql://bj-cynosdbmysql-grp-c3nr713k.sql.tencentcdb.com:26327/Time Manager";
        String USER = "root";
        String PASSWORD = "SAyouNAla123";

        DatabaseThread(int idNumber, int score) {
            this.idNumber = idNumber;
            this.score = score;
        }

        public void run() {
            try {
                Class.forName(DRIVER);
                Connection conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Information (id,score) VALUES (?,?)");
                ps.setInt(1, idNumber);
                ps.setInt(2, score);
                ps.executeUpdate();
                ps.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ifID(int idNumber, int[] ids) {
        k = 0;
        int j = 0;
        if (ids[0] != -1) {
            while (j < ids.length) {
                if (idNumber == ids[j]) {
                    k = 1;
                    break;
                }
                j++;
            }
        }
    }
}