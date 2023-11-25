package backend;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Validations {
    public static boolean isEmpty(JTextField textField) {
	if(!textField.getText().isEmpty() && !textField.getText().isBlank()) {
	    return false;
	}
	return true;
    }
    public static boolean isEmpty(JTextArea textArea) {
	if(!textArea.getText().isEmpty() && !textArea.getText().isBlank()) {
	    return false;
	}
	return true;
    }
}
