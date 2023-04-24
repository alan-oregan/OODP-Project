package Menu;

public class RemoveCommand implements MenuCommand  {

    private final CafeMenuGUI parent;

    private final String selectedItem;

    RemoveCommand(CafeMenuGUI parent) {
        this.parent = parent;
        selectedItem = parent.getOrderList().getSelectedValue();
    }

    @Override
    public void execute() {
        parent.getOrderListModel().removeElement(selectedItem);
    }

    @Override
    public void undo() {
        parent.getOrderListModel().addElement(selectedItem);
    }

    @Override
    public void redo() {
        parent.getOrderListModel().removeElement(selectedItem);
    }
}
