package com.aar.app.wordsearch.model;

public enum GameMode {

    Normal(1),
    Hidden(2),
    CountDown(3);

    private int mId;
    GameMode(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public static GameMode getById(int id) {
        for (GameMode gameMode : values()) {
            if (gameMode.mId == id) return gameMode;
        }
        return Normal;
    }
}
