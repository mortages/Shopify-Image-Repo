import controllers.LoginController;
import controllers.MainMenuController;
import presenters.LoginPresenter;
import presenters.MainMenuPresenter;
import services.ImageRepositoryService;
import views.LoginView;
import views.MainMenuView;

import javax.swing.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner inputScanner = new Scanner(System.in);
		JFileChooser fileChooser = new JFileChooser();

		LoginController loginController = new LoginController();
		LoginPresenter loginPresenter = new LoginPresenter();
		LoginView loginView = new LoginView(loginPresenter, loginController, inputScanner);

		ImageRepositoryService imageRepoService = loginView.run();

		MainMenuController mainMenuController = new MainMenuController(imageRepoService);
		MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();
		MainMenuView mainMenuView = new MainMenuView(mainMenuPresenter, mainMenuController, inputScanner, fileChooser);

		while (true) {
			mainMenuView.run();
		}
	}


}
