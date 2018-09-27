package com.aar.app.wsp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.aar.app.wsp.data.room.AnswerLineConverter;

/**
 * Created by abdularis on 08/07/17.
 */

@Entity(tableName = "used_words")
public class UsedWord extends Word {

    @ColumnInfo(name = "game_data_id")
    private int gameDataId;
    @TypeConverters({AnswerLineConverter.class})
    @ColumnInfo(name = "answer_line")
    private AnswerLine mAnswerLine;
    @ColumnInfo(name = "duration")
    private int mDuration;
    @ColumnInfo(name = "max_duration")
    private int mMaxDuration;

    public UsedWord() {
        mAnswerLine = null;
        mDuration = 0;
        mMaxDuration = 0;
    }

    public int getGameDataId() {
        return gameDataId;
    }

    public void setGameDataId(int gameDataId) {
        this.gameDataId = gameDataId;
    }

    public AnswerLine getAnswerLine() {
        return mAnswerLine;
    }

    public void setAnswerLine(AnswerLine answerLine) {
        mAnswerLine = answerLine;
    }

    public boolean isAnswered() {
        return mAnswerLine != null;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getMaxDuration() {
        return mMaxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        mMaxDuration = maxDuration;
    }

    public boolean isTimeout() {
        return mDuration >= mMaxDuration;
    }

    public int getRemainingDuration() {
        return Math.max(0, mMaxDuration - mDuration);
    }

    public static final class AnswerLine {
        public int startRow;
        public int startCol;
        public int endRow;
        public int endCol;
        public int color;

        public AnswerLine() {
            this(0, 0, 0, 0, 0);
        }

        public AnswerLine(int startRow, int startCol, int endRow, int endCol, int color) {
            this.startRow = startRow;
            this.startCol = startCol;
            this.endRow = endRow;
            this.endCol = endCol;
            this.color = color;
        }

        @Override
        public String toString() {
            return startRow + "," + startCol + ":" + endRow + "," + endCol + ":" + color;
        }

        public void fromString(String string) {
            /*
                Expected format string = 1,1:6,6
             */
            if (string == null) return;

            String split[] = string.split(":", 3);
            if (split.length >= 3) {
                String start[] = split[0].split(",", 2);
                String end[] = split[1].split(",", 2);

                color = Integer.parseInt(split[2]);
                if (start.length >= 2 && end.length >= 2) {
                    startRow = Integer.parseInt(start[0]);
                    startCol = Integer.parseInt(start[1]);
                    endRow = Integer.parseInt(end[0]);
                    endCol = Integer.parseInt(end[1]);
                }
            }
        }

    }

}
