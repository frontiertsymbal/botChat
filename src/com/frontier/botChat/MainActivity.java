package com.frontier.botChat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.frontier.botChat.mapper.CursorMapper;
import com.frontier.botChat.utils.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<User> userList = new ArrayList<>();
    private ListViewAdapter adapter;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        EditText editText = (EditText) findViewById(R.id.message);

        ListView chat = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter(MainActivity.this, userList);
        chat.setAdapter(adapter);

        UserDataBase userDataBase = new UserDataBase(MainActivity.this);
        db = userDataBase.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.query("chat", null, null, null, null, null, null);
        List<User> dBList;
        dBList = CursorMapper.create(User.class).map(cursor);
        if (dBList.size() != 0) {
            userList.addAll(dBList);
            Log.i(Const.LOG_TAG, "Size of userList " + userList.size());
        }

        String sysMessage = "Enter \"currency\" to " +
                "know the exchange rate in PrivatBank\n" +
                "Enter \"anecdote\" bot to show you a random anecdote.\n" +
                "Enter \"weather\" to see the actual weather.";
        userList.add(new User(Const.TYPE_SYSTEM, sysMessage));

        adapter.notifyDataSetChanged();

        ImageButton sendButton = (ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() != 0) {
                    userList.add(new User(Const.TYPE_USER, editText.getText().toString()));
                    cv.put("type", Const.TYPE_USER);
                    cv.put("message", editText.getText().toString());
                    cv.put("imageId", "");
                    db.insert("chat", null, cv);
                }

                String message = editText.getText().toString();
                if (message.toLowerCase().contains("anecdote") || message.toLowerCase().contains("weather")
                        || message.toLowerCase().contains("currency")) {
                    if (isOnline()) {
                        new AsyncTask<Void, Void, User>() {
                            @Override
                            protected User doInBackground(Void... params) {
                                if (message.toLowerCase().contains("anecdote")) {
                                    return new User(Const.TYPE_BOT, GetAnecdote.getAnecdote());
                                }
                                if (message.toLowerCase().contains("weather")) {
                                    GetWeather getWeather = new GetWeather();
                                    return new User(Const.TYPE_WEATHER, getWeather.getMessage(), getWeather.getId());
                                }
                                if (message.toLowerCase().contains("currency")) {
                                    return new User(Const.TYPE_SYSTEM, GetCurrency.getCurrency());
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(User user) {
                                cv.put("type", user.getType());
                                cv.put("message", user.getMessage());
                                if (user.getType() == Const.TYPE_WEATHER) {
                                    cv.put("imageId", user.getImageId());
                                } else {
                                    cv.put("imageId", "");
                                }
                                db.insert("chat", null, cv);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO menu items
        if (item.getItemId() == R.id.options) {
            db.delete("chat", null, null);
            userList.clear();
            Log.i(Const.LOG_TAG, "History cleared");
            userList.add(new User(Const.TYPE_SYSTEM, "History cleared")); //this user (message) no add to db
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
