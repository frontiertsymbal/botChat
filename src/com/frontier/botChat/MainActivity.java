package com.frontier.botChat;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.frontier.botChat.utils.Const;
import com.frontier.botChat.utils.GetAnecdote;
import com.frontier.botChat.utils.GetWeather;
import com.frontier.botChat.utils.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private final List<User> userList = new ArrayList<>();

    private ListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        EditText editText = (EditText) findViewById(R.id.message);

        ListView chat = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter(MainActivity.this, userList);
        chat.setAdapter(adapter);

        String sysMessage = "-=System message=-\nEnter \"? Currency\" to know the exchange rate in PrivatBank\n" +
                "Enter \"? Anecdote\" bot to show you a random anecdote.\n" +
                "Enter \"? Weather\" to see the actual weather.";
        userList.add(new User(Const.TYPE_SYSTEM, sysMessage));

        ImageButton sendButton = (ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() != 0) {
                    userList.add(new User(Const.TYPE_USER, editText.getText().toString()));
                }
                String message = editText.getText().toString();
                if (message.equals("? Anecdote") || message.equals("? Weather") || message.equals("? Currency")) {
                    if (isOnline()) {
                        new AsyncTask<Void, Void, User>() {

                            @Override
                            protected User doInBackground(Void... params) {
                                if (message.equals("? Anecdote")) {
                                    String anecdote = GetAnecdote.getAnecdote();
                                    return new User(Const.TYPE_BOT, anecdote);
                                }
                                if (message.equals("? Weather")) {
                                    GetWeather getWeather = new GetWeather();
                                    String weather = getWeather.getMessage();
                                    String id = getWeather.getId();
                                    return new User(Const.TYPE_WEATHER, weather, id);
                                }
                                if (message.equals("? Currency")) {
                                    return null;
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(User user) {
                                userList.add(user);
                                adapter.notifyDataSetChanged();
                            }
                        }.execute();
                    } else {
                        Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }
                }
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
