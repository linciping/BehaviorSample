package com.linciping.behaviorsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linciping.behaviorsample.bottom.ListBottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnShowDialog=findViewById(R.id.btn_show_dialog);
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBottomSheetDialog listBottomSheetDialog=new ListBottomSheetDialog();
                listBottomSheetDialog.show(getSupportFragmentManager(),"listBottomSheetDialog");
            }
        });
    }
}