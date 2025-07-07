
package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    private int currentQuestionIndex = 0;
    private int score = 0; // Variable to track the score

    private final String[] questions = {
            "What is the capital of France?",  
            "What is 67*7+ 2?",                  
            "What is the largest planet in our solar system?", 
            "What is the chemical symbol for water?",           
            "Which planet is known as the Red Planet?",        
            "What is the square root of 16?",                   
            "Who wrote 'Romeo and Juliet'?"                      
    };

    private final String[][] options = {
            {"Berlin", "Madrid", "Paris", "Rome"}, 
            {"387", "471", "590", "656"},                   
            {"Earth", "Mars", "Jupiter", "Saturn"},
            {"H2O", "O2", "CO2", "N2"},             
            {"Mars", "Venus", "Jupiter", "Saturn"},
            {"2", "4", "8", "16"},                  
            {"Shakespeare", "Hemingway", "Tolkien", "Dickens"}
    };
//Create button for all choice
   
    
    private final int[] answers = {2, 1, 2, 0, 0, 1, 0}; // Index of correct answers

    private Label questionLabel;
    private ToggleGroup optionsGroup;
    private Label scoreLabel;
    private Button startButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Easy Quiz");

        // Title Label
        Label titleLabel = new Label("Easy Quiz");
        titleLabel.setStyle("-fx-font-size: 34px; -fx-font-weight: bold; -fx-text-fill: #3B3B3B;"); // Large title style

        // Initialize UI components
        startButton = new Button("PLAY");
        startButton.setOnAction(e -> startQuiz());
        startButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 30px;"); // Orange button with larger font
        startButton.setPadding(new Insets(10)); // Padding for the button

        VBox startLayout = new VBox(20, titleLabel, startButton); // Use VBox to center the button and title
        startLayout.setPadding(new Insets(20));
        startLayout.setStyle("-fx-background-color: #D3E4CD;"); // Light green background
        startLayout.setAlignment(Pos.CENTER); // Center the content
        Scene startScene = new Scene(startLayout, 800, 800);

        primaryStage.setScene(startScene); // Set the start scene
        primaryStage.show(); // Show the application window
    }

    private void startQuiz() {
        currentQuestionIndex = 0;
        score = 0; // Reset score
        displayQuestion();
    }

    private void displayQuestion() {
        questionLabel = new Label(getQuestionWithNumber());
        questionLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #3B3B3B;"); // Question style

        optionsGroup = new ToggleGroup();
        VBox optionsBox = new VBox(10);
        optionsBox.setStyle("-fx-background-color: #F0F8FF;"); // Alice blue background

        // Create radio buttons for the current question options
        for (String option : options[currentQuestionIndex]) {
            RadioButton radioButton = new RadioButton(option);
            radioButton.setToggleGroup(optionsGroup);
            radioButton.setStyle("-fx-text-fill: #2F4F4F;"); // Dark slate gray text
            optionsBox.getChildren().add(radioButton);
        }

        scoreLabel = new Label("Score: " + score); // Score label
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #3B3B3B;"); // Dark gray text


// Create a submit button with an action handler
       
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> checkAnswer());
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green button

        // Layout for the quiz application
        VBox layout = new VBox(10, scoreLabel, questionLabel, optionsBox, submitButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #E8F0F2;"); // Light gray background
        Scene quizScene = new Scene(layout, 700, 700);

        Stage quizStage = new Stage();
        quizStage.setTitle("Quiz Time");
        quizStage.setScene(quizScene);
        quizStage.show(); // Show the quiz window
    }

    // Method to get the current question with its number
    private String getQuestionWithNumber() {
        return "Question " + (currentQuestionIndex + 1) + ": " + questions[currentQuestionIndex];
    }

    // Method to check the selected answer
    private void checkAnswer() {
        RadioButton selectedOption = (RadioButton) optionsGroup.getSelectedToggle();
        if (selectedOption != null) {
            int selectedIndex = optionsGroup.getToggles().indexOf(selectedOption);
            if (selectedIndex == answers[currentQuestionIndex]) {
                score++; // Increment score for correct answer
                showAlert("Correct!", "You selected the right answer.");
            } else {
                showAlert("Wrong!", "That's not the correct answer.");
            }
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                displayQuestion(); // Show the next question
            } else {
                showResult(); // Show the result at the end of the quiz
            }
        } else {
            showAlert("No Selection", "Please select an answer.");
        }
    }

    // Method to show the final result
    private void showResult() {
        Stage resultStage = new Stage();
        VBox resultLayout = new VBox(10);
        resultLayout.setPadding(new Insets(20));
        resultLayout.setStyle("-fx-background-color: #E8F0F2;"); // Light gray background

        // Congratulations Circle
        Circle congratsCircle = new Circle(70);
        congratsCircle.setFill(score >= 5 ? Color.GREEN : Color.RED); // Green for high score, Red for low
        congratsCircle.setStroke(Color.BLACK);
        congratsCircle.setStrokeWidth(2);

        // Congratulatory Label
        Label congratsLabel = new Label("Congratulations!");
        congratsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: RED;"); // White text
        Label finalScoreLabel = new Label("Your final score is: " + score + "/" + questions.length);
        finalScoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #3B3B3B;"); // Dark gray text

        // Special message for winners
        if (score >= 5) {
            Label winnerMessage = new Label("You are a quiz champion!");
            winnerMessage.setStyle("-fx-font-size: 16px; -fx-text-fill: #FF4500;"); // Orange red text
            resultLayout.getChildren().addAll(congratsCircle, congratsLabel, finalScoreLabel, winnerMessage);
        } else {
            Label loserMessage = new Label("Better luck next time!");
            loserMessage.setStyle("-fx-font-size: 16px; -fx-text-fill: #FF4500;"); // Orange red text
            resultLayout.getChildren().addAll(congratsCircle, congratsLabel, finalScoreLabel, loserMessage);
        }

        resultLayout.setAlignment(Pos.CENTER); // Center the result layout
        Scene resultScene = new Scene(resultLayout, 500, 700);
        resultStage.setTitle("Quiz Finished");
        resultStage.setScene(resultScene);
        resultStage.show(); // Show the result window

        // Close the quiz stage
        Stage stage = (Stage) scoreLabel.getScene().getWindow();
        stage.close();
    }

// Method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
