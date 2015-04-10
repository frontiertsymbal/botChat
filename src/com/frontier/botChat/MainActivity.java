package com.frontier.botChat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.frontier.botChat.utils.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView chat = (ListView) findViewById(R.id.listView);
        List<User> userList = new ArrayList<>();

        userList = getUsers();

        ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, userList);
        chat.setAdapter(adapter);
    }

    private List<User> getUsers() {
        List<User> list = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 20; i++) {
            int r = rand.nextInt(3);
            list.add(new User(r, "message from user " + r + " on " + i + " lap."));
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
