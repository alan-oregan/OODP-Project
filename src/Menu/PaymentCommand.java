package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentCommand extends JFrame implements MenuCommand, ActionListener {
    private final CafeMenuGUI parent;

    private final JLabel totalLabel;
    private final JButton payButton;

    private double total = 0;

    // Create Make a Payment window
    public PaymentCommand(CafeMenuGUI parent) {
        super("Make a Payment");
        this.parent = parent;

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

    // Display payment confirmation
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Thank you for your payment of €" + String.format("%.2f", total));
        parent.getOrderListModel().clear();
        this.dispose();
    }

    // Calculate total and display Make a Payment window
    @Override
    public void execute() {
        for (int i = 0; i < parent.getOrderListModel().getSize(); i++) {
            String orderItem = parent.getOrderListModel().getElementAt(i);
            String[] orderParts = orderItem.split(" x ");
            String itemName = orderParts[0];
            int quantity = Integer.parseInt(orderParts[1]);
            switch (itemName) {
                case "Espresso" -> total += 1.50 * quantity;
                case "Latte" -> total += 2.50 * quantity;
                case "Cappuccino" -> total += 3.00 * quantity;
                case "Mocha" -> total += 3.50 * quantity;
            }
        }
        totalLabel.setText("Total: €" + String.format("%.2f", total));
        this.setVisible(true);
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
