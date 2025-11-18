package com.abigail.androidclickergame;

public class Food {
    final int cost;
    private int amountOfFood;
    final int foodPoints;
    final int TextViewId;
    final int viewId;
    final int textFieldIndex;

    public int getTextFieldIndex() {
        return textFieldIndex;
    }

    public Food(int foodPoints, int amountOfFood, int cost, int TextViewId, int viewId, int textFieldIndex) {
        this.foodPoints = foodPoints;
        this.amountOfFood = amountOfFood;
        this.cost = cost;
        this.TextViewId = TextViewId;
        this.viewId = viewId;
        this.textFieldIndex = textFieldIndex;
    }
    public int getViewId() {
        return viewId;
    }
    public int getTextViewId() {
        return TextViewId;
    }
    public int getCost() {
        return cost;
    }
    public int getAmountOfFood() {
        return amountOfFood;
    }
    public void setAmountOfFood(int amountOfFood) {
        this.amountOfFood = amountOfFood;
    }
    public void increaseFoodAmount(){
        this.amountOfFood++;
    }
    public void decreaseFoodAmount(){this.amountOfFood--;}
    public int getFoodPoints() {
        return foodPoints;
    }
}
