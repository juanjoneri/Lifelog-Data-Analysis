package com.apps.juan.myanalytics;

public class ImageHelper {

    int[] mainImages;
    int[] foodAndDrink, life, sports, school, shop, transport, events, health;
    int[][] images;


    public ImageHelper(){

        this.mainImages = new int[8];

        mainImages[0] = R.drawable.ic_restaurant_menu_white_36dp;
        mainImages[1] = R.drawable.ic_home_white_36dp;
        mainImages[2] = R.drawable.ic_whatshot_white_36dp;
        mainImages[3] = R.drawable.ic_school_white_36dp;
        mainImages[4] = R.drawable.ic_shopping_cart_white_36dp;
        mainImages[5] = R.drawable.ic_directions_car_white_36dp;
        mainImages[6] = R.drawable.ic_account_multiple_white_48dp;
        mainImages[7] = R.drawable.ic_healing_white_36dp;

        this.foodAndDrink = new int[6];

        foodAndDrink[0] = R.drawable.ic_local_drink_white_48dp;
        foodAndDrink[1] = R.drawable.ic_local_pizza_white_48dp;
        foodAndDrink[2] = R.drawable.ic_food_apple_white_48dp;
        foodAndDrink[3] = R.drawable.ic_local_cafe_white_48dp;
        foodAndDrink[4] = R.drawable.ic_beer_white_48dp;
        foodAndDrink[5] = R.drawable.ic_glass_tulip_white_48dp;

        this.life = new int[6];

        life[0] = R.drawable.ic_bell_sleep_white_48dp;
        life[1] = R.drawable.ic_white_balance_sunny_white_48dp;
        life[2] = R.drawable.ic_water_white_48dp;
        life[3] = R.drawable.ic_emoticon_poop_white_48dp;
        life[4] = R.drawable.ic_tshirt_crew_white_48dp;
        life[5] = R.drawable.ic_content_cut_white_48dp;

        this.sports = new int[5];

        sports[0] = R.drawable.ic_walk_white_48dp;
        sports[1] = R.drawable.ic_dumbbell_white_48dp;
        sports[2] = R.drawable.ic_swim_white_48dp;
        sports[3] = R.drawable.ic_football_helmet_white_48dp;
        sports[4] = R.drawable.ic_bike_white_48dp;

        this.school = new int[5];

        school[0] = R.drawable.ic_chair_school_white_48dp;
        school[1] = R.drawable.ic_chart_areaspline_white_48dp;
        school[2] = R.drawable.ic_pencil_white_48dp;
        school[3] = R.drawable.ic_file_white_48dp;
        school[4] = R.drawable.ic_trophy_white_48dp;

        this.shop = new int[5];

        shop[0] = R.drawable.ic_hanger_white_48dp;
        shop[1] = R.drawable.ic_pen_white_48dp;
        shop[2] = R.drawable.ic_laptop_mac_white_48dp;
        shop[3] = R.drawable.ic_gift_white_48dp;
        shop[4] = R.drawable.ic_scale_bathroom_white_48dp;

        this.transport = new int[5];

        transport[0] = R.drawable.ic_car_white_48dp;
        transport[1] = R.drawable.ic_bus_white_48dp;
        transport[2] = R.drawable.ic_taxi_white_48dp;
        transport[3] = R.drawable.ic_airplane_white_48dp;
        transport[4] = R.drawable.ic_ferry_white_48dp;

        this.events = new int[5];

        events[0] = R.drawable.ic_cake_white_36dp;
        events[1] = R.drawable.ic_local_bar_white_48dp;
        events[2] = R.drawable.ic_calendar_white_48dp;
        events[3] = R.drawable.ic_football_white_48dp;
        events[4] = R.drawable.ic_guitar_white_48dp;

        this.health = new int[4];

        health[0] = R.drawable.ic_bone_white_48dp;
        health[1] = R.drawable.ic_hotel_white_48dp;
        health[2] = R.drawable.ic_hospital_building_white_48dp;
        health[3] = R.drawable.ic_pill_white_48dp;

        this.images = new int[][] {foodAndDrink, life, sports, school, shop, transport, events, health};


    }

    public int getImage(int position){
        return mainImages[position];
    }

    public int[] getMainImages(){
        return mainImages;
    }

    public int[] getImages(int actionIndex){
        return images[actionIndex];
    }
}
