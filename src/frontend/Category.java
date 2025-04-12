package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import database.ModelGames;

public class Category extends JInternalFrame implements ActionListener, MouseListener, InternalFrameListener {
    private static final long serialVersionUID = -1072944751022628676L;
    private final JComboBox<String> cbCategory = new JComboBox<>();
    private static JList<String> jlistGames = new JList<>();
    private final JScrollPane scrListGame = new JScrollPane(jlistGames);
    private static DefaultListModel<String> modelList = new DefaultListModel<>();
    private final JButton btnEdit = new JButton("Editar");
    private final JButton btnAdd = new JButton("Añadir");

    public Category() {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/category.png"));
            this.setFrameIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
        }
        setBounds(100, 100, 310, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setClosable(true);
        setResizable(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.ipadx = 20;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(cbCategory, gbc);
        gbc.gridx++;
        add(btnEdit, gbc);
        gbc.gridx++;
        add(btnAdd, gbc);
        gbc.gridx = 0;
        gbc.gridy+=2;
        gbc.gridheight = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 3.0;
        gbc.weighty = 3.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrListGame, gbc);

        btnAdd.addActionListener(this);
        btnEdit.addActionListener(this);
        cbCategory.addActionListener(this);
        jlistGames.addMouseListener(this);

        updateData();

        this.addInternalFrameListener(this);
        setVisible(true);
    }

    private void updateGameList() {
        jlistGames.removeAll();
        modelList.clear();

        ModelGames mg = new ModelGames();
        ArrayList<String> listCategory = new ArrayList<>();
        listCategory = mg.getGamesNameListCategory(mg.getCategoryIdFromName(cbCategory.getSelectedItem().toString()));
        for(int i = 1; i < listCategory.size(); i++) {
            modelList.addElement(listCategory.get(i));
        }
        jlistGames.setModel(modelList);
        int nGames = listCategory.size() - 1;
        setTitle("Categoría " + cbCategory.getSelectedItem().toString() + " | " + nGames + " juegos");
    }

    private void updateData() {
        ModelGames mg = new ModelGames();
        ArrayList<String> category = mg.getCategoryList();
        cbCategory.removeAllItems();
        for(int i = 0; i < category.size(); i++) {
            cbCategory.addItem(category.get(i));
        }
        updateGameList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAdd) {
            String cat = JOptionPane.showInputDialog(this, "Ingrese el nombre de la categoría");
            if(cat.length() == 0) return;
            if(cat != "") {
                ModelGames mg = new ModelGames();
                int rp = mg.addCategory(cat);
                if(rp == 1) {
                    JOptionPane.showMessageDialog(this, "La categoría ha sido guardada");
                    updateData();
                } else {
                    JOptionPane.showMessageDialog(this, "Ha habido un error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == btnEdit) {
            String cat = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre de la categoría", cbCategory.getSelectedItem().toString());
            if(cat == null)
                return;
            if(cat != "") {
                ModelGames mg = new ModelGames();
                int rp = mg.editCategory(cbCategory.getSelectedItem().toString(), cat);
                if(rp == 1) {
                    JOptionPane.showMessageDialog(this, "La categoría ha sido actualizada");
                    updateData();
                } else {
                    JOptionPane.showMessageDialog(this, "Ha habido un error al actualizar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == cbCategory) {
            updateGameList();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == jlistGames && e.getClickCount() == 2) {
            ModelGames mg = new ModelGames();
            MainWindow.j.add(new EditGame(mg.getIdFromGameName(jlistGames.getSelectedValue())));
            MainWindow.j.repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void internalFrameOpened(InternalFrameEvent e) {
    }

    public void internalFrameClosing(InternalFrameEvent e) {
        jlistGames.removeMouseListener(this);
    }

    public void internalFrameClosed(InternalFrameEvent e) {
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameActivated(InternalFrameEvent e) {
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}
