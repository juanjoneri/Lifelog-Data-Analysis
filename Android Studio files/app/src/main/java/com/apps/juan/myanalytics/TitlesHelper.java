package com.apps.juan.myanalytics;


public class TitlesHelper {

    String[] main, foodAndDrink, life, sports, school, shop, transport, events, health;
    String[][] titles;

    public TitlesHelper(){

        this.main = new String[] {"Food & Drink", "Life", "Sports", "School", "Shop", "Transport", "Events", "Health"};

        this.foodAndDrink = new String[] {"Water", "Meal", "Snack", "Coffee" ,"Soft drink", "Beverage"};
        this.life = new String[] {"Sleep", "Wake up", "Shower", "Bathroom", "Laundry", "Haircut "};
        this.sports = new String[] {"Walk", "Gym", "Swimming", "Match", "Bike"};
        this.school = new String[] {"Test", "Result", "Homework", "Hand in", "Certificate"};
        this.shop = new String[] {"Clothes", "School materials", "Electronics", "Gift", "Grooming"};
        this.transport = new String[] {"Car", "Bus", "Taxi", "Plane", "Boat"};
        this.events = new String[] {"Birthday", "Party", "University Event", "Stadium", "Concert"};
        this.health = new String[] {"Injury", "Fiver", "Hospital", "Medicine"};

        this.titles = new String[][] {foodAndDrink, life, sports, school, shop, transport, events, health};

       }

    public String[] getTitles(int index) {
        return titles[index];
    }

    public String[] getMainTitles(){
        return main;
    }
}
