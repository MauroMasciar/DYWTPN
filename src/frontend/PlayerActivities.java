package frontend;

import database.ModelPlayer;

import java.awt.FlowLayout;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PlayerActivities extends JInternalFrame {
    private static final long serialVersionUID = 4484286064012240569L;
    public static JTable tbPlayerActivities = new JTable();

    public PlayerActivities() {
	setBounds(30, 30, 500, 500);
	setTitle("Actividades");
	setClosable(true);
	setResizable(true);
	
	JScrollPane scrTable = new JScrollPane(tbPlayerActivities);
	
	ModelPlayer model = new ModelPlayer();
	
	tbPlayerActivities.setModel(model.getActivities());
	
	setLayout(new FlowLayout());
	add(scrTable);
		
	setVisible(true);
    }
}
