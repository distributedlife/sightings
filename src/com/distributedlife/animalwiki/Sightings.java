package com.distributedlife.animalwiki;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.distributedlife.animalwiki.model.Sighting;
import org.apache.commons.lang3.StringUtils;

public class Sightings extends SQLiteOpenHelper {
    private static final String NAME = "sightings";
    private static final String ID = "id" ;
    private static final String WHAT = "what" ;
//    private static final String WHEN = "timestamp" ;
//    private static final String WHERE = "location" ;
//    private static final String TYPE = "type" ;
//    private static final String PHOTO = "photo";
    private static final String LAST_UPDATED = "last_updated";
    public static final String TIMESTAMP_PATTERN = "YYYY-MM-DD HH:mm:ss.SSSZ";
    private static final int VERSION = 1;
    private static String[] CREATE_COLUMNS = new String[] {
        "id INTEGER PRIMARY KEY AUTOINCREMENT",
        "what TEXT NOT NULL"
    };
    private static final String[] INSERT_COLUMNS = new String[] {WHAT};
    private static final String[] ALL_COLUMNS = new String[] {ID, WHAT};

    public Sightings(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void add(Sighting sighting) {
        getWritableDatabase().execSQL(
                insert(sighting.getWhat())
        );
    }

//    @Override
//    public void update(Sighting sighting) {
//        DateTimeParser parser = DateTimeFormat.forPattern(TIMESTAMP_PATTERN).getParser();
//        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();
//
//        String sql = String.format("UPDATE %s SET what = '%s', WHERE id = %d;",
//                NAME,
//                sighting.getWhat()
//        );
//        getWritableDatabase().execSQL(sql);
//    }

//    private java.util.AnimalList<Sighting> getAll() {
//        java.util.AnimalList<Sighting> sightings = new ArrayList<Sighting>();
//
//        Cursor cursor = getReadableDatabase().query(NAME, ALL_COLUMNS, null, null, null, null, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            sightings.add(mapToSighting(cursor));
//
//            cursor.moveToNext();
//        }
//
//        cursor.close();
//        return sightings;
//    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}

    private static String create() {
        return "CREATE TABLE" + " " + NAME + " " + "(" + StringUtils.join(CREATE_COLUMNS, ", ") + ")" + ";";
    }

    private String insert(String what) {
//        DateTimeParser parser = DateTimeFormat.forPattern(TIMESTAMP_PATTERN).getParser();
//        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();

        String columns = StringUtils.join(INSERT_COLUMNS, ", ");

        return String.format("INSERT INTO %s (%s) VALUES ('%s');", NAME, columns, tidy(what));
    }

    private String tidy(String text) {
        return text.replaceAll("'", "%27");
    }

    public boolean hasSighting(String what) {
        //TODO: use selection arguments
        Cursor cursor = getReadableDatabase().query(NAME, ALL_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sighting sighting = mapToSighting(cursor);

            if (sighting.getWhat().equals(tidy(what))) {
                return true;
            }

            cursor.moveToNext();
        }

        cursor.close();

        return false;
    }

    private Sighting mapToSighting(Cursor cursor) {
//        DateTimeParser parser = DateTimeFormat.forPattern(TIMESTAMP_PATTERN).getParser();
//        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();

        return new Sighting(
                cursor.getInt(0),
                cursor.getString(1)
//                catalogue.find(cursor.getInt(1)),
//                DateTime.parse(cursor.getString(2), formatter),
//                cursor.getInt(3),
//                cursor.getInt(4)
        );
    }

    public void remove(Sighting sighting) {
        getWritableDatabase().delete(
                NAME,
                "what = ?",
                new String[] {tidy(sighting.getWhat())}
        );
    }
}
