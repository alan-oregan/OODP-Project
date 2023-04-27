package Menu;

import Singletons.OrderHandler;

public class RemoveCommand implements MenuCommand {
    // Singletons
    private final OrderHandler orderHandler = OrderHandler.GetTransactionHandler();

    private final CafeMenuGUI parent;
    private final MenuItem selectedItem;

    RemoveCommand(CafeMenuGUI parent) {
        this.parent = parent;
        selectedItem = parent.getOrderJList().getSelectedValue();
    }

    @Override
    public void execute() {
        orderHandler.removeItem(selectedItem);
    }

    @Override
    public void undo() {
        orderHandler.addItem(selectedItem);
    }

    @Override
    public void redo() {
        orderHandler.removeItem(selectedItem);
    }
}
