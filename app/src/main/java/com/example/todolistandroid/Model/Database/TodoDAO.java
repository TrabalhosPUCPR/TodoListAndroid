package com.example.todolistandroid.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolistandroid.Model.ConnectionResponse;
import com.example.todolistandroid.Model.Todo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO extends SQLiteOpenHelper implements DAO<Todo>{

    private static final int DB_VERSION = 1;

    private static final String DB_TABLE_TODOS = "todos";
    private static final String TODO_COL_ID = "id";
    private static final String TODO_COL_NAME = "name";
    private static final String TODO_COL_DESCRIPTION = "description";
    private static final String TODO_COL_DATE = "date";
    private static final String TODO_COL_TIME = "time";
    private static final String TODO_COL_TYPE = "type";

    private static final String DB_TABLE_TYPES = "types";
    private static final String TYPES_COL_ID = "id";
    private static final String TYPES_COL_DESC = "desc";

    public static final List<String> types = new ArrayList<>();

    private final Context context;

    public TodoDAO(@NotNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public boolean add(Todo todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(TODO_COL_NAME, todo.getName());
        value.put(TODO_COL_DESCRIPTION, todo.getDescription());
        value.put(TODO_COL_DATE, todo.getDate());
        value.put(TODO_COL_TIME, todo.getTime());
        value.put(TODO_COL_TYPE, todo.getTypeId());

        long id = db.insert(DB_TABLE_TODOS, null, value);
        todo.setId((int) id);

        db.close();

        return id > 0;
    }

    @Override
    public boolean remove(int id) {

        SQLiteDatabase db = getWritableDatabase();

        int count = db.delete(DB_TABLE_TODOS, "id=" + id, null);

        db.close();

        return count > 0;
    }

    @Override
    public boolean clearAll() {
        SQLiteDatabase db = getWritableDatabase();

        int count = db.delete(DB_TABLE_TODOS, null, null);

        db.close();

        return count > 0;
    }

    public boolean edit(Todo todo, int id) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(TODO_COL_NAME, todo.getName());
        cv.put(TODO_COL_DESCRIPTION, todo.getDescription());
        cv.put(TODO_COL_DATE, todo.getDate());
        cv.put(TODO_COL_TIME, todo.getTime());
        cv.put(TODO_COL_TYPE, todo.getTypeId());

        int count = db.update(DB_TABLE_TODOS, cv, "id="+id, null);

        db.close();

        return count > 0;
    }

    @Override
    public Todo get(int id) {

        SQLiteDatabase db = getReadableDatabase();
        Todo todo = new Todo();

        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_TODOS + " WHERE "+ TODO_COL_ID + "=" + id + ";", null);
        if(c.moveToFirst()){
            todo.setName(c.getString(0));
            todo.setDescription(c.getString(1));
            todo.setDate(c.getString(2));
            todo.setTime(c.getString(3));
            todo.setTypeId(c.getInt(4));
        }
        db.close();
        return todo;
    }

    @Override
    public List<Todo> getList() {

        ArrayList<Todo> array = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_TODOS, null);
        if(c.moveToFirst()){
            do{
                array.add(new Todo(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5)));
            } while(c.moveToNext());
        }
        db.close();
        return array;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_TODOS + " (";
        createTableSQL += TODO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        createTableSQL += TODO_COL_NAME + " TEXT NOT NULL,";
        createTableSQL += TODO_COL_DESCRIPTION + " TEXT,";
        createTableSQL += TODO_COL_DATE + " TEXT,";
        createTableSQL += TODO_COL_TIME + " TEXT,";
        createTableSQL += TODO_COL_TYPE + " INTEGER);";

        sqLiteDatabase.execSQL(createTableSQL);

        createTableSQL = "CREATE TABLE IF NOT EXISTS" + DB_TABLE_TYPES + " (";
        createTableSQL += TYPES_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT";
        createTableSQL += TYPES_COL_DESC + "TEXT NOT NULL);";

        sqLiteDatabase.execSQL(createTableSQL);

        loadTypesJSON();
    }

    private void loadTypesJSON(){
        try {
            InputStream inputStream = context.getAssets().open("options.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonStr = new String(buffer, StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonStr);
            JSONArray types = json.getJSONArray("options");

            SQLiteDatabase db = getWritableDatabase();

            for (int i=0; i < types.length(); i++) {
                ContentValues value = new ContentValues();

                JSONObject typeObj = types.getJSONObject(i);
                String type = typeObj.getString("type");

                TodoDAO.types.add(type);
                value.put(TYPES_COL_DESC, type);
                db.insert(DB_TABLE_TYPES, null, value);
            }
            db.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int currentVersion, int newVersion) {

    }

    ConnectionResponse getRequestResponse(String endPoint, String method, String parameters){
        StringBuilder jsonStr = new StringBuilder();
        int errorCode = 0;
        String message = "";
        HttpURLConnection connection;

        try {
            URL url = new URL(endPoint);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);

            if(parameters != null){
                connection.setDoOutput(true);
                connection.connect();
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
                writer.write(parameters);
                writer.flush();
                writer.close();
            }else{
                connection.connect();
            }

            int responseCode = connection.getResponseCode();
            if(responseCode < 200){
                message = "Error: internal server error";
            } else if(responseCode <= 299){
                InputStream inputStream = connection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);

                String line = reader.readLine();
                while(line != null){
                    jsonStr.append(line);
                    line = reader.readLine();
                }
                reader.close();
                streamReader.close();
                inputStream.close();
                connection.disconnect();
            }else if(responseCode <= 499){
                message = "Error: Unexpected error has occurred";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ConnectionResponse(jsonStr.toString(), errorCode, message);
    }
}
