package Menu;

public class CloseCommand implements MenuCommand {
    private final CafeMenuGUI parent;

    public CloseCommand(CafeMenuGUI parent) {
        this.parent = parent;
    }

    @Override
    public void execute() {
        parent.dispose();
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
