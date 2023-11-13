package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import database.ModelGames;
import database.ModelPlayer;

public class PlayerActivities extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 4484286064012240569L;
    private JComboBox<String> cbGames = new JComboBox<String>();
    private JScrollPane scrTable = new JScrollPane(tbPlayerActivities);
    public static JTable tbPlayerActivities = new JTable();

    public PlayerActivities() {
	setBounds(90, 90, 500, 500);
	setTitle("Actividad");
	setClosable(true);
	setResizable(true);
	setLayout(new GridBagLayout());

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 1;
	gbc.ipady = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	add(cbGames, gbc);	
	gbc.gridy ++;
	add(scrTable, gbc);

	loadActivity();

	cbGames.addActionListener(this);

	setVisible(true);
    }

    public void loadActivity() {
	ArrayList<String> listGames = new ArrayList<String>();
	ModelGames mg = new ModelGames();
	listGames = mg.getGamesNameList(true);
	cbGames.removeAllItems();
	cbGames.addItem("Todos");

	try {
	    for (int i = 1; i < listGames.size(); i++) cbGames.addItem(listGames.get(i));
	} catch (ArrayIndexOutOfBoundsException ex) {
	    ex.printStackTrace();
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == cbGames) {
	    ModelPlayer model = new ModelPlayer();
	    tbPlayerActivities.setModel(model.getActivities(cbGames.getSelectedItem().toString()));
	}
    }
}
