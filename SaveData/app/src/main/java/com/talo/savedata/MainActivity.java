package com.talo.savedata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String File_Name = "Data.txt";

    private Button btnWrite, btnRead, btnClear;
    private EditText edtInput;
    private TextView txtFileContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        btnClear = findViewById(R.id.btnClear);

        edtInput = findViewById(R.id.edtInput);
        txtFileContext = findViewById(R.id.txtFileContext);

        btnWrite.setOnClickListener(btnWriteClick);
        btnRead.setOnClickListener(btnReadClick);
        btnClear.setOnClickListener(btnClearClick);
        getSupportActionBar().hide();
    }
    private View.OnClickListener btnWriteClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //把資料寫入檔案
            FileOutputStream fileOutputStream = null;
            BufferedOutputStream bufferedOutputStream = null;

            try {
                fileOutputStream = openFileOutput(File_Name, MODE_APPEND);
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                bufferedOutputStream.write(edtInput.getText().toString().getBytes());   //取得EditText資料
                bufferedOutputStream.close();
                Toast.makeText(MainActivity.this, "Write Data", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private View.OnClickListener btnReadClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;

            try {
                fileInputStream = openFileInput(File_Name);
                bufferedInputStream = new BufferedInputStream(fileInputStream);

                byte[] buffbyte = new byte[10];

                txtFileContext.setText("");

                do {
                    int take = bufferedInputStream.read(buffbyte);
                    if (take == -1)
                        break;
                    else
                        txtFileContext.append(new String(buffbyte), 0, take);
                }while (true);

                bufferedInputStream.close();
                Toast.makeText(MainActivity.this, "Read Data", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private View.OnClickListener btnClearClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = openFileOutput(File_Name, MODE_PRIVATE);
                fileOutputStream.close();
                txtFileContext.setText("");
                Toast.makeText(MainActivity.this, "Clear Data", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}