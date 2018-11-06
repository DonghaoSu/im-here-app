package com.example.user.teamproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.couchbase.lite.Array;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Expression;
import com.couchbase.lite.MutableArray;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

import java.util.ArrayList;

public class ChatRoom extends AppCompatActivity {
    String myExtension, friendExtension, referChatRoom, chatRoomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //Textfield = findViewByID();


        Intent intent = getIntent();
        referChatRoom = intent.getStringExtra("ReferChatRoom");
        MutableDocument chatRoom;

        try {
            DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
            Database chatRoomDatabase = new Database("chatRooms", config);

            Query query = QueryBuilder.select(SelectResult.property("chatRoomID"))
                    .from(DataSource.database(chatRoomDatabase))
                    .where(Expression.property("referChatRoom").equalTo(Expression.string(referChatRoom)));
            ResultSet rs = query.execute();

            if(rs.allResults().size() == 1){
                rs = query.execute();
                chatRoomID = rs.allResults().get(0).getString("chatRoomID");
                chatRoom = chatRoomDatabase.getDocument(chatRoomID).toMutable();
            }else{
                // Create a new document (i.e. a record) in the database.
                chatRoom = new MutableDocument();
                chatRoom.setString("chatRoomID", chatRoom.getId());
            }

            /*
            while(connected){
                Array[] messageArray = new Array[20];

                sendBtn.setOnclickListener{
                    Array[] message = new Array[3];
                    String text = Textfield.getText();
                    String speaker = user.getText();
                    String time = deviceTime.getText();

                    message[0] = text;
                    message[1] = speaker;
                    message[2] = time;
                    for(int i = 0; i < 20; i++){
                        if(messageArray[i] == null){
                            messageArray[i] = message;
                        }else if(messageArray[19] != null){
                            for(int j = 0; j < 19; j++){
                                messageArray[j] = messageArray[j+1];
                            }
                            messageArray[19] = message;
                        }
                    }
                    chatRoom.setArray("messageArray", messageArray);
                }
                messageWall(chatroom);
            }
            chatRoomDatabase.save(chatroom);
            finish();
            */
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }


    private void messageWall(MutableDocument mutableDocument) {

        Array messageArray = mutableDocument.getArray("messageArray");

        //get the data and append to a list
        ArrayList<String> listData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 3; j++) {
                String[] message = new String[3];
                message[j] = messageArray.getArray(i).getString(j);
                listData.add(message[j]);
            }
        }
/*
        //get the value from the data in column, then add it to the ArrayList
        listData.add(intent.getStringExtra("Username"));
        listData.add(intent.getStringExtra("Name"));
        listData.add(intent.getStringExtra("Extension"));

        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        users.setAdapter(adapter);
        */
    }

}