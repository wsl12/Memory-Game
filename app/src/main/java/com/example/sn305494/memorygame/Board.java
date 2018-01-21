package com.example.sn305494.memorygame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.*;

/**
 * Created by sn305494 on 3/20/2015.
 */
public class Board extends Activity {

    private final int[] images = {R.drawable.ic_launcher , R.drawable.banana, R.drawable.adidas,
                                  R.drawable.school_supplies, R.drawable.apple_logo, R.drawable.target,
                                  R.drawable.ice_cream, R.drawable.microsoft, R.drawable.google,
                                  R.drawable.samsung, R.drawable.disney, R.drawable.twitter,
                                  R.drawable.cookie, R.drawable.spotify, R.drawable.mcdonalds};

    //private ImageView[] startImages;
    private ImageView[] selections;
    private List<Integer> randomImages;
    private boolean[] imageCheck;
    private TextView score;
    private String boardSize;
    private int numberOfClicks;
    private int displayedScore;
    private int boardDimensions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        boardSize = intent.getExtras().getString("boardSize");
        int width = Integer.parseInt(boardSize.substring(0, 1));
        int height = Integer.parseInt(boardSize.substring(2));
        boardDimensions = width * height;

        if (boardDimensions == 12) {
            setContentView(R.layout.three_by_four);
        } else if (boardDimensions == 20) {
            setContentView(R.layout.four_by_five);
        } else {
            setContentView(R.layout.five_by_six);
        }

        ImageView[] startImages = {(ImageView) findViewById(R.id.image1), (ImageView) findViewById(R.id.image2),
                                   (ImageView) findViewById(R.id.image3), (ImageView) findViewById(R.id.image4),
                                   (ImageView) findViewById(R.id.image5), (ImageView) findViewById(R.id.image6),
                                   (ImageView) findViewById(R.id.image7), (ImageView) findViewById(R.id.image8),
                                   (ImageView) findViewById(R.id.image9), (ImageView) findViewById(R.id.image10),
                                   (ImageView) findViewById(R.id.image11), (ImageView) findViewById(R.id.image12),
                                   (ImageView) findViewById(R.id.image13), (ImageView) findViewById(R.id.image14),
                                   (ImageView) findViewById(R.id.image15), (ImageView) findViewById(R.id.image16),
                                   (ImageView) findViewById(R.id.image17), (ImageView) findViewById(R.id.image18),
                                   (ImageView) findViewById(R.id.image19), (ImageView) findViewById(R.id.image20),
                                   (ImageView) findViewById(R.id.image21), (ImageView) findViewById(R.id.image22),
                                   (ImageView) findViewById(R.id.image23), (ImageView) findViewById(R.id.image24),
                                   (ImageView) findViewById(R.id.image25), (ImageView) findViewById(R.id.image26),
                                   (ImageView) findViewById(R.id.image27), (ImageView) findViewById(R.id.image28),
                                   (ImageView) findViewById(R.id.image29), (ImageView) findViewById(R.id.image30)};

        selections = new ImageView[boardDimensions];
        randomImages = new ArrayList<>();
        imageCheck = new boolean[boardDimensions];
        score = (TextView) findViewById(R.id.score);
        Random random = new Random();
        Set<Integer> imagesUsed = new HashSet<Integer>();

        for (int i = 0; i < boardDimensions; i++) {
            selections[i] = startImages[i];
        }

        while (randomImages.size() < boardDimensions) {
            int rand = random.nextInt(images.length);
            if (!imagesUsed.contains(rand)) {
                randomImages.add(images[rand]);
                randomImages.add(images[rand]);
                imagesUsed.add(rand);
            }
        }
        Collections.shuffle(randomImages);
    }

    public void onClick(View v) {
        for (int i = 0; i < selections.length; i++){
            if (v == selections[i]){
                numberOfClicks++;
                if (numberOfClicks == 2){
                    limitNumberOfClicks();
                }
                startAnimation(selections[i]);

                selections[i].setClickable(false);
                selections[i].setBackgroundResource(randomImages.get(i));
                imageCheck[i] = true;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(numberOfClicks == 2) {
                            checkMatch();
                        }
                    }
                }, 1000);
            }
        }
    }

    public void checkMatch() {
        for (int i = 0; i < selections.length; i++){
            for (int j = 0; j < selections.length; j++){
                if (selections[i].equals(selections[j])){
                    continue;
                }
                if (imageCheck[i] && imageCheck[j]) {
                    if ((randomImages.get(i)).equals(randomImages.get(j))) {
                        displayedScore++;
                        score.setText("Score: " + displayedScore);
                        resetClicks();
                        imageCheck[i] = false;
                        imageCheck[j] = false;
                        selections[i].setEnabled(false);
                        selections[j].setEnabled(false);
                        numberOfClicks = 0;
                        if(displayedScore == boardDimensions / 2) {
                            Intent userWin = new Intent(this, WinScreen.class);
                            userWin.putExtra("boardSize", boardSize);
                            startActivity(userWin);
                            finish();
                        }
                    } else if (!(randomImages.get(i)).equals(randomImages.get(j))) {
                        resetAnimation(selections[i], selections[j]);
                        selections[i].setBackgroundResource(R.drawable.rounded_image);
                        selections[j].setBackgroundResource(R.drawable.rounded_image);
                        resetClicks();
                        imageCheck[i] = false;
                        imageCheck[j] = false;
                        numberOfClicks = 0;
                    }
                }
            }
        }
    }

    public void startAnimation(View v) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(v, "rotationY", 0f, 360f);
        animY.setDuration(1000);
        animY.start();
    }

    public void resetAnimation(View v, View v2) {
        ObjectAnimator anim1Y = ObjectAnimator.ofFloat(v, "rotationY", 360f, 0f);
        ObjectAnimator anim2Y = ObjectAnimator.ofFloat(v2, "rotationY", 360f, 0f);
        AnimatorSet setImages = new AnimatorSet();
        setImages.playTogether(anim1Y, anim2Y);
        setImages.start();
        v.setClickable(true);
        v2.setClickable(true);
    }

    public void limitNumberOfClicks() {
        for (int i = 0; i < selections.length; i++){
            selections[i].setClickable(false);
        }
    }

    public void resetClicks() {
        for (int i = 0; i < selections.length; i++) {
            selections[i].setClickable(true);
        }
    }

    public void returnMainScreen(View v) {
        Intent returnHome = new Intent(this, MainActivity.class);
        startActivity(returnHome);
        finish();
    }
}
