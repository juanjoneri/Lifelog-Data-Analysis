package com.apps.juan.myanalytics;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

public class MainActivity extends Activity{

    final static private String APP_KEY = "q3k6tm4kys6z2wb";
    final static private String APP_SECRET = "l8nyd81rck6hj5m";
    final static private String DROPBOX_KEY = "key_dropbox_8877";
    String accessToken;

    private static final String BACKUP_VERSION_KEY = "key_version_backup_9362";
    private static final String VERSION_KEY = "key_version_9242";
    boolean firstTime;
    int backupVersion;

    private static final String PRESSED_CARD_INTENT_KEY = "pressed_key_0033";

    String action, details;
    int price;

    //Databases declaration
    DatabaseOpenHelper dbOpenHelper;
    Preferences preferences;
    DropboxAPI<AndroidAuthSession> mDBApi;
    SqlCommandHelper mainCommandHelper;

    //Others
    String[] cardsTitles = {"Food & Drink", "Life", "Sports", "School", "Shop", "Transport", "Events", "Health"};
    int[] prices = {0,0,0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalizeUi();

        preferences = new Preferences(this);

        if (preferences.getInt(VERSION_KEY,0) == 0){
            preferences.putInt(VERSION_KEY, 1);
            firstTime = true;
            Toast toast = Toast.makeText(MainActivity.this, "Please link your Dropbox account", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            firstTime = false;
        }


        dbOpenHelper = new DatabaseOpenHelper(this);
        mainCommandHelper = new SqlCommandHelper("''", "''",0);

        //Initialize dropbox connection
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        if(firstTime){
            mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
        }
        else {
            accessToken = preferences.getString(DROPBOX_KEY,null);
            session.setOAuth2AccessToken(accessToken);
            mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        }

    }

    private void initalizeUi() {

        ColorsHelper colorsHelper = new ColorsHelper(this);
        ImageHelper imageHelper = new ImageHelper();
        int[] imagesArray = imageHelper.getMainImages();
        int[] colorsArray = colorsHelper.getMainScreenColors();

        ViewGroup containerLeft = (ViewGroup)findViewById(R.id.row_one);
        ViewGroup containerRight = (ViewGroup)findViewById(R.id.row_two);
        ViewGroup[] containers = {containerLeft, containerRight};

        CardsAdapter cardsAdapter = new CardsAdapter(this,containers,cardsTitles,colorsArray,imagesArray,prices,cardClickedListener);
        cardsAdapter.fillContainer(200);

    }

    View.OnClickListener cardClickedListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Transition exitTrans = new Fade();
            getWindow().setExitTransition(exitTrans);

            Transition reenterTrans = new Fade();
            getWindow().setReenterTransition(reenterTrans);

            v.findViewById(R.id.card_background).setTransitionName("card_transition_background");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( MainActivity.this,
                    Pair.create(v.findViewById(R.id.card_background), "card_transition_background"));

            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra(PRESSED_CARD_INTENT_KEY, cardsTitles[v.getId()]);
            startActivity(intent, options.toBundle());

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();

                accessToken = mDBApi.getSession().getOAuth2AccessToken();
                preferences.putString(DROPBOX_KEY,accessToken);

            } catch (IllegalStateException e) {
                Toast toast = Toast.makeText(MainActivity.this, "Could not authenticate", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_backup) {

            backupVersion = preferences.getInt(BACKUP_VERSION_KEY,0) + 1;

            new backgroundDropboxUpload().execute(String.valueOf(backupVersion));
            return true;
        }


        else if (id == R.id.action_log){

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_custom_log);
            dialog.setTitle("Custom log");
            dialog.setCancelable(true);
            dialog.show();

            Button addLog = (Button) dialog.findViewById(R.id.button_positive);
            addLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    action = ((EditText)dialog.findViewById(R.id.actionField)).getText().toString();
                    details = ((EditText)dialog.findViewById(R.id.detailsField)).getText().toString();
                    String value = ((EditText)dialog.findViewById(R.id.priceField)).getText().toString();
                    if(!value.equals("")){
                        price = Integer.parseInt(value);
                    }

                    String insertCommand = mainCommandHelper.insertSet(action, details, price);
                    dbOpenHelper.getWritableDatabase().execSQL(insertCommand);

                    Toast.makeText(MainActivity.this, "Logged", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }
            });

            Button cancelLog = (Button) dialog.findViewById(R.id.button_negative);
            cancelLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }


    private class backgroundDropboxUpload extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            return dbOpenHelper.backUpToDropbox(mDBApi,params[0]);
        }

        @Override
        protected void onPostExecute(String string) {
            //Update the value of the current version
            if (string.contains("Backed")){
                preferences.putInt(BACKUP_VERSION_KEY,backupVersion);
            }

            Toast toast = Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}

