package com.frontier.botChat;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.frontier.botChat.utils.GetAnecdote;
import com.frontier.botChat.utils.GetWeather;
import com.frontier.botChat.utils.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private final int TYPE_USER = 1;
    private final int TYPE_BOT = 2;
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

        userList.add(new User(0, "-=System message=-\nEnter \"? Currencies\" to know the exchange rate in PrivatBank\n" +
                "Enter \"? Anecdote\" bot to show you a random anecdote.\n" +
                "Enter \"? Weather\" to see the actual weather."));

        ImageButton sendButton = (ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.add(new User(TYPE_USER, editText.getText().toString()));
                if (editText.getText().toString().equals("? Anecdote")) {
                    Log.i("MyTag", "Anecdote");
                    new AsyncTask<Void, Void, User>() {

                        @Override
                        protected User doInBackground(Void... params) {
                            String anecdote = GetAnecdote.getAnecdote();
                            return new User(TYPE_BOT, anecdote);
                        }

                        @Override
                        protected void onPostExecute(User user) {
                            userList.add(user);
                            adapter.notifyDataSetChanged();
                        }
                    }.execute();
                }
                if (editText.getText().toString().equals("? Weather")) {
                    Log.i("MyTag", "Weather");
                    new AsyncTask<Void, Void, User>() {

                        @Override
                        protected User doInBackground(Void... params) {
                            String weather = GetWeather.getWeather();
                            return new User(TYPE_BOT, weather);
                        }

                        @Override
                        protected void onPostExecute(User user) {
                            userList.add(user);
                            adapter.notifyDataSetChanged();
                        }
                    }.execute();
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
        return cm.getActiveNetworkInfo() == null;
    }

}
