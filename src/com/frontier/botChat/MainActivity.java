package com.frontier.botChat;

import android.app.ListActivity;
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
import com.frontier.botChat.utils.crashReporter.CrashReporter;
import com.frontier.botChat.utils.crashReporter.Feedback;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends ListActivity {

    private List<User> userList = new ArrayList<>();
    private ListViewAdapter adapter;
    private SQLiteDatabase db;

    private String anecdote = "anecdote";
    private String weather = "weather";
    private String currency = "currency";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy");
    private Calendar cal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        EditText editText = (EditText) findViewById(R.id.message);

        ListView chat = getListView();
        adapter = new ListViewAdapter(MainActivity.this, userList);
        chat.setAdapter(adapter);

        CrashReporter crashReporter = new CrashReporter(this, "Error: " + getPackageName()
                + " (" + Feedback.getPackageVersion(getApplicationContext()) + ")");
        Thread.setDefaultUncaughtExceptionHandler(crashReporter);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(chat, new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            String[] deleteItemTime = new String[1];
                            deleteItemTime[0] = adapter.getItem(position).getChatTime();
                            adapter.remove(adapter.getItem(position));
                            db.delete("chat", "chatTime = ?", deleteItemTime);
                            Toast.makeText(MainActivity.this, "Message deleted.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        chat.setOnTouchListener(touchListener);
        chat.setOnScrollListener(touchListener.makeScrollListener());

        UserDataBase userDataBase = new UserDataBase(MainActivity.this);
        db = userDataBase.getWritableDatabase();
        Cursor cursor = db.query("chat", null, null, null, null, null, null);
        List<User> dBList;
        dBList = CursorMapper.create(User.class).map(cursor);
        if (dBList.size() != 0) {
            userList.addAll(dBList);
            Log.i(Const.LOG_TAG, "Size of userList " + userList.size());
        }

        String sysMessage = "Enter \"" + currency + "\" to " +
                "know the exchange rate in PrivatBank\n" +
                "Enter \"" + anecdote + "\" bot to show you a random anecdote.\n" +
                "Enter \"" + weather + "\" to see the actual weather.";
        userList.add(new User(Const.TYPE_SYSTEM, sysMessage, getTimeString()));

        adapter.notifyDataSetChanged();

        ImageButton sendButton = (ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() != 0) {
                    userList.add(new User(Const.TYPE_USER, editText.getText().toString(), getTimeString()));
                    addToDb(Const.TYPE_USER, editText.getText().toString(), "", userList.get(userList.size() - 1).getChatTime());
                }

                String message = editText.getText().toString();
                if (message.toLowerCase().contains(anecdote) || message.toLowerCase().contains(weather)
                        || message.toLowerCase().contains(currency)) {
                    if (isOnline()) {
                        new AsyncTask<Void, Void, User>() {
                            @Override
                            protected User doInBackground(Void... params) {
                                if (message.toLowerCase().contains(anecdote)) {
                                    return new User(Const.TYPE_BOT, GetAnecdote.getAnecdote(), getTimeString());
                                }
                                if (message.toLowerCase().contains(weather)) {
                                    GetWeather getWeather = new GetWeather();
                                    return new User(Const.TYPE_WEATHER, getWeather.getMessage(), getWeather.getId(), getTimeString());
                                }
                                if (message.toLowerCase().contains(currency)) {
                                    return new User(Const.TYPE_SYSTEM, GetCurrency.getCurrency(), getTimeString());
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(User user) {
                                addToDb(user.getType(), user.getMessage(), user.getImageId(), user.getChatTime());
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

    private void addToDb(int type, String message, String imageId, String time) {
        ContentValues cv = new ContentValues();
        cv.put("type", type);
        cv.put("message", message);
        cv.put("chatTime", time);
        if (type == Const.TYPE_WEATHER) {
            cv.put("imageId", imageId);
        } else {
            cv.put("imageId", "");
        }
        db.insert("chat", null, cv);
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
            userList.add(new User(Const.TYPE_SYSTEM, "History cleared", getTimeString())); //this user (message) no add to db
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    private String getTimeString() {
        cal = GregorianCalendar.getInstance(Locale.UK);
        return dateFormat.format(cal.getTime());
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
