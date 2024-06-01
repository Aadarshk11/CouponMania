package com.example.couponmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CHAT extends AppCompatActivity {

    private List<ChatMessage> messages;
    private ChatAdapter adapter;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private Button buttonSend;
    private TextView textViewNoChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
//        int noChats = 0;
//        if(noChats==0){
//            textViewNoChats.setVisibility(View.VISIBLE);
//        }else {
//            textViewNoChats.setVisibility(View.GONE);
//        }
        messages = new ArrayList<>();
        adapter = new ChatAdapter(this, messages);

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = editTextMessage.getText().toString().trim();
                if (!messageContent.isEmpty()) {
                    // Create and add a new message to the list
                    ChatMessage message = new ChatMessage("Sender", messageContent, System.currentTimeMillis());
                    messages.add(message);
                    adapter.notifyDataSetChanged();

                    // Clear the input field
                    editTextMessage.setText("");

                    // Optionally, scroll to the last message
                    recyclerViewMessages.scrollToPosition(messages.size() - 1);
                }
            }
        });
    }
}