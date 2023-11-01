package frontend;

import java.awt.FlowLayout;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.ModelPlayer;

public class PlayerHistory extends JInternalFrame {
    private static final long serialVersionUID = 4484286064012240569L;
    public static JTable tbPlayerHistory = new JTable();

    public PlayerHistory() {
	setBounds(30, 30, 500, 500);
	setTitle("Historial");
	setClosable(true);
	setResizable(true);
	
	JScrollPane scrTable = new JScrollPane(tbPlayerHistory);
	
	ModelPlayer model = new ModelPlayer();
	
	tbPlayerHistory.setModel(model.getHistory());
	
	setLayout(new FlowLayout());
	add(scrTable);
		
	setVisible(true);
    }
}
