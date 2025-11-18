package com.abigail.androidclickergame;

public class Monster {

    private boolean isHatched;
    final int cost;
    private int eggImgId;
    private int monsterImgId;
    private int viewId;
    final String effectId;

    public void setMonsterDesc(String monsterDesc) {
        this.monsterDesc = monsterDesc;
    }

    private String monsterDesc;
    private int level;
    private int levelCap;
    private int currentLevelPts;
    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getEffectId() {
        return effectId;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevelCap() {
        return levelCap;
    }

    public int getCurrentLevelPts() {
        return currentLevelPts;
    }

    public void setCurrentLevelPts(int currentLevelPts) {
        this.currentLevelPts = currentLevelPts;
    }

    public void setLevelCap(int levelCap) {
        this.levelCap = levelCap;
    }

    public Monster(int viewId,
                   boolean isHatched,
                   int cost,
                   int eggImgId,
                   int monsterImgId,
                   String effectId,
                   String monsterDesc,
                   int level,
                   int levelCap,
                   int currentLevelPts) {
        this.isHatched = isHatched;
        this.cost = cost;
        this.eggImgId = eggImgId;
        this.monsterImgId = monsterImgId;
        this.viewId = viewId;
        this.effectId = effectId;
        this.monsterDesc = monsterDesc;
        this.level = level;
        this.levelCap = levelCap;
        this.currentLevelPts = currentLevelPts;
    }

    public String getMonsterDesc() {return monsterDesc;}
    public boolean isHatched() {
        return isHatched;
    }
    public void setHatched(boolean hatched) {
        isHatched = hatched;
    }
    public int getCost() {
        return cost;
    }
    public int getEggImgId() {
        return eggImgId;
    }
    public void setEggImgId(int eggImgId) {
        this.eggImgId = eggImgId;
    }
    public int getMonsterImgId() {
        return monsterImgId;
    }
    public void setMonsterImgId(int monsterImgId) {
        this.monsterImgId = monsterImgId;
    }
    public void levelUp(){this.level++;}
    public void increaseLevelPts(int incrementer){
        this.currentLevelPts+=incrementer;
    }
    public void changeMonsterDesc(int number){

    }
}
