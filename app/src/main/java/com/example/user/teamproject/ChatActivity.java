//package com.example.user.teamproject;
//
//import android.app.DownloadManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.wifi.WifiManager;
//import android.net.wifi.p2p.WifiP2pConfig;
//import android.net.wifi.p2p.WifiP2pDevice;
//import android.net.wifi.p2p.WifiP2pInfo;
//import android.net.wifi.p2p.WifiP2pManager;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
//import android.os.StrictMode;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Adapter;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.couchbase.lite.CouchbaseLiteException;
//import com.couchbase.lite.DataSource;
//import com.couchbase.lite.Database;
//import com.couchbase.lite.DatabaseConfiguration;
//
//import com.couchbase.lite.Document;
//import com.couchbase.lite.MutableDocument;
//import com.couchbase.lite.Expression;
//import com.couchbase.lite.Query;
//import com.couchbase.lite.QueryBuilder;
//import com.couchbase.lite.ResultSet;
//import com.couchbase.lite.Result;
//import com.couchbase.lite.SelectResult;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import org.ocpsoft.prettytime.PrettyTime;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.channels.AsynchronousChannel;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.Date;
//
//public class ChatActivity extends AppCompatActivity {
//
//    // WiFi Objects
//    WifiManager wifiManager;
//    WifiP2pManager mManager;
//    WifiP2pManager.Channel mChannel;
//
//    BroadcastReceiver mReceiver;
//    IntentFilter mIntentFilter;
//    InetAddress goAddress;
//
//    // Chat objects
//    ClientClass clientClass;
//    ServerClass serverClass;
//    SendReceive sendReceive;
//    //    ArrayList<String> msgList;
//    ArrayList msgList = new ArrayList<ChatModel>();
//    CustomAdapter adapter;
//
//    // UI elements
//    FloatingActionButton sendButton;
//    EditText entryBox;
//    ListView messageList;
//
//    // Self Identifiers
//    String selfUsername;
//    String selfUUID;
//
//    // Friend Identifiers
//    String docID, friendUUID, friendUsername;
//
//    // Databases
//    Database userListDatabase;
//    Database chatRoomDatabase;
//
//    static final int MESSAGE_READ =  1;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//        DatabaseConfiguration DBconfig = new DatabaseConfiguration(getApplicationContext());
//
//        Intent intent = getIntent();
////        getSupportActionBar().setTitle(intent.getStringExtra("deviceName").substring(5));
//
//        wireUiToVars();
//        setupObjects();
//
////        initiateConnection(intent.getStringExtra("deviceType"));
//
////        msgList = new ArrayList<>();
////        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, msgList);
////        messageList.setAdapter(adapter);
////        adapter = new CustomAdapter(this, msgList);
////        messageList.setAdapter(adapter);
//
//        // Get own information from userList Database and save to constants
////        try {
////            DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
////            userListDatabase = new Database("userList", config);
////
////            Query query = QueryBuilder.select(SelectResult.property("username"), SelectResult.property("UUID"), SelectResult.property("hasLogin"))
////                    .from(DataSource.database(userListDatabase))
////                    .where(Expression.property("hasLogin").equalTo(Expression.string("true")));
////
////            com.couchbase.lite.ResultSet rs = query.execute();
////            for (Result result: rs) {
////                if (result.getString("hasLogin").equals("true")) {
////                    selfUsername = result.getString("username");
////                    selfUUID = result.getString("UUID");
////                }
//////                Log.d("ChatActivity", "username: " + result.getString("username"));
//////                Log.d("ChatActivity", "UUID: " + result.getString("UUID"));
//////                Log.d("ChatActivity", "hasLogin: " + result.getString("hasLogin"));
////            }
////
////        } catch (CouchbaseLiteException ce) {
////            Log.d("ChatActivity", "Couldn't retrieve from the database");
////            ce.printStackTrace();
////        }
//
//        // Check if friend database exists, if no, create a new one
////        try {
////            DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
////
////        }
//
//
////        sendButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                try {
////                    String metadata = createJSONMeta(selfUUID, selfUsername);
////                    Log.d("Chat Activity", "uuid: "+ selfUUID);
////                    Log.d("Chat Activity", "username" + selfUsername);
////                    Log.d("Chat Activity", metadata);
////                    sendReceive.write(metadata.getBytes());
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////
////                String textToSend = entryBox.getText().toString();
////                ChatModel model = new ChatModel(textToSend, true);
////                adapter = new CustomAdapter(getApplicationContext(), msgList);
////                messageList.setAdapter(adapter);
////                entryBox.setText("");
////                sendReceive.write(textToSend.getBytes());
////                msgList.add(model);
////                adapter.notifyDataSetChanged();
////            }
////        });
//    }
//
//    private void wireUiToVars() {
//        sendButton = (FloatingActionButton) findViewById(R.id.send_fab);
//        entryBox = (EditText) findViewById(R.id.entryBox);
//        messageList = (ListView) findViewById(R.id.messageList);
//    }
//
//    private void setupObjects() {
//        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//
//        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
//        mChannel = mManager.initialize(this, getMainLooper(), null);
//
////        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
//
//        mIntentFilter = new IntentFilter();
//        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
//        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
//        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
//    }
//
//    private void initiateConnection(String deviceType) {
//        if (deviceType.equals("host")) {
//            final String deviceName = getIntent().getStringExtra("deviceName");
//            String deviceAddress = getIntent().getStringExtra("deviceAddress");
//            WifiP2pConfig config = new WifiP2pConfig();
//            config.deviceAddress = deviceAddress;
//
//            mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
//
//                @Override
//                public void onSuccess() {
//                    Toast.makeText(getApplicationContext(), "Connected to " + deviceName, Toast.LENGTH_SHORT).show();
//                    //TODO: Add to Couchbase DB
//                    // Get the database (and create it if it doesn’t exist).
//                    DatabaseConfiguration DBconfig = new DatabaseConfiguration(getApplicationContext());
//                    try {
//                        chatRoomDatabase = new Database("friendList", DBconfig);
//                    } catch (CouchbaseLiteException e) {
//                        e.printStackTrace();
//                    }
//
//                    // Create a new document (i.e. a record) in the database.
//                    MutableDocument friendDoc = new MutableDocument();
//                    docID = friendDoc.getId();
//                    friendDoc.setString("docID", docID);
//                    //imply the owner of friends by UUID, maybe other info
//                        /*
//                        friendDoc.setString("myUUID", myUUID);
//                        friendDoc.setBlob("myImage", myBlob);
//                        friendDoc.setString("myUsername", myUsername);
//                        */
//
//
//                    //a function to get friends' information and save document
//                    Intent intent = getIntent();
//                    friendUUID = intent.getStringExtra("FriendUUID");
//                    friendUsername = intent.getStringExtra("FriendUsername");
//                    //imageInByte = getArray(otherDevice);
//                    //friendBlob = new Blob("image/*", imageInByte);
//
//                    friendDoc.setString("friendUUID", friendUUID);
//                    friendDoc.setString("friendUsername", friendUsername);
//                    //friendDoc.setBlob("friendBlob", friendBlob);
//
//                    try {
//                        friendDatabase.save(friendDoc);
//                    } catch (CouchbaseLiteException e) {
//                        e.printStackTrace();
//                    }
//
//                    //create if chat doesn't exist, otherwise open it and reload old message
//                    try {
//                        chatDatabase = new Database(friendUUID, DBconfig);
//                        loadMessage(chatDatabase);
//                    } catch (CouchbaseLiteException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(int reason) {
//                    Toast.makeText(getApplicationContext(), "Failed to connect to " + deviceName, Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            });
//        } else if (deviceType.equals("client")) {
//            Toast.makeText(getApplicationContext(), "Client", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//
//            switch (msg.what) {
//                case MESSAGE_READ:
//                    byte[] readBuff = (byte[]) msg.obj;
//                    String tempMsg = new String(readBuff, 0, msg.arg1);
////                    Toast.makeText(getApplicationContext(), tempMsg, Toast.LENGTH_LONG).show();
//                    ChatModel model = new ChatModel(tempMsg, false);
//                    adapter = new CustomAdapter(getApplicationContext(), msgList);
//                    messageList.setAdapter(adapter);
//                    msgList.add(model);
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(getApplicationContext(), "Count:" + adapter.getCount(), Toast.LENGTH_LONG).show();
//                    // TODO: add to database
//                    try {
//                        DatabaseConfiguration DBconfig = new DatabaseConfiguration(getApplicationContext());
//                        chatDatabase = new Database(friendUUID, DBconfig);
//                        MutableDocument message = new MutableDocument();
//                        int count = (int) chatDatabase.getCount();
//                        //count can be last index
//                        //such as when count is 0, means there is no previous message
//                        //and the current message will be at index 0 to new chat db.
//                        //and so on.
//                        message.setInt("index", count);
//                        message.setValue("model", model);
//                        chatDatabase.save(message);
//
//                        //get time
//                        try {
//                            friendDatabase = new Database("friendList", DBconfig);
//                            Query query = QueryBuilder.select(SelectResult.property("docID"))
//                                    .from(DataSource.database(friendDatabase))
//                                    .where(Expression.property("friendUUID").equalTo(Expression.string(friendUUID)));
//                            ResultSet rs = query.execute();
//                            docID = rs.allResults().get(0).getString("docID");
//                            MutableDocument friendDoc = friendDatabase.getDocument(docID).toMutable();
//                            PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
//                            String ago = prettyTime.format(new Date(String.valueOf(Calendar.getInstance().getTime())));
//                            friendDoc.setString("time", ago);
//                            friendDatabase.save(friendDoc);
//                        } catch (CouchbaseLiteException e) {
//                            e.printStackTrace();
//                        }
//                    } catch (CouchbaseLiteException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//            return true;
//        }
//    });
//
//    private String createJSONMeta(String uuid, String username) throws JSONException {
//        try {
//            JSONObject meta = new JSONObject();
//            meta.put("type", "meta");
//            meta.put("UUID", uuid);
//            meta.put("username", username);
//            Log.d("Chat Activity", "meta: " + meta.toString());
//            return meta.toString();
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    private String createJSONChat(String msg, String lat, String lon) throws JSONException {
//        try {
//            JSONObject chat = new JSONObject();
//            chat.put("type", "chat");
//            chat.put("lat", lat);
//            chat.put("lon", lon);
//            return chat.toString();
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    public WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
//        @Override
//        public void onConnectionInfoAvailable(WifiP2pInfo info) {
//            final InetAddress groupOwnerAddress = info.groupOwnerAddress;
//
//            if (info.groupFormed && info.isGroupOwner) {
//                Toast.makeText(getApplicationContext(), "Host", Toast.LENGTH_SHORT).show();
//                serverClass = new ServerClass();
//                serverClass.start();
//                // TODO: Send data about myself to the client
//
//
//            } else if (info.groupFormed && !info.isGroupOwner) {
//                Log.d("Chat Activity", "goAddress populated");
//                goAddress = groupOwnerAddress;
//                clientClass = new ClientClass(goAddress);
//                clientClass.start();
//
//            }
//        }
//    };
//
//    private class SendReceive extends Thread {
//
//        private Socket socket;
//        private InputStream inputStream;
//        private OutputStream outputStream;
//
//        public SendReceive(Socket skt) {
//            socket = skt;
//            try {
//                inputStream = socket.getInputStream();
//                outputStream = socket.getOutputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void run() {
//            byte[] buffer = new byte[1024];
//            int bytes;
//
//            while (socket != null) {
//                try {
//                    bytes = inputStream.read(buffer);
//
//                    if (bytes > 0) {
//                        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        public void write(byte[] bytes) {
//            try {
//                outputStream.write(bytes);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public class ServerClass extends Thread {
//        Socket socket;
//        ServerSocket serverSocket;
//
//        @Override
//        public void run() {
//            try {
//                serverSocket = new ServerSocket(8888);
//                socket = serverSocket.accept();
//                sendReceive = new SendReceive(socket);
//                sendReceive.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public class ClientClass extends Thread {
//
//        Socket socket;
//        String hostAdd;
//
//        public ClientClass(InetAddress hostAddress) {
//            hostAdd = hostAddress.getHostAddress();
//            socket = new Socket();
//        }
//
//        @Override
//        public void run() {
//            try {
//                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
//                sendReceive = new SendReceive(socket);
//                sendReceive.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerReceiver(mReceiver, mIntentFilter);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(mReceiver);
//    }
//
//    private void loadMessage(Database friendChat) throws CouchbaseLiteException {
//        Query query = QueryBuilder.select(SelectResult.property("index"))
//                .from(DataSource.database(friendChat))
//                .where(Expression.property("index"));
//        ResultSet rs = query.execute();
//        int size = rs.allResults().size();
//
//        //get the data and append to a list
//        ArrayList<String> listData = new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            query = QueryBuilder.select(SelectResult.property("model"))
//                    .from(DataSource.database(chatDatabase))
//                    .where(Expression.property("index").equalTo(Expression.property("index").value(i)));
//            rs = query.execute();
//            ChatModel model = (ChatModel)rs.allResults().get(0).getValue("model");
//            adapter = new CustomAdapter(getApplicationContext(), msgList);
//            messageList.setAdapter(adapter);
//            msgList.add(model);
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//
//}