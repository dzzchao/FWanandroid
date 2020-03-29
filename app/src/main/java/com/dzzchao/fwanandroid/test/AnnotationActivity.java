package com.dzzchao.fwanandroid.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dzzchao.fwanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class AnnotationActivity extends AppCompatActivity {

    @BindView(R.id.btnTestAnnotation)
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
//        Binding.bind(this);
        ButterKnife.bind(this);
        button.setText("haha");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("哈哈哈哈");
                Toast.makeText(AnnotationActivity.this, "hha", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
