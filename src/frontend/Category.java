package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import database.ModelGames;

public class Category extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = -1072944751022628676L;
    private JLabel lblCategory = new JLabel("Categoria:");
    private final JComboBox<String> cbCategory = new JComboBox<String>();
    private final JButton btnEdit = new JButton("Editar");
    private final JButton btnAdd = new JButton("Añadir");

    public Category() {
	setTitle("Categorias");
	setBounds(100, 100, 400, 230);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setClosable(true);
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 40;
	gbc.ipady = 40;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	add(lblCategory, gbc);
	gbc.gridx++;
	add(cbCategory, gbc);
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

    public void updateData() {
	ModelGames mg = new ModelGames();
	ArrayList<String> category = mg.getCategoryList();
	cbCategory.removeAllItems();
	for (int i = 0; i < category.size(); i++)
	    cbCategory.addItem(category.get(i));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == btnAdd) {
	    String cat = JOptionPane.showInputDialog(this, "Ingrese el nombre de la categoria");
	    if (cat.length() == 0)
		return;
	    if (cat != "") {
		ModelGames mg = new ModelGames();
		int rp = mg.addCategory(cat);
		if (rp == 1) {
		    JOptionPane.showMessageDialog(this, "La categoria ha sido guardada");
		    updateData();
		} else {
		    JOptionPane.showMessageDialog(this, "Ha habido un error al guardar los datos", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
	} else if (e.getSource() == btnEdit) {
	    String cat = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre de la categoria",
		    cbCategory.getSelectedItem().toString());
	    if (cat == null)
		return;
	    if (cat != "") {
		ModelGames mg = new ModelGames();
		int rp = mg.editCategory(cbCategory.getSelectedItem().toString(), cat);
		if (rp == 1) {
		    JOptionPane.showMessageDialog(this, "La categoria ha sido actualizada");
		    updateData();
		} else {
		    JOptionPane.showMessageDialog(this, "Ha habido un error al actualizar los datos", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
	}
    }
}
