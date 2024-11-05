package frontend;

import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.PrintWriter;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

public class Notes extends JInternalFrame implements KeyListener {
    private JTextArea txtaNotes = new JTextArea();
    private JScrollPane scr = new JScrollPane(txtaNotes);
    private File f = new File("notes.txt");
    
    public Notes() {
        /*try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/notes.png"));
            this.setFrameIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
        }*/
        
        setBounds(30, 30, 500, 500);
        setTitle("Notas");
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
        add(scr, gbc);
        
        txtaNotes.addKeyListener(this);
        
        loadNote();
        
        setVisible(true);
    }

    private void loadNote() {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                txtaNotes.append(line);
                txtaNotes.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        try {
            FileWriter fw = new FileWriter("notes.txt", false);
            PrintWriter pw = new PrintWriter(fw);
            String s = txtaNotes.getText();
            pw.println(s);
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
