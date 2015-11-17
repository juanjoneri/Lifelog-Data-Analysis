package com.apps.juan.myanalytics;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailsActivity extends Activity {

    private static final String PRESSED_CARD_INTENT_KEY = "pressed_key_0033";
    String whichAction;
    int actionIndex;

    //Data Base
    SqlCommandHelper commandHelper;
    DatabaseOpenHelper dbOpenHelper;

    //UI
    FrameLayout backColorFrame;
    ViewGroup containerLeft, containerRight, containerCenter;
    ImageView detailsImage;

    //Helper Classes
    ColorsHelper colorsHelper;
    ImageHelper imageHelper;
    TitlesHelper titlesHelper;
    PriceHelper priceHelper;
    int[] mainImages, mainColors;
    String[] mainCardsTitles;
    CardsAdapter cardsAdapter;

    int price;
    String details;


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        whichAction = intent.getStringExtra(PRESSED_CARD_INTENT_KEY);
        actionIndex = Arrays.asList(mainCardsTitles).indexOf(whichAction);

        backColorFrame.setBackgroundColor(mainColors[actionIndex]);
        getActionBar().setBackgroundDrawable(new ColorDrawable(mainColors[actionIndex]));
        detailsImage.setImageResource(mainImages[actionIndex]);

        containerLeft = (ViewGroup)findViewById(R.id.details_row_one);
        containerCenter = (ViewGroup)findViewById(R.id.details_row_two);
        containerRight = (ViewGroup)findViewById(R.id.details_row_three);
        ViewGroup[] containers = {containerLeft, containerCenter, containerRight};

        cardsAdapter = new CardsAdapter(this,containers,titlesHelper.getTitles(actionIndex), colorsHelper.getColors(actionIndex), imageHelper.getImages(actionIndex), priceHelper.getPrices(actionIndex), detailsCardClickListener);

        Transition enterTrans = new Fade();
        getWindow().setEnterTransition(enterTrans);

        enterTrans.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

                cardsAdapter.emptyContainer();

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                cardsAdapter.fillContainer(150);

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

        Transition returnTrans = new Fade();
        getWindow().setReturnTransition(returnTrans);

        returnTrans.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Animation fadeOut = AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.card_fade_out);
                findViewById(R.id.card_itself).startAnimation(fadeOut);
            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    View.OnClickListener detailsCardClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            //v.getId() to get card index

            int price = priceHelper.getPrices(actionIndex)[v.getId()];
            String details = titlesHelper.getTitles(actionIndex)[v.getId()];

            if (price == -1){

                final Dialog dialog = new Dialog(DetailsActivity.this);
                dialog.setContentView(R.layout.dialog_custom_price_log);
                dialog.setTitle(details + " price");
                dialog.setCancelable(true);
                dialog.show();

                Button addLog = (Button) dialog.findViewById(R.id.button_positive);
                addLog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = ((EditText) dialog.findViewById(R.id.priceField)).getText().toString();

                        int price = 0;

                        if (!value.equals("")) {
                            price = Integer.parseInt(value);
                        }

                        String insertCommand = commandHelper.insertSet(mainCardsTitles[actionIndex], titlesHelper.getTitles(actionIndex)[v.getId()], price);
                        dbOpenHelper.getWritableDatabase().execSQL(insertCommand);
                        Toast.makeText(DetailsActivity.this, "Logged", Toast.LENGTH_SHORT).show();

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

            else {
                String insertCommand = commandHelper.insertSet(mainCardsTitles[actionIndex], details, price);
                dbOpenHelper.getWritableDatabase().execSQL(insertCommand);
                Toast.makeText(DetailsActivity.this, "Logged", Toast.LENGTH_SHORT).show();
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_details);

        colorsHelper = new ColorsHelper(this);
        imageHelper = new ImageHelper();
        titlesHelper = new TitlesHelper();
        priceHelper = new PriceHelper();
        mainImages = imageHelper.getMainImages();
        mainColors = colorsHelper.getMainScreenColors();
        mainCardsTitles = titlesHelper.getMainTitles();

        backColorFrame = (FrameLayout) findViewById(R.id.details_background);
        detailsImage = (ImageView) findViewById(R.id.details_image);

        dbOpenHelper = new DatabaseOpenHelper(this);
        commandHelper = new SqlCommandHelper("''", "''",0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finishAfterTransition();
            super.onBackPressed();
        }

        else if (id == R.id.action_log){
            final Dialog dialog = new Dialog(DetailsActivity.this);
            dialog.setContentView(R.layout.dialog_custom_details_log);
            dialog.setTitle(mainCardsTitles[actionIndex] + " Log");
            dialog.setCancelable(true);
            dialog.show();

            Button addLog = (Button) dialog.findViewById(R.id.button_positive);
            addLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value = ((EditText)dialog.findViewById(R.id.priceField)).getText().toString();
                    details = ((EditText)dialog.findViewById(R.id.detailsField)).getText().toString();

                    if(!value.equals("")){
                        price = Integer.parseInt(value);
                    }

                    String insertCommand = commandHelper.insertSet(mainCardsTitles[actionIndex], details, price);
                    dbOpenHelper.getWritableDatabase().execSQL(insertCommand);

                    Toast.makeText(DetailsActivity.this, "Logged", Toast.LENGTH_SHORT).show();

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

}
