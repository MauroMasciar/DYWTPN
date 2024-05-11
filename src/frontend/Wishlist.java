package frontend;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Wishlist extends JInternalFrame implements ActionListener {
    private JList<String> lGames = new JList<>();
    private JScrollPane scrLGames = new JScrollPane(lGames);
    private JButton btnNew = new JButton("Nuevo");
    
    public Wishlist() {
        setTitle("Lista de deseos");
        setBounds(100, 100, 300, 200);
        setClosable(true);
        setResizable(true);
        
        add(scrLGames);
        add(btnNew);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        
    }
}
