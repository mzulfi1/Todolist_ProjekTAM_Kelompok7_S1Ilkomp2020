package com.zulfikar.todolisttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationMessage extends AppCompatActivity {
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);
        message = findViewById(R.id.tv_message);
        Bundle bundle = getIntent().getExtras();
        message.setText(bundle.getString("message"));
    }
}