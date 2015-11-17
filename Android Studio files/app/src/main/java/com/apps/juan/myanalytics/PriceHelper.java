package com.apps.juan.myanalytics;

public class PriceHelper {

    int [][] prices;

    public PriceHelper(){

        prices = new int[][] {
            {0,-1,-1,-1,-1,-1},
            {0,0,0,0,10,25},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {-1,-1,-1,-1,-1},
            {0,-1,-1,0,0},
            {0,0,0,-1,-1},
            {0,0,0,0}
        };

    }

    public int[] getPrices (int index){
        return prices[index];
    }

}
