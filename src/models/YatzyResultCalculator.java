package models;

import java.util.Arrays;

public class YatzyResultCalculator {
    private final int[] dice = new int[5];

    public YatzyResultCalculator(Die[] dice) {
        for (int i = 0; i < dice.length; i++) {
            this.dice[i] = dice[i].getEyes();
        }
        Arrays.sort(this.dice);
    }

    public int upperSectionScore(int eyes) {
        int score = 0;
        for (int die : dice) {
            if (die == eyes) {
                score += eyes;
            }
        }
        return score;
    }

    public int onePairScore() {
        for (int i = dice.length - 1; i > 0; i--) {
            if (dice[i] == dice[i - 1]) {
                return dice[i] * 2;
            }
        }
        return 0;
    }

    public int twoPairScore() {
        int twoPairScore = 0;

        for (int i = dice.length - 1; i > 0; i--) {
            if (dice[i] == dice[i - 1]) {
                if (twoPairScore == 0) {
                    twoPairScore = dice[i] * 2;
                    i--;
                } else {
                    return twoPairScore + (dice[i] * 2);
                }
            }
        }
        return 0;
    }

    public int threeOfAKindScore() {
        for (int i = dice.length - 1; i > 0; i--) {
            if (dice[i] == dice[i - 1] && dice[i] == dice[i - 2]) {
                return dice[i] * 3;
            }
        }
        return 0;
    }

    public int fourOfAKindScore() {
        for (int i = dice.length - 1; i > 0; i--) {
            if (dice[i] == dice[i - 1] && dice[i] == dice[i - 2] && dice[i] == dice[i - 3]) {
                return dice[i] * 4;
            }
        }
        return 0;
    }

    public int smallStraightScore() {
        int[] smallStraight = {1, 2, 3, 4, 5};
        if (Arrays.equals(dice, smallStraight)) {
            return 15;
        }
        return 0;
    }

    public int largeStraightScore() {
        int[] largeStraight = {2, 3, 4, 5, 6};
        if (Arrays.equals(dice, largeStraight)) {
            return 20;
        }
        return 0;
    }

    public int fullHouseScore() {
        int threeOfAKindValue = 0;
        int pairValue = 0;
        for (int i = dice.length - 1; i > 1; i--) {
            if (dice[i] == dice[i - 1] && dice[i] == dice[i - 2]) {
                threeOfAKindValue = dice[i] * 3;
            }
        }
        for (int i = dice.length - 1; i > 0; i--) {
            if (dice[i] == dice[i - 1]) {
                if (threeOfAKindValue / 3 != dice[i]) {
                    pairValue = dice[i] * 2;
                }
            }
            if (threeOfAKindValue > 0 && pairValue > 0) {
                return threeOfAKindValue + pairValue;
            }
        }
        return 0;
    }

    public int chanceScore() {
        int sum = 0;
        for (int die : dice) {
            sum += die;
        }
        return sum;
    }

    public int yatzyScore() {
        for (int i = 0; i < dice.length - 1; i++) {
            if (dice[i] != dice[i + 1]) {
                return 0;
            }
        }
        return 50;
    }
}
