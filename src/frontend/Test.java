package frontend;

import database.ModelGames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

public final class Test extends JPanel {
    /*public static void main(String[] args) {
	JFrame frame = new JFrame("CheckedComboBox");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.getContentPane().add(new Test());
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }*/

    private Test() {
	super(new BorderLayout());
	JPanel p = new JPanel(new GridLayout(0, 1));
	p.add(new CheckedComboBox<>(makeModel()));
	add(p, BorderLayout.NORTH);
	setPreferredSize(new Dimension(320, 240));
    }

    private static ComboBoxModel<CheckableItem> makeModel() {
	ModelGames mg = new ModelGames();
	ArrayList<String> games = new ArrayList<>();
	games = mg.getGamesNameList(true);
	
	CheckableItem[] m = {
		new CheckableItem(games.get(1), false),
		new CheckableItem("bb", true),
		new CheckableItem("111", false),
		new CheckableItem("33333", true),
		new CheckableItem("2222", true),
		new CheckableItem("c", false)
	};
	return new DefaultComboBoxModel<>(m);
    }
}
