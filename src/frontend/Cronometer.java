package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cronometer extends JInternalFrame implements ActionListener {
    private JButton btn = new JButton("Iniciar");
    public Cronometer() {
        setSize(300, 200);
        setLayout(new FlowLayout());

        add(btn);

        btn.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn) {

        }
    }
}
