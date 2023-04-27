package Menu;

import Singletons.OrderHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCommand extends JFrame implements MenuCommand, ActionListener {
    // Singletons
    private final OrderHandler orderHandler = OrderHandler.GetOrderHandler();

    private final CafeMenuGUI parent;
    private final JLabel itemLabel;
    private final JSpinner quantitySpinner;

    private MenuItem selectedItem;
    private MenuItem addedItem;

    // Create Add to Order window
    AddCommand(CafeMenuGUI parent) {
        super("Add to Order");
        this.parent = parent;

        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        itemLabel = new JLabel();

        JLabel quantityLabel = new JLabel("Quantity:");
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);

        JPanel addToOrderPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        addToOrderPanel.add(itemLabel);
        addToOrderPanel.add(quantityLabel);

        addToOrderPanel.add(quantitySpinner);
        addToOrderPanel.add(addButton);

        this.add(addToOrderPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.selectedItem = parent.getMenuJList().getSelectedValue();

        if (selectedItem != null) {

            itemLabel.setText(selectedItem.toString());

            // Add selected item and quantity to order list
            int quantity = (int) quantitySpinner.getValue();

            addedItem = selectedItem;

            orderHandler.addItem(addedItem);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to add to your order.", "No item selected",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void execute() {
        this.setVisible(true);
    }

    @Override
    public void undo() {
        orderHandler.removeItem(addedItem);
    }

    @Override
    public void redo() {
        orderHandler.addItem(addedItem);
    }
}
