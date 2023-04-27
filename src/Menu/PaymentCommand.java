package Menu;

import static Menu.CafeMenuGUI.CURRENCY;

import Singletons.OrderHandler;

import Transaction.Transaction;
import Transaction.TransactionDirector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentCommand extends JFrame implements MenuCommand, ActionListener {
    private static final String[] PAYMENT_OPTIONS = {"Cash", "Card"};
    private static final String[] CARD_OPTIONS = {"Visa", "Mastercard"};

    // singletons
    private final OrderHandler orderHandler = OrderHandler.GetTransactionHandler();

    private final CafeMenuGUI parent;

    private final JLabel totalLabel;
    private final JButton payButton;

    private double total = 0;

    // Create Make a Payment window
    public PaymentCommand(CafeMenuGUI parent) {
        super("Make a Payment");
        this.parent = parent;
        this.total = orderHandler.getTotalPrice();

        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel paymentPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        totalLabel = new JLabel("Total: €0.00");
        payButton = new JButton("Pay");
        payButton.addActionListener(this);

        paymentPanel.add(totalLabel);
        paymentPanel.add(payButton);

        this.add(paymentPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double amount = 0;
        boolean valid = false;

        Transaction transaction = null;

        int paymentOptionChoice = JOptionPane.showOptionDialog(this, "How would you like to pay?", "Payment Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, PAYMENT_OPTIONS, PAYMENT_OPTIONS[0]);

        switch (PAYMENT_OPTIONS[paymentOptionChoice]) {
            case "Cash":
                while (!valid) {
                    try {
                        amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter amount to pay:"));
                        valid = true;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid amount entered");
                    }
                }
                if (amount >= total) {
                    JOptionPane.showMessageDialog(this,
                            String.format("Thank you for your payment of %s%.2f, your change is %s%.2f",
                                    CURRENCY, amount, CURRENCY, amount - total),
                            "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    transaction = TransactionDirector.getNewCashTransaction(amount, amount - total);
                    orderHandler.clearOrder();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient amount entered");
                }
                break;

            case "Card":

                int cardOptionChoice = JOptionPane.showOptionDialog(this, "What is you card type?", "Card Type",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, CARD_OPTIONS, CARD_OPTIONS[0]);

                String cardNumber = JOptionPane.showInputDialog(this, "Enter card number:");

                if (cardNumber != null) {
                    JOptionPane.showMessageDialog(this, "Thank you for your payment of €" + String.format("%.2f", total));
                    transaction = TransactionDirector.getNewCardTransaction(CARD_OPTIONS[cardOptionChoice]);
                    orderHandler.clearOrder();
                    this.dispose();
                }
                break;
        }

        orderHandler.addTransaction(transaction);
    }

    // display Make a Payment window
    @Override
    public void execute() {
        orderHandler.getTotalPrice();
        totalLabel.setText("Total: €" + String.format("%.2f", total));
        this.setVisible(true);
    }

    @Override
    public void undo() {
        // cannot undo payment
    }

    @Override
    public void redo() {
        // cannot redo payment
    }
}
