package Menu;

import Singletons.FileHandler;
import Singletons.OrderHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

/*
 * Main GUI for the Café Menu
 * Displays the menu and order lists
 * Allows the user to add and remove items from the order list
 * Allows the user to make a payment
 */
public class CafeMenuGUI extends JFrame implements ActionListener {

    // Constants for menu list
    public static final String CURRENCY = "€";

    // singletons
    private final FileHandler fileHandler = FileHandler.GetFileHandler();
    private final OrderHandler orderHandler = OrderHandler.GetTransactionHandler();

    // stacks for undo/redo
    private final Stack<MenuCommand>
            redoStack = new Stack<>(),
            undoStack = new Stack<>();

    private final JButton addOrderButton, removeOrderButton, paymentButton, exitButton, undoButton, redoButton;
    private final JList<MenuItem> menuList, orderList;

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
        JMenuBar menuBar = new JMenuBar();

        //Add a menu items
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");
        JMenuItem exit = new JMenuItem("Exit");

        //Add action listeners
        undo.addActionListener(this);
        redo.addActionListener(this);
        exit.addActionListener(this);

        // Create components
        JLabel titleLabel = new JLabel("Cafe Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        menuList = new JList<>(fileHandler.getMenuItems());
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane menuScrollPane = new JScrollPane(menuList);

        orderList = new JList<>(orderHandler.getOrderItems());
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

        // Add menu bar to frame
        setJMenuBar(menuBar);

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

    // accessors
    public JList<MenuItem> getMenuJList() {
        return menuList;
    }

    public JList<MenuItem> getOrderJList() {
        return orderList;
    }

    // handles button clicks
    @Override
    public void actionPerformed(ActionEvent e) {

        // menu button commands
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
            undoStack.clear();
            redoStack.clear();
            return;
        }

        if (e.getSource() == exitButton) {
            executeCommand(new CloseCommand(this));
            return;
        }

        // undo and redo buttons
        if (e.getSource() == undoButton) {
            if (undoStack.isEmpty()) {
                return;
            }
            redoStack.push(undoStack.pop()).undo();
            return;
        }

        if (e.getSource() == redoButton) {
            if (redoStack.isEmpty()) {
                return;
            }
            undoStack.push(redoStack.pop()).redo();
        }
    }

    // executes a command and adds it to the undoStack
    public void executeCommand(MenuCommand command) {
        command.execute();
        undoStack.push(command);
    }
}