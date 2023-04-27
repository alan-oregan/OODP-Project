package Menu;

import Singletons.OrderHandler;

public class CloseCommand implements MenuCommand {
    private final CafeMenuGUI parent;

    public CloseCommand(CafeMenuGUI parent) {
        this.parent = parent;
    }

    @Override
    public void execute() {
        if (OrderHandler.GetTransactionHandler().saveTransactions()) {
            parent.dispose();
        }
        else {
            System.out.println("Error saving transactions");
        }
    }

    @Override
    public void undo() {
        // not needed
    }

    @Override
    public void redo() {
        // not needed
    }
}
