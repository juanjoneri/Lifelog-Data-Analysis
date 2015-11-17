package com.apps.juan.myanalytics;
import android.content.Context;

import com.apps.juan.myanalytics.R;

import java.util.Arrays;

public class ColorsHelper {

    int[] colors;
    int[] foodAndDrink, life, sports, school, shop, transport, events, health;
    int[][] details;
    String[] colorNames;
    Context context;

    public ColorsHelper(Context context){

        this.context = context;
        this.colors = new int[19];
        this.colorNames = new String[19];

        colorNames[0] = "red";
        colorNames[1] = "pink";
        colorNames[2] = "purple";
        colorNames[3] = "deep_purple";
        colorNames[4] = "indigo";
        colorNames[5] = "blue";
        colorNames[6] = "light_blue";
        colorNames[7] = "cyan";
        colorNames[8] = "teal";
        colorNames[9] = "green";
        colorNames[10] = "light_green";
        colorNames[11] = "lime";
        colorNames[12] = "yellow";
        colorNames[13] = "amber";
        colorNames[14] = "orange";
        colorNames[15] = "deep_orange";
        colorNames[16] = "brown";
        colorNames[17] = "grey";
        colorNames[18] = "blue_grey";

        colors[0] = context.getResources().getColor(R.color.red);
        colors[1] = context.getResources().getColor(R.color.pink);
        colors[2] = context.getResources().getColor(R.color.purple);
        colors[3] = context.getResources().getColor(R.color.deep_purple);
        colors[4] = context.getResources().getColor(R.color.indigo);
        colors[5] = context.getResources().getColor(R.color.blue);
        colors[6] = context.getResources().getColor(R.color.light_blue);
        colors[7] = context.getResources().getColor(R.color.cyan);
        colors[8] = context.getResources().getColor(R.color.teal);
        colors[9] = context.getResources().getColor(R.color.green);
        colors[10] = context.getResources().getColor(R.color.light_green);
        colors[11] = context.getResources().getColor(R.color.lime);
        colors[12] = context.getResources().getColor(R.color.yellow);
        colors[13] = context.getResources().getColor(R.color.amber);
        colors[14] = context.getResources().getColor(R.color.orange);
        colors[15] = context.getResources().getColor(R.color.deep_orange);
        colors[16] = context.getResources().getColor(R.color.brown);
        colors[17] = context.getResources().getColor(R.color.grey);
        colors[18] = context.getResources().getColor(R.color.blue_grey);

        this.foodAndDrink = new int[] {getColor("blue"), getColor("light_green"), getColor("red"), getColor("brown"), getColor("lime"), getColor("cyan"), getColor("purple"), getColor("blue_grey")};
        this.life = new int[]{getColor("grey"), getColor("yellow"), getColor("blue"), getColor("green"), getColor("pink"), getColor("orange")};
        this.sports = new int[] {getColor("indigo"), getColor("grey"), getColor("light_blue"), getColor("green"), getColor("brown")};
        this.school = new int[] {getColor("light_green"), getColor("teal"), getColor("deep_orange"), getColor("purple"), getColor("lime")};
        this.shop = new int[] {getColor("deep_purple"), getColor("blue_grey"), getColor("teal"), getColor("red"), getColor("blue")};
        this.transport = new int[] {getColor("red"), getColor("yellow"), getColor("amber"), getColor("cyan"), getColor("blue")};
        this.events = new int[] {getColor("orange"), getColor("pink"), getColor("green"), getColor("brown"), getColor("deep_purple")};
        this.health = new int[] {getColor("grey"), getColor("lime"), getColor("blue_grey"), getColor("green")};

        this.details = new int[][] {foodAndDrink, life, sports, school, shop, transport, events, health};
    }

    public int getColor(String color){
        return colors[Arrays.asList(colorNames).indexOf(color)];
    }

    public int[] getMainScreenColors(){
        int [] mainColors = {   getColor("blue"),
                                getColor("light_green"),
                                getColor("red"),
                                getColor("brown"),
                                getColor("amber"),
                                getColor("green"),
                                getColor("purple"),
                                getColor("cyan")};
        return  mainColors;
    }

    public Integer[] getColors(String[] colorNames){
        Integer[] colors = new Integer[colorNames.length];

        for(int i=0; i<colors.length; i++){
            colors[i] = getColor(colorNames[i]);
        }

        return colors;
    }

    public int[] getColors(int index){
       return details[index];
    }

}
