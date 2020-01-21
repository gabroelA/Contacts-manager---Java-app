// Name: Gavriel Arbov Id: 311846208

import java.io.FileNotFoundException;
import java.io.IOException;


import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			
			ContactsManager cm = new ContactsManager("contacts.dat");

			IContactView cmf = new ContactsManagerFrame();
			
			IContactView cmjfx = new ContactsManagerJFX(primaryStage);
			
			ContactsController cc = new ContactsController(cm);
			
			cc.addView(cmf);
			
			cc.addView(cmjfx);	
			
		//	cmf.init();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
}
