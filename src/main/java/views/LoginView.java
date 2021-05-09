package views;

import controllers.LoginController;
import presenters.LoginPresenter;
import services.ImageRepositoryService;

import java.util.Scanner;

/**
 * Responsible for printing prompts onto the console via the login presenter, reading user input,
 * and communicating with the image repository service via the login controller.
 */
public class LoginView {
	private final LoginPresenter presenter;
	private final LoginController controller;
	private final Scanner inputScanner;

	public LoginView(LoginPresenter presenter, LoginController controller, Scanner inputScanner) {
		this.presenter = presenter;
		this.controller = controller;
		this.inputScanner = inputScanner;
	}

	public ImageRepositoryService run() {
		presenter.getInitialPrompt();
		inputScanner.nextLine();

		presenter.getCloudNamePrompt();
		String cloudName = inputScanner.nextLine();

		presenter.getApiKeyPrompt();
		String apiKey = inputScanner.nextLine();

		presenter.getApiSecretPrompt();
		String apiSecret = inputScanner.nextLine();

		return controller.login(cloudName, apiKey, apiSecret);
	}
}
