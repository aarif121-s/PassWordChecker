import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class PasswordStrengthChecker extends JFrame {
    
    private JPasswordField passwordField;
    private JButton checkButton, clearButton;
    private JLabel strengthLabel, scoreLabel;
    private JProgressBar strengthBar;
    private JPanel criteriaPanel;
    
    private JLabel[] criteriaLabels;
    private JCheckBox[] criteriaCheckboxes;
    
    private static final String[] CRITERIA = {
        "Minimum 8 characters",
        "Contains uppercase letters",
        "Contains lowercase letters",
        "Contains numbers",
        "Contains special characters",
        "No common patterns"
    };
    
    private static final Color[] STRENGTH_COLORS = {
        new Color(220, 53, 69),   // Very Weak - Red
        new Color(255, 153, 51),  // Weak - Orange
        new Color(255, 193, 7),   // Moderate - Yellow
        new Color(40, 167, 69),   // Good - Green
        new Color(0, 123, 255)    // Strong - Blue
    };
    
  
    private static final String[] STRENGTH_TEXTS = {
        "Very Weak", "Weak", "Moderate", "Good", "Strong"
    };
    
    public PasswordStrengthChecker() {
        setTitle("Password Strength Checker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        initComponents();
        
        setSize(500, 500);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
    }
    
    private void initComponents() {
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("🔐 Password Strength Analyzer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(41, 128, 185));
        titlePanel.add(titleLabel);
        
        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("Enter Password"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Password field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkPasswordStrength();
            }
        });
        inputPanel.add(passwordField, gbc);
        
        // Show password checkbox
        gbc.gridy = 1;
        JCheckBox showPasswordCheck = new JCheckBox("Show Password");
        showPasswordCheck.addActionListener(e -> {
            JCheckBox cb = (JCheckBox) e.getSource();
            passwordField.setEchoChar(cb.isSelected() ? '\0' : '•');
        });
        inputPanel.add(showPasswordCheck, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        checkButton = new JButton("Check Strength");
        checkButton.setBackground(new Color(41, 128, 185));
        checkButton.setForeground(Color.WHITE);
        checkButton.setFocusPainted(false);
        checkButton.addActionListener(e -> checkPasswordStrength());
        
        clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(108, 117, 125));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> {
            passwordField.setText("");
            resetDisplay();
        });
        
        buttonPanel.add(checkButton);
        buttonPanel.add(clearButton);
        
        // Strength display panel
        JPanel strengthPanel = new JPanel();
        strengthPanel.setLayout(new GridLayout(2, 1, 5, 5));
        strengthPanel.setBorder(new TitledBorder("Strength Analysis"));
        
        // Strength bar
        strengthBar = new JProgressBar(0, 100);
        strengthBar.setStringPainted(true);
        strengthBar.setForeground(STRENGTH_COLORS[0]);
        
        // Strength label and score
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        strengthLabel = new JLabel("Enter a password to check");
        strengthLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        scoreLabel = new JLabel("Score: 0/100");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        labelPanel.add(strengthLabel);
        labelPanel.add(scoreLabel);
        
        strengthPanel.add(strengthBar);
        strengthPanel.add(labelPanel);
        
        // Criteria panel
        criteriaPanel = new JPanel();
        criteriaPanel.setLayout(new GridLayout(CRITERIA.length, 2, 5, 5));
        criteriaPanel.setBorder(new TitledBorder("Password Criteria"));
        
        criteriaLabels = new JLabel[CRITERIA.length];
        criteriaCheckboxes = new JCheckBox[CRITERIA.length];
        
        for (int i = 0; i < CRITERIA.length; i++) {
            criteriaLabels[i] = new JLabel(CRITERIA[i]);
            criteriaCheckboxes[i] = new JCheckBox();
            criteriaCheckboxes[i].setEnabled(false);
            
            criteriaPanel.add(criteriaLabels[i]);
            criteriaPanel.add(criteriaCheckboxes[i]);
        }
        
        // Add all panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add everything to frame
        add(mainPanel, BorderLayout.NORTH);
        add(strengthPanel, BorderLayout.CENTER);
        add(criteriaPanel, BorderLayout.SOUTH);
    }
    
    private void checkPasswordStrength() {
        char[] password = passwordField.getPassword();
        String passwordStr = new String(password);
        
        if (passwordStr.isEmpty()) {
            resetDisplay();
            return;
        }
        
        // Check each criteria
        boolean[] criteriaMet = new boolean[CRITERIA.length];
        
        // Minimum 8 characters
        criteriaMet[0] = passwordStr.length() >= 8;
        
        //Contains uppercase letters
        criteriaMet[1] = Pattern.compile("[A-Z]").matcher(passwordStr).find();
        
        // Contains lowercase letters
        criteriaMet[2] = Pattern.compile("[a-z]").matcher(passwordStr).find();
        
        //Contains numbers
        criteriaMet[3] = Pattern.compile("[0-9]").matcher(passwordStr).find();
        
        //Contains special characters
        criteriaMet[4] = Pattern.compile("[^A-Za-z0-9]").matcher(passwordStr).find();
        
        //No common patterns (simplified check)
        criteriaMet[5] = !hasCommonPatterns(passwordStr);
        
        for (int i = 0; i < criteriaCheckboxes.length; i++) {
            criteriaCheckboxes[i].setSelected(criteriaMet[i]);
            criteriaLabels[i].setForeground(criteriaMet[i] ? Color.GREEN.darker() : Color.RED);
        }
        
        int score = calculateScore(passwordStr, criteriaMet);
        
        updateStrengthDisplay(score);
    }
    
    private int calculateScore(String password, boolean[] criteriaMet) {
        int score = 0;
        
        score += Math.min(password.length() * 3, 40);
        
        int criteriaCount = 0;
        for (int i = 1; i <= 4; i++) { // Check criteria 1-4 (character types)
            if (criteriaMet[i]) criteriaCount++;
        }
        
        // Character variety (max 40 points)
        score += criteriaCount * 10;
        
        // Special characters and pattern (max 20 points)
        if (criteriaMet[4]) score += 10; // Special chars
        if (criteriaMet[5]) score += 10; // No common patterns
        
        return Math.min(score, 100);
    }
    
    private boolean hasCommonPatterns(String password) {
        String[] commonPatterns = {
            "123", "abc", "qwerty", "password", "admin", "111", "000"
        };
        
        String lowerPass = password.toLowerCase();
        for (String pattern : commonPatterns) {
            if (lowerPass.contains(pattern)) {
                return true;
            }
        }
        
        return Pattern.compile("(.)\\1{2,}").matcher(password).find();
    }
    
    private void updateStrengthDisplay(int score) {
        // Update progress bar
        strengthBar.setValue(score);
        
        int level;
        if (score < 20) level = 0;
        else if (score < 40) level = 1;
        else if (score < 60) level = 2;
        else if (score < 80) level = 3;
        else level = 4;
        
        Color barColor = STRENGTH_COLORS[level];
        strengthBar.setForeground(barColor);
        strengthLabel.setText("Strength: " + STRENGTH_TEXTS[level]);
        strengthLabel.setForeground(barColor);
        scoreLabel.setText("Score: " + score + "/100");
    }
    
    private void resetDisplay() {
        for (int i = 0; i < criteriaCheckboxes.length; i++) {
            criteriaCheckboxes[i].setSelected(false);
            criteriaLabels[i].setForeground(Color.BLACK);
        }
        
        strengthBar.setValue(0);
        strengthBar.setForeground(STRENGTH_COLORS[0]);
        strengthLabel.setText("Enter a password to check");
        strengthLabel.setForeground(Color.BLACK);
        scoreLabel.setText("Score: 0/100");
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            PasswordStrengthChecker checker = new PasswordStrengthChecker();
            checker.setVisible(true);
        });
    }
}