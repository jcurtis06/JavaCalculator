import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class CalculatorGUI implements ActionListener {
    private final LinkedList<String> expression = new LinkedList<>();
    private InputState inputState = InputState.START;
    private final JLabel label;
    private final Calculator calculate;

    public CalculatorGUI() {
        calculate = new Calculator();
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(20, 20, 20));

        JPanel output = new JPanel();
        output.setBackground(new Color(20, 20, 20));
        output.setPreferredSize(new Dimension(400, 100));

        label = new JLabel("...");
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 36));
        label.setPreferredSize(new Dimension(300, 100));
        output.add(label);

        JPanel buttons = new JPanel(new GridLayout(5, 4, 5, 5));
        buttons.setBackground(new Color(20, 20, 20));

        String[] symbols = {"7", "8", "9", "x", "4", "5", "6", "+", "1", "2", "3", "/", ".", "0", "-", "=", "(", ")"};
        for (String symbol : symbols) {
            buttons.add(createButton(symbol));
        }
        
        JButton empty = createButton("");
        empty.setEnabled(false);
        buttons.add(empty);
        buttons.add(createButton("CLEAR"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, output, buttons);
        splitPane.setDividerSize(0);

        frame.add(splitPane);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CalculatorGUI();
    }
    
    private void updateLabel() {
        StringBuilder strBuilder = new StringBuilder();
        for (String str : expression) {
            strBuilder.append(str).append("");
        }
        label.setText(strBuilder.toString());
    }

    private JButton createButton(String text) {
        JButton btn = new JButton();
        btn.addActionListener(this);
        btn.setText(text);
        btn.setForeground(new Color(255, 255, 255));
        btn.setBackground(new Color(40, 40, 40));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        char symbol = button.getText().charAt(0);
        
        if (inputState == InputState.START) {
            expression.add(button.getText());
            inputState = InputState.NUMBER;
            updateLabel();
            return;
        }

        switch (symbol) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                boolean shouldAdd = inputState == InputState.NUMBER || inputState == InputState.NEGATIVE || inputState == InputState.DECIMAL;
                if (shouldAdd) expression.set(expression.size() - 1, expression.get(expression.size() - 1) + symbol);
                else expression.add(button.getText());

                inputState = InputState.NUMBER;
                break;
            }
            case '.' -> {
                if (InputState.NUMBER == inputState) expression.set(expression.size() - 1, expression.get(expression.size() - 1) + symbol);
                else expression.add(button.getText());
                
                inputState = InputState.DECIMAL;
                break;
            }
            case '+', '/', 'x', '(', ')' -> {
                expression.add(button.getText());
                inputState = InputState.OPERATOR;
                break;
            }
            case '-' -> {
                if (inputState != InputState.NUMBER) inputState = InputState.NEGATIVE;
                else inputState = InputState.OPERATOR;

                expression.add(button.getText());
                break;
            }
            case '=' -> {
                label.setText(calculate.calculate(expression));
                expression.clear();
                return;
            }
            case 'C' -> {
                expression.clear();
                inputState = InputState.START;
                break;
            }
        }
        updateLabel();
    }

    enum InputState {
        NUMBER, OPERATOR, NEGATIVE, DECIMAL, START
    }
}
