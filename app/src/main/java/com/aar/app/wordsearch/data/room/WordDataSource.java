package com.aar.app.wordsearch.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.aar.app.wordsearch.model.Word;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface WordDataSource {

    @Query("SELECT * FROM words")
    Flowable<List<Word>> getWords();

    @Query("SELECT * FROM words WHERE game_theme_id=:themeId")
    Flowable<List<Word>> getWords(int themeId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Word> words);

}
