package frontend;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsPlayer extends JInternalFrame implements ActionListener {
	private static final long serialVersionUID = -85291059687834357L;
	private JLabel lblName = new JLabel("Nombre: ");
	private JLabel lblRegisterDate = new JLabel("Fecha de registro: ");
	private JLabel lblHoursLastDay = new JLabel("Horas ultimo día: ");
	private JLabel lblHoursLastWeek = new JLabel("Horas ultima semana: ");
	private JLabel lblHoursLastTwoWeek = new JLabel("Horas ultimas dos semanas: ");
	private JLabel lblHoursLastMonth = new JLabel("Horas ultimo mes: ");
	private JLabel lblHoursLastYear = new JLabel("Horas ultimo año: ");
	private JLabel lblHoursTotal = new JLabel("Horas totales: ");
	private JLabel lblTotalInitGames = new JLabel("Total de juegos iniciados: ");
	private JLabel lblTotalCompletedGames = new JLabel("Total de juegos completados: ");
	private JLabel lblTotalPorcentCompletedGames = new JLabel("% de juegos completados: ");
	private JLabel lblTotalSessions = new JLabel("Total de sesiones: ");
	private JLabel lblGameMostPlayedHours = new JLabel("Juego con mas horas: ");
	private JLabel lblGameMostPlayerInit = new JLabel("Juego con mas sesiones: ");
	private JLabel lblGameTopAchievement = new JLabel("Juego con mas hazañas");
	private JLabel lblGameOfYear = new JLabel("Juego del año: ");
	private JLabel lblGameOfMonth = new JLabel("Juego del mes: ");
	private JLabel lblTopLibrary = new JLabel("Biblioteca mas jugada: ");
	private JLabel lblTopPlatform = new JLabel("Plataforma mas jugada: ");
	private JLabel lblTopCategory = new JLabel("Categoria mas jugada: ");
	private JTextField txtlblName = new JTextField(20);
	private JTextField txtRegisterDate = new JTextField(20);
	private JTextField txtHoursLastDay = new JTextField(20);
	private JTextField txtHoursLastWeek = new JTextField(20);
	private JTextField txtHoursLastTwoWeek = new JTextField(20);
	private JTextField txtHoursLastMonth = new JTextField(20);
	private JTextField txtHoursLastYear = new JTextField(20);
	private JTextField txtHoursTotal = new JTextField(20);
	private JTextField txtTotalInitGames = new JTextField(20);
	private JTextField txtTotalCompletedGames = new JTextField(20);
	private JTextField txtTotalPorcentCompletedGames = new JTextField(20);
	private JTextField txtTotalSessions = new JTextField(20);
	private JTextField txtGameMostPlayedHours = new JTextField(20);
	private JTextField txtGameMostPlayerInit = new JTextField(20);
	private JTextField txtGameTopAchievement = new JTextField(20);
	private JTextField txtGameOfYear = new JTextField(20);
	private JTextField txtGameOfMonth = new JTextField(20);
	private JTextField txtTopLibrary = new JTextField(20);
	private JTextField txtTopPlatform = new JTextField(20);
	private JTextField txtTopCategory = new JTextField(20);
		
	public StatisticsPlayer() {
		try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/statisticsplayer.png"));
            this.setFrameIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
        }
        setTitle("Estadísticas del jugador");
        setSize(550, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setClosable(true);
        setResizable(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
