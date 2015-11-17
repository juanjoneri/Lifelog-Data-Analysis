package com.apps.juan.myanalytics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "life_log.db";
    private static final String TABLE_NAME = "Life";

    private static final String TABLE_ROW_ONE = "Action";
    private static final String TABLE_ROW_TWO = "Details";
    private static final String TABLE_ROW_THREE = "Price";

    Context cont;

    private static final String CREATE_COMMAND =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Date" + " TEXT default CURRENT_TIMESTAMP, " +
                    TABLE_ROW_ONE + " TEXT, " +
                    TABLE_ROW_TWO + " TEXT, " +
                    TABLE_ROW_THREE + " INT);";

    public DatabaseOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    public void backUpToMemory(String version) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.apps.juan.myanalytics" + "//databases//" + DATABASE_NAME;
                String backupDBPath = version + "_" + DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast toast = Toast.makeText(cont, "Back up " + version, Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception e) {
            Toast.makeText(cont, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public String backUpToDropbox(DropboxAPI<AndroidAuthSession> mDBApi, String version){
        try {
            File data = Environment.getDataDirectory();

            String currentDBPath = "//data//" + "com.apps.juan.myanalytics" + "//databases//" + DATABASE_NAME;
            File currentDB = new File(data, currentDBPath);
            FileChannel src = new FileInputStream(currentDB).getChannel();

            mDBApi.putFile(version + "_" + DATABASE_NAME, new FileInputStream(currentDB),
                    src.size(), null, null);

            src.close();

        } catch (Exception e) {
            return e.toString();
        }
        return "Version " + version + " Backed up";
    }


}
