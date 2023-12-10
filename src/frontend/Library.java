package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import database.ModelGames;

public class Library extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = -1072944751022628676L;
    private final JLabel lblInfo = new JLabel(" Aqui tiene la lista de bibliotecas, puede seleccionarla");
    private final JLabel lblInfo2 = new JLabel("y usar editar o añadir una nueva");
    private final JLabel lblLibrary = new JLabel("Biblioteca:");
    private final JComboBox<String> cbLibrary = new JComboBox<>();
    private final JButton btnEdit = new JButton("Editar");
    private final JButton btnAdd = new JButton("Añadir");

    public Library() {
	try {
	    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/library.png"));
	    this.setFrameIcon(icon);
	} catch (Exception ex) {
	    ex.printStackTrace();
	    JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
	}
	setTitle("Bibliotecas");
	setBounds(100, 100, 295, 140);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);
	setResizable(true);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridheight = 1;
	gbc.gridwidth = 2;
	gbc.weightx = 2.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 20;
	gbc.ipady = 20;
	gbc.fill = GridBagConstraints.NONE;
	gbc.gridx = 0;
	gbc.gridy = 0;
	add(lblInfo, gbc);
	gbc.gridy++;
	add(lblInfo2, gbc);
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.gridy++;
	add(lblLibrary, gbc);
	gbc.gridx++;
	add(cbLibrary, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	add(btnEdit, gbc);
	gbc.gridx++;
	add(btnAdd, gbc);

	btnAdd.addActionListener(this);
	btnEdit.addActionListener(this);

	updateData();

	setVisible(true);
    }

    private void updateData() {
	ModelGames mg = new ModelGames();
	ArrayList<String> library = mg.getLibraryList();
	cbLibrary.removeAllItems();
	for (int i = 0; i < library.size(); i++) {
	    cbLibrary.addItem(library.get(i));
	}   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == btnAdd) {
	    String cat = JOptionPane.showInputDialog(this, "Ingrese el nombre de la biblioteca");
	    if (cat.length() == 0)
		return;
	    if (cat != "") {
		ModelGames mg = new ModelGames();
		int rp = mg.addLibrary(cat);
		if (rp == 1) {
		    JOptionPane.showMessageDialog(this, "La biblioteca ha sido guardada");
		    updateData();
		} else {
		    JOptionPane.showMessageDialog(this, "Ha habido un error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	} else if (e.getSource() == btnEdit) {
	    String cat = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre de la biblioteca", cbLibrary.getSelectedItem().toString());
	    if (cat == null)
		return;
	    if (cat != "") {
		ModelGames mg = new ModelGames();
		int rp = mg.editLibrary(cbLibrary.getSelectedItem().toString(), cat);
		if (rp == 1) {
		    JOptionPane.showMessageDialog(this, "La biblioteca ha sido actualizada");
		    updateData();
		} else {
		    JOptionPane.showMessageDialog(this, "Ha habido un error al actualizar los datos", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	}
    }
}
