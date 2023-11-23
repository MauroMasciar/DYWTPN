package backend;

import javax.swing.JTextField;

public class Validations {
	public static boolean isEmpty(JTextField textField) {
		if(!textField.getText().isEmpty() && !textField.getText().isBlank()) {
			return false;
		}
		return true;
	}
}
