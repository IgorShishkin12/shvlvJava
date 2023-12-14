
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Player extends Thread {
    private static final Set<Integer> generatedNumbers = new HashSet<>();
    private static final Object lock = new Object();

    private final String playerName;
    private final int maxNumber;

    public Player(String playerName, int maxNumber) {
        this.playerName = playerName;
        this.maxNumber = maxNumber;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!allNumbersGenerated()) {
            int number = random.nextInt(maxNumber) + 1;
            if (addGeneratedNumber(number)) {
                System.out.println(playerName + " generated: " + number);
            }
        }
    }

    private boolean addGeneratedNumber(int number) {
        synchronized (lock) {
            return generatedNumbers.add(number);
        }
    }

    private boolean allNumbersGenerated() {
        synchronized (lock) {
            return generatedNumbers.size() == maxNumber;
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMaxNumber() {
        return maxNumber;
    }
    public Set<Integer> getGeneratedNumbers(){
        return generatedNumbers;
    }
}

class Judge extends Thread {
    private final Player[] players;

    public Judge(Player[] players) {
        this.players = players;
    }

    @Override
    public void run() {
        for (Player player : players) {
            try {
                player.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        determineWinner();
    }

    private void determineWinner() {
        System.out.println("Game Over!");
        int maxScore = -1;
        String winner = null;

        for (Player player : players) {
            int score = countScore(player);
            System.out.println(player.getName() + " scored: " + score);
            if (score > maxScore) {
                maxScore = score;
                winner = player.getName();
            }
        }

        System.out.println("Winner: " + winner);
    }

    private int countScore(Player player) {
        synchronized (Judge.class) {
            int score = 0;
            for (int i = 1; i <= player.getMaxNumber(); i++) {
                if (player.getGeneratedNumbers().contains(i)) {
                    score++;
                }
            }
            return score;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int maxNumber = 100;
        int numberOfPlayers = 2;

        Player[] players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player("Player " + (i + 1), maxNumber);
            players[i].start();
        }

        Judge judge = new Judge(players);
        judge.start();
    }
}
