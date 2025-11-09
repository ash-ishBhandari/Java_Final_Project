import java.awt.*;
import javax.swing.*;

public class QuizGUI {
    private Question[] quiz = {
        new Question("1. Which language is used for Android development?", 
            new String[]{"Java", "Swift", "Python", "C#"}, 1),
        new Question("2. Which keyword is used to inherit a class in Java?", 
            new String[]{"implement", "this", "extends", "inherits"}, 3),
        new Question("3. Which of the following is not a Java feature?", 
            new String[]{"Object-Oriented", "Portable", "Dynamic", "Uses pointers"}, 4),
        new Question("4. What is the size of int data type in Java?", 
            new String[]{"2 bytes", "4 bytes", "8 bytes", "1 byte"}, 2),
        new Question("5. Which company originally developed Java?", 
            new String[]{"Microsoft", "Sun Microsystems", "IBM", "Apple"}, 2)
    };

    private int currentQuestion = 0;
    private int score = 0;
    private String playerName;
    private JFrame frame;
    private JPanel panel;
    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[4];
    private JLabel feedbackLabel;

    public QuizGUI() {
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        frame = new JFrame("Online Quiz System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("🏆 Welcome to Online Quiz System 🏆", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcome, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        JLabel nameLabel = new JLabel("Enter your name: ");
        JTextField nameField = new JTextField(15);
        JButton startButton = new JButton("Start Quiz");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(startButton);
        panel.add(inputPanel, BorderLayout.CENTER);

        startButton.addActionListener(e -> {
            playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                showQuestionScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter your name!");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showQuestionScreen() {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionsPanel.add(optionButtons[i]);
            int selectedOption = i + 1;
            optionButtons[i].addActionListener(e -> checkAnswer(selectedOption));
        }

        panel.add(optionsPanel, BorderLayout.CENTER);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(feedbackLabel, BorderLayout.SOUTH);

        updateQuestion();
        frame.revalidate();
        frame.repaint();
    }

    private void updateQuestion() {
        if (currentQuestion < quiz.length) {
            Question q = quiz[currentQuestion];
            questionLabel.setText("<html><div style='text-align:center;'>" + q.question + "</div></html>");
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(q.options[i]);
                optionButtons[i].setBackground(null);
                optionButtons[i].setEnabled(true);
            }
            feedbackLabel.setText("");
        } else {
            showResultScreen();
        }
    }

    private void checkAnswer(int selected) {
        Question q = quiz[currentQuestion];
        if (selected == q.correctOption) {
            score++;
            feedbackLabel.setText("✅ Correct!");
            feedbackLabel.setForeground(new Color(0, 128, 0));
        } else {
            feedbackLabel.setText("❌ Wrong! Correct answer: " + q.options[q.correctOption - 1]);
            feedbackLabel.setForeground(Color.RED);
        }

        for (JButton btn : optionButtons) {
            btn.setEnabled(false);
        }

        Timer timer = new Timer(1000, e -> {
            currentQuestion++;
            updateQuestion();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void showResultScreen() {
        panel.removeAll();
        panel.setLayout(new GridLayout(5, 1));

        JLabel finish = new JLabel("🎯 Quiz Finished! 🎯", SwingConstants.CENTER);
        finish.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(finish);

        JLabel nameLabel = new JLabel("Player: " + playerName, SwingConstants.CENTER);
        panel.add(nameLabel);

        JLabel scoreLabel = new JLabel("Score: " + score + " / " + quiz.length, SwingConstants.CENTER);
        panel.add(scoreLabel);

        double percentage = (double) score / quiz.length * 100;
        JLabel percentLabel = new JLabel(String.format("Percentage: %.2f%%", percentage), SwingConstants.CENTER);
        panel.add(percentLabel);

        String remark;
        if (percentage >= 80) remark = "🏆 Excellent work!";
        else if (percentage >= 50) remark = "👍 Good job!";
        else remark = "💡 Keep practicing!";

        JLabel remarkLabel = new JLabel(remark, SwingConstants.CENTER);
        panel.add(remarkLabel);

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizGUI::new);
    }

    // Inner class for questions
    static class Question {
        String question;
        String[] options;
        int correctOption;

        public Question(String question, String[] options, int correctOption) {
            this.question = question;
            this.options = options;
            this.correctOption = correctOption;
        }
    }
}
