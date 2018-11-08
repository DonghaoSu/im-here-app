package com.example.user.teamproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.couchbase.lite.Blob;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView HomeImage;
    TextView HomeName, HomeUsername;
    Blob myBlob;
    byte[] imageInByte;
    String myUsername, myName, myDeviceId, friendExtension;
    private ListView users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //navigation drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                /*
                Intent intent = new Intent(HomeActivity.this, SearchList.class);
                intent.putExtra("Extension", extensionCol);

                intent.putExtra("ProfileImage", imageInByte);
                intent.putExtra("Name", nameCol);
                intent.putExtra("Username", usernameCol);
                startActivity(intent);
                */
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //user's information from previous page
        Intent intent = getIntent();
        imageInByte = intent.getByteArrayExtra("ProfileImage");
        myBlob = new Blob("image/*", imageInByte);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);

        myUsername = intent.getStringExtra("Username");
        myName = intent.getStringExtra("Name");
        myDeviceId = intent.getStringExtra("DeviceId");

        //navigation header read user info
        HomeImage = navigationView.getHeaderView(0).findViewById(R.id.NavHeaderImageView);
        HomeImage.setImageBitmap(bitmap);

        HomeName = navigationView.getHeaderView(0).findViewById(R.id.NavHeaderName);
        HomeName.setText(intent.getStringExtra("Name"));

        HomeUsername = navigationView.getHeaderView(0).findViewById(R.id.NavHeaderUsername);
        HomeUsername.setText(intent.getStringExtra("Username"));

        try {
            // Get the database (and create it if it doesn’t exist).
            DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
            Database friendDatabase = new Database("friendList", config);

            /*
            * //list all friend
            *
            * Query query = QueryBuilder.select(SelectResult.property("friendExtension"))
                                .from(DataSource.database(friendDatabase))
                                .where(Expression.property("myExtension").equalTo(Expression.string(myExtension)));
            * rs = query.execute();
            * int i = 0;
            * String referChatRoom;
            * while( i < rs.allResults().size()){
            *   rs = query.execute();
            *   referChatRoom = rs.allResults().get(i).getString("referChatRoom");
            *   ...
            * }
            *
            * //onclick any friend
            *
            * get(referChatRoom);
            *
            * start an intent to new activity according to ref of chatroom
            * Intent intent2 = new Intent(HomeActivity.this, ChatRoom.class);
            * intent2.putExtra("ReferChatRoom", referChatRoom);
            * startActivity(intent2);
            *
            *
             */
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        //view users' list purpose, will delete
        users = findViewById(R.id.listView);
        populateListView();

    }

    //view user purpose, will delete
    private void populateListView() {
        //get the data and append to a list
        ArrayList<String> listData = new ArrayList<>();

        //get the value from the data in column, then add it to the ArrayList
        listData.add(myUsername);
        listData.add(myName);
        listData.add(myDeviceId);

        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        users.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.action_settings) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(HomeActivity.this, LoginPageActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
