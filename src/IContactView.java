import javafx.scene.paint.Color;

public interface IContactView {

	void setLabels(IContact iContact, Color color);

	void setText(String firstName, String lastName, String phoneNumber, Color color);

	void setTextField(String firstName, String lastName, String phoneNumber);

	void enableUpdate();

	void disableUpdate();

	
	

}
