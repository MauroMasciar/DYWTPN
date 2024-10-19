package frontend;

import database.ModelGames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

public final class Test extends JPanel {
    /*public static void main(String[] args) {
	JFrame frame = new JFrame("CheckedComboBox");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.getContentPane().add(new Test());
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }*/

    private Test() {
        super(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(0, 1));
        p.add(new CheckedComboBox<>(makeModel()));
        add(p, BorderLayout.NORTH);
        setPreferredSize(new Dimension(320, 240));
    }
    
    /*public int addSessionGame(int gameId, String name, int minsPlayed, String date) {
        int resultado = 0;
        try {
            String query = "INSERT INTO games_sessions_history (game_id, game_name, mins, datetime) VALUES (?,?,?,?)";
            conex = DriverManager.getConnection(Data.url, Data.username, Data.password);
            PreparedStatement p = conex.prepareStatement(query);
            p.setInt(1, gameId);
            p.setString(2, name);
            p.setString(3, String.valueOf(minsPlayed));
            p.setString(4, date);
            resultado = p.executeUpdate();
            int secs = getSecondsPlayed(gameId);
            secs += minsPlayed * 60;
            int r = setTimePlayed(gameId, secs);
            if(r == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido sumar el tiempo jugado", "Error al guardar los datos", JOptionPane.ERROR_MESSAGE);
            } else {
                addTimeLibrary(gameId, minsPlayed * 60);
                addSessionLibrary(gameId);
                addTimePlatform(gameId, minsPlayed * 60);
                addSessionPlatform(gameId);
            }
            p.close();
            conex.close();
        } catch (SQLException ex) {
            Log.Loguear(ex.getMessage());
            ex.printStackTrace();
            return 0;
        }
        return resultado;
    }*/

    private static ComboBoxModel<CheckableItem> makeModel() {
        ModelGames mg = new ModelGames();
        ArrayList<String> games = new ArrayList<>();
        games = mg.getGamesNameList(true, false, false);

        CheckableItem[] m = {
                new CheckableItem(games.get(1), false),
                new CheckableItem("bb", true),
                new CheckableItem("111", false),
                new CheckableItem("33333", true),
                new CheckableItem("2222", true),
                new CheckableItem("c", false)
        };
        return new DefaultComboBoxModel<>(m);
    }
}
