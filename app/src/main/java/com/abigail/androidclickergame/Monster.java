package com.abigail.androidclickergame;

public class Monster {

    private boolean isHatched;
    final int cost;
    private int eggImgId;
    private int monsterImgId;
    private int viewId;
    final String effectId;

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getEffectId() {
        return effectId;
    }

    public Monster(int viewId, boolean isHatched, int cost, int eggImgId, int monsterImgId, String effectId) {
        this.isHatched = isHatched;
        this.cost = cost;
        this.eggImgId = eggImgId;
        this.monsterImgId = monsterImgId;
        this.viewId = viewId;
        this.effectId = effectId;
    }

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
}
