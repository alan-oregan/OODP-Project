package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCommand extends JFrame implements MenuCommand, ActionListener {
    private final CafeMenuGUI parent;
    private final JLabel itemLabel;
    private final JSpinner quantitySpinner;

    private final String selectedItem;
    private String addedItem;

    // Create Add to Order window
    AddCommand(CafeMenuGUI parent) {
        super("Add to Order");
        this.parent = parent;

        selectedItem = parent.getMenuList().getSelectedValue();

        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        itemLabel = new JLabel();
        itemLabel.setText(selectedItem);

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
        // Add selected item and quantity to order list
        int quantity = (int) quantitySpinner.getValue();

        addedItem = selectedItem + " x " + quantity;

        parent.getOrderListModel().addElement(addedItem);
        this.dispose();
    }

    @Override
    public void execute() {
        this.setVisible(true);
    }

    @Override
    public void undo() {
        parent.getOrderListModel().removeElement(addedItem);
    }

    @Override
    public void redo() {
        parent.getOrderListModel().addElement(addedItem);
    }
}
