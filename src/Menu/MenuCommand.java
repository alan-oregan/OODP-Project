package Menu;

// base command interface for menu button actions
public interface MenuCommand {
    void execute();

    void undo();

    void redo();
}
