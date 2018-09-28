package com.aar.app.wsp.features.mainmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.aar.app.wsp.R;
import com.aar.app.wsp.features.FullscreenActivity;
import com.aar.app.wsp.features.gamehistory.GameHistoryActivity;
import com.aar.app.wsp.features.gameplay.GamePlayActivity;
import com.aar.app.wsp.features.gamethemeselector.ThemeSelectorActivity;
import com.aar.app.wsp.model.Difficulty;
import com.aar.app.wsp.model.GameMode;
import com.aar.app.wsp.model.GameTheme;
import com.aar.app.wsp.features.settings.SettingsActivity;
import com.github.abdularis.horizontalselector.HorizontalSelector;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends FullscreenActivity {

    @BindView(R.id.selectorDifficulty) HorizontalSelector mDifficultySelector;
    @BindView(R.id.selectorGridSize) HorizontalSelector mGridSizeSelector;
    @BindView(R.id.selectorGameMode) HorizontalSelector mGameModeSelector;
    @BindView(R.id.imageEnjoy) View mEnjoy;
    @BindView(R.id.adView) AdView mAdView;

    @BindArray(R.array.game_round_dimension_values) int[] mGameRoundDimValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ButterKnife.bind(this);

        mEnjoy.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tag_enjoy));

        mGameModeSelector.setOnSelectedItemChangedListener(newItem -> {
            if (newItem.equals(getString(R.string.mode_count_down)) ||
                    newItem.equals(getString(R.string.mode_marathon))) {
                mDifficultySelector.setVisibility(View.VISIBLE);
            } else {
                mDifficultySelector.setVisibility(View.GONE);
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            
        });
    }

    @OnClick(R.id.settings_button)
    public void onSettingsClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.new_game_btn)
    public void onNewGameClick() {
        int dim = getGridSizeDimension();
        Intent intent = new Intent(this, ThemeSelectorActivity.class);
        intent.putExtra(ThemeSelectorActivity.EXTRA_ROW_COUNT, dim);
        intent.putExtra(ThemeSelectorActivity.EXTRA_COL_COUNT, dim);
        startActivityForResult(intent, 100);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btnHistory)
    public void onHistoryClick() {
        Intent i = new Intent(this, GameHistoryActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            startNewGame(
                    data.getIntExtra(ThemeSelectorActivity.EXTRA_THEME_ID, GameTheme.NONE.getId())
            );
        }
    }

    private void startNewGame(int gameThemeId) {
        int dim = getGridSizeDimension();
        Intent intent = new Intent(MainMenuActivity.this, GamePlayActivity.class);
        intent.putExtra(GamePlayActivity.EXTRA_GAME_DIFFICULTY, getDifficultyFromSpinner());
        intent.putExtra(GamePlayActivity.EXTRA_GAME_MODE, getGameModeFromSpinner());
        intent.putExtra(GamePlayActivity.EXTRA_GAME_THEME_ID, gameThemeId);
        intent.putExtra(GamePlayActivity.EXTRA_ROW_COUNT, dim);
        intent.putExtra(GamePlayActivity.EXTRA_COL_COUNT, dim);
        startActivity(intent);
    }

    private GameMode getGameModeFromSpinner() {
        if (mGameModeSelector.getCurrentValue() != null) {
            String selected = mGameModeSelector.getCurrentValue();
            if (selected.equals(getString(R.string.mode_hidden))) {
                return GameMode.Hidden;
            } else if (selected.equals(getString(R.string.mode_count_down))) {
                return GameMode.CountDown;
            } else if (selected.equals(getString(R.string.mode_marathon))) {
                return GameMode.Marathon;
            } else {
                return GameMode.Normal;
            }
        }
        return GameMode.Normal;
    }

    private Difficulty getDifficultyFromSpinner() {
        if (mDifficultySelector.getCurrentValue() != null) {
            String selected = mDifficultySelector.getCurrentValue();
            if (selected.equals(getString(R.string.diff_easy))) {
                return Difficulty.Easy;
            } else if (selected.equals(getString(R.string.diff_medium))) {
                return Difficulty.Medium;
            } else {
                return Difficulty.Hard;
            }
        }
        return Difficulty.Easy;
    }

    private int getGridSizeDimension() {
        return mGameRoundDimValues[ mGridSizeSelector.getCurrentIndex() ];
    }
}
