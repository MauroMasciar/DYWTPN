package frontend;

import database.ModelPlayer;

import java.awt.FlowLayout;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PlayerActivities extends JInternalFrame {
    private static final long serialVersionUID = 4484286064012240569L;
    private final int HOUR_GAME = 60;
    private JTable tbPlayerActivities = new JTable();

    public PlayerActivities() {
	setSize(500, 500);
	setTitle("Actividades");
	setClosable(true);
	setResizable(false);
	
	JScrollPane scrTable = new JScrollPane(tbPlayerActivities);
	
	ModelPlayer model = new ModelPlayer();
	
	tbPlayerActivities.setModel(model.getActivities());
	
	setLayout(new FlowLayout());
	add(tbPlayerActivities);
	this.pack();
	setVisible(true);
	
    }
}
