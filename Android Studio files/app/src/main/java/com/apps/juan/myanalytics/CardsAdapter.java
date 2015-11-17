package com.apps.juan.myanalytics;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class CardsAdapter {

    ViewGroup[] containers;
    Context context;
    View.OnClickListener cardClickedListener;

    String[] cardsTitles;
    int[] colorsArray, imagesArray, prices;

    public CardsAdapter(Context context, ViewGroup[] containers, String[] cardsTitles, int[] colorsArray, int[] imagesArray, int[] prices, View.OnClickListener cardClickedListener){

        this.context = context;
        this.cardsTitles = cardsTitles;
        this.colorsArray = colorsArray;
        this.imagesArray = imagesArray;
        this.prices = prices;
        this.containers = containers;
        this.cardClickedListener = cardClickedListener;

    }

    public void fillContainer(int delay){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        for (int i=0; i<cardsTitles.length; i++){
            View card = inflater.inflate(R.layout.card_view, null);
            card.setId(i);
            card.setOnClickListener(cardClickedListener);
            TextView tv = (TextView) card.findViewById(R.id.card_title);
            FrameLayout fl = (FrameLayout) card.findViewById(R.id.card_background);
            ImageView iv = (ImageView) card.findViewById(R.id.card_image);
            FrameLayout ph = (FrameLayout) card.findViewById(R.id.pricePlaceHolder);

            tv.setText(cardsTitles[i]);
            fl.setBackgroundColor(colorsArray[i]);
            iv.setImageResource(imagesArray[i]);

            int price = prices[i];
            if (price != 0){
                if (price == -1){
                    ImageView bill = new ImageView(context);
                    bill.setColorFilter(context.getResources().getColor(R.color.light_green));
                    bill.setImageResource(R.drawable.ic_cash_usd_black_24dp);
                    ph.addView(bill);

                }
                else{
                    TextView bill = new TextView(context);
                    bill.setText("$" + String.valueOf(price));
                    bill.setTextColor(context.getResources().getColor(R.color.green));
                    bill.setGravity(Gravity.CENTER);
                    ph.addView(bill);

                }
            }
            putInContainer(containers, card, i, delay);

        }
    }

    public void emptyContainer(){
        for(ViewGroup c : containers){
            c.removeAllViews();
        }
    }

    private void putInContainer(ViewGroup[] containers, View card, int index, int delay){

        containers[index%containers.length].addView(card);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.card_fade_in);
        animation.setStartOffset(100*index + delay);
        card.startAnimation(animation);

    }

}
