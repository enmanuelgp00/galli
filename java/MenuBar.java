import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
	MenuBar (ActionListener actionListener) {
		super();
		JMenu menu = new JMenu("File");
		JMenuItem item = new JMenuItem("Delete");
		item.addActionListener(actionListener);
		menu.add(item);
		add(menu);
	}
}
