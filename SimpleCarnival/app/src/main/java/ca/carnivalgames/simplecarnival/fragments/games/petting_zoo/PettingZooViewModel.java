package ca.carnivalgames.simplecarnival.fragments.games.petting_zoo;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import java.lang.Math;

public class PettingZooViewModel extends ViewModel {
    private int currentClicks;
    private int totalClicks;
    private int coins;
    private int totalCoins;
    private boolean angryPig = false;
    private int level;

    /**
     * Increases currentClicks by 1
     */
    public void onAnimalPet() {
        currentClicks += 1;
    }

    /**
     * Determines whether or not a player receives a coin. If they do increase coins by 1.
     */
    public void generateCoins(){
        double coinChance = level*0.05;
        double d = Math.random();
        if (d <= coinChance){
            coins++;
        }
    }

    /**
     * Increases level if they click more that 30 times. Caps level at 20.
     * Takes gameStatusView and set its text to text1 or text2 depending on whether the player leveled up.
     * @param gameStatusView: TextView
     * @param text1: String that is displayed when player beats level
     * @param text2: String that is dispayed when player does not beat level
     */
    public void levelUp(TextView gameStatusView, String text1, String text2){
        if (level < 20 && currentClicks > 30){
            level++;
            totalCoins = coins + totalCoins;
            totalClicks = currentClicks + totalClicks;
            gameStatusView.setText(text1);
        }
        else{
            gameStatusView.setText(text2);
        }
    }

    /**
     * Determines whether or not the pig gets angry.
     */
    public void becomeAngry(){
        double angryChance = level*0.05;
        double d = Math.random();
        if (d <= angryChance){
            this.angryPig = true;
        }
    }

    /**
     * Makes pig happy.
     */
    public void becomeHappy(){
        this.angryPig = false;
    }

    //getters and setters

    public boolean isAngryPig() {
        return angryPig;
    }

    public void setAngryPig(boolean angryPig) {
        this.angryPig = angryPig;
    }

    public int getCurrentClicks() {
        return currentClicks;
    }

    public void setCurrentClicks(int currentClicks) {
        this.currentClicks = currentClicks;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(int totalCoins) {
        this.totalCoins = totalCoins;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
