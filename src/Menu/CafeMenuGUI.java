package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class CafeMenuGUI extends JFrame implements ActionListener {

    // Declare variables for components and data
    private final String[] menuItems = {"Espresso", "Latte", "Cappuccino", "Mocha"};

    private final JLabel titleLabel;
    private final JButton addOrderButton, removeOrderButton, paymentButton, exitButton, undoButton, redoButton;

    private final JList<String> menuList;
    private final JList<String> orderList;
    private final DefaultListModel<String> orderListModel;

    private Stack<MenuCommand> redoStack = new Stack<>();
    private Stack<MenuCommand> undoStack = new Stack<>();

    public JList<String> getMenuList() {
        return menuList;
    }

    public JList<String> getOrderList() {
        return orderList;
    }

    public DefaultListModel<String> getOrderListModel() {
        return orderListModel;
    }

    public CafeMenuGUI() {
        // Set window properties
        setTitle("Cafe Menu");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panels for components
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create components
        titleLabel = new JLabel("Cafe Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        menuList = new JList<>(menuItems);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane menuScrollPane = new JScrollPane(menuList);

        orderListModel = new DefaultListModel<>();
        orderList = new JList<>(orderListModel);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane orderScrollPane = new JScrollPane(orderList);

        addOrderButton = new JButton("Add to Order");
        removeOrderButton = new JButton("Remove from Order");
        paymentButton = new JButton("Make a Payment");
        exitButton = new JButton("Exit");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");

        // Add components to panels
        topPanel.add(titleLabel);

        centerPanel.add(menuScrollPane);
        centerPanel.add(orderScrollPane);

        bottomPanel.add(addOrderButton);
        bottomPanel.add(removeOrderButton);
        bottomPanel.add(paymentButton);
        bottomPanel.add(exitButton);
        bottomPanel.add(undoButton);
        bottomPanel.add(redoButton);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        addOrderButton.addActionListener(this);
        removeOrderButton.addActionListener(this);
        paymentButton.addActionListener(this);
        exitButton.addActionListener(this);
        undoButton.addActionListener(this);
        redoButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addOrderButton) {
            executeCommand(new AddCommand(this));
            return;
        }

        if (e.getSource() == removeOrderButton) {
            executeCommand(new RemoveCommand(this));
            return;
        }

        if (e.getSource() == paymentButton) {
            executeCommand(new PaymentCommand(this));
            return;
        }

        if (e.getSource() == exitButton) {
            executeCommand(new CloseCommand(this));
        }

        if (e.getSource() == undoButton) {
            redoStack.push(undoStack.pop()).undo();
        }

        if (e.getSource() == redoButton) {
            undoStack.push(redoStack.pop()).redo();
        }
    }

    public void executeCommand(MenuCommand command) {
        command.execute();
        undoStack.push(command);
    }

    public static void main(String[] args) {
        // Create and show Cafe Menu GUI
        CafeMenuGUI cafeMenu = new CafeMenuGUI();
        cafeMenu.setVisible(true);
    }
}