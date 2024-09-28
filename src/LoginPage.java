import javax.swing.*;
import java.awt.*;

public class LoginPage extends JDialog {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private boolean loginSuccessful = false;

    // Constructor with the correct access to the parent frame
    public LoginPage(Frame parent) {
        super(parent, "Login", true);  // Call JDialog constructor with parent frame and modal
        setLayout(new BorderLayout());
        setSize(300, 200);
        setLocationRelativeTo(parent);  // Center relative to the parent frame

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Create a panel for the button with FlowLayout to center it
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = getButton();
        buttonPanel.add(loginButton);  // Add the button to the button panel

        panel.add(new JPanel());  // Empty placeholder for grid alignment
        panel.add(buttonPanel);    // Add the button panel to the main panel

        add(panel, BorderLayout.CENTER);
    }

    private JButton getButton() {
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(_ -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("user") && password.equals("password")) {
                JOptionPane.showMessageDialog(LoginPage.this, "Login Successful!");
                loginSuccessful = true;
                dispose();  // Close the dialog if login is successful
            } else {
                JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password!");
            }
        });
        return loginButton;
    }

    // Method to return whether the login was successful
    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame but don't display it yet
            PomodoroTimer mainFrame = new PomodoroTimer();

            // Show the login dialog, passing the mainFrame as parent
            LoginPage loginPage = new LoginPage(mainFrame);
            loginPage.setVisible(true);  // Display the login page

            // Check if login was successful
            if (loginPage.isLoginSuccessful()) {
                mainFrame.setVisible(true);  // Show the main application window if login is successful
            } else {
                System.exit(0);  // Exit if login is not successful
            }
        });
    }
}

