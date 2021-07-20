package sg.edu.rp.c346.id20004713.ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "songs.db";
    private static final int DATABASE_VERSION = 5;
    private static final String TABLE_NAME = "songs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARs = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, " + COLUMN_SINGERS + " TEXT, "
                + COLUMN_YEAR + " INTEGER, " + COLUMN_STARs + " INTEGER)";

        db.execSQL(createNoteTableSql);

        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, "Dummy " + i);
            values.put(COLUMN_SINGERS, "Dummy " + i);
            values.put(COLUMN_YEAR, 2012);
            values.put(COLUMN_STARs, 4);
            db.insert(TABLE_NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create table(s) again
        onCreate(db);
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_TITLE + ", " + COLUMN_SINGERS + ", " + COLUMN_YEAR + ", "
                + COLUMN_STARs
                + " FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);

                Song song = new Song(title, singer, year, star);
                song.set_id(id);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getAllSongsByYear(String yearToFind) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STARs};
        String condition = COLUMN_YEAR + " = ?";
        String[] args = {yearToFind};
        Cursor cursor = db.query(TABLE_NAME, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);

                Song song = new Song(title, singer, year, star);
                song.set_id(id);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public long insertSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_SINGERS, song.getSingers());
        values.put(COLUMN_YEAR, song.getYear());
        values.put(COLUMN_STARs, song.getStars());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGERS, data.getSingers());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STARs, data.getStars());

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};

        int result = db.update(TABLE_NAME, values, condition, args);
        db.close();

        return result;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NAME, condition, args);
        db.close();

        return result;
    }

    public ArrayList<Year> getDistinctYear(){
        ArrayList<Year> al = new ArrayList<Year>();

        String selectQuery = "SELECT DISTINCT " + COLUMN_YEAR
                + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_YEAR + " ASC";

        al.add(new Year(""));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String year = cursor.getString(0);
                al.add(new Year(year));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return al;
    }
}
