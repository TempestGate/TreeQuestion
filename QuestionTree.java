//Programmer: Luis Lopez
//Date: 8/8/23
/*
This code simulates a guessing game where a robot with a tree-like brain 
guesses what object you're thinking of by asking yes-or-no questions. 
It starts with a default guess and adapts based on your responses. 
If it's wrong, you provide the correct answer and a new question to refine its knowledge. 
As you play, the robot's ability to guess improves, and you can save its learned information 
for future games. The code also includes a text-based user interface that interacts with the player, 
allowing them to play multiple rounds, view game statistics, and optionally save/load game progress.
*/

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionTree {
    private UserInterface ui;
    private QuestionNode root;
    private int totalGamesPlayed;
    private int gamesWon;

    // Constructor for QuestionTree
    public QuestionTree(UserInterface ui) {
        this.ui = ui;
        root = new QuestionNode("computer"); // Initialize the root node
        totalGamesPlayed = 0;
        gamesWon = 0;
    }

    // Method to play the game
    public void play() {
        totalGamesPlayed++;
        QuestionNode currentNode = root;

        // Traverse the tree based on user responses
        while (currentNode.yes != null && currentNode.no != null) {
            ui.print(currentNode.data + " ");
            if (ui.nextBoolean()) {
                currentNode = currentNode.yes;
            } else {
                currentNode = currentNode.no;
            }
        }

        // Make a guess and handle win or loss
        ui.print("Would your object happen to be " + currentNode.data + "? ");
        if (ui.nextBoolean()) {
            ui.println("I win!");
            gamesWon++;
        } else {
            handleLoss(currentNode);
        }
    }

    // Method to handle a loss in the game
    private void handleLoss(QuestionNode incorrectGuess) {
        ui.print("I lose. What is your object? ");
        String userObject = ui.nextLine();
        ui.print("Type a yes/no question to distinguish your item from " + incorrectGuess.data + ": ");
        String newQuestion = ui.nextLine();
        ui.print("And what is the answer for your object? ");
        boolean userAnswer = ui.nextBoolean();

        // Update the tree with new information
        QuestionNode newQuestionNode = new QuestionNode(newQuestion);
        QuestionNode userObjectNode = new QuestionNode(userObject);

        if (userAnswer) {
            newQuestionNode.yes = userObjectNode;
            newQuestionNode.no = incorrectGuess;
        } else {
            newQuestionNode.yes = incorrectGuess;
            newQuestionNode.no = userObjectNode;
        }

        // Offer to play another round
        ui.print("Challenge me again? ");
        if (ui.nextBoolean()) {
            play();
        }
    }

    // Method to get the total number of games played
    public int totalGames() {
        return totalGamesPlayed;
    }

    // Method to get the number of games won
    public int gamesWon() {
        return gamesWon;
    }
}

// Class representing a node in the question tree
class QuestionNode {
    public String data;
    public QuestionNode yes;
    public QuestionNode no;

    // Constructor for QuestionNode
    public QuestionNode(String data) {
        this.data = data;
        yes = null;
        no = null;
    }
}
