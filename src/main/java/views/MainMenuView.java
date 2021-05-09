package views;

import controllers.MainMenuController;
import models.ImageModel;
import presenters.MainMenuPresenter;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Responsible for printing prompts onto the console via the main menu presenter, reading user input,
 * and communicating with the image repository service via the main menu controller.
 */
public class MainMenuView {
	MainMenuPresenter presenter;
	MainMenuController controller;
	Scanner inputScanner;
	JFileChooser fileChooser;

	public MainMenuView(MainMenuPresenter presenter, MainMenuController controller, Scanner inputScanner, JFileChooser fileChooser) {
		this.presenter = presenter;
		this.controller = controller;
		this.inputScanner = inputScanner;
		this.fileChooser = fileChooser;
	}

	/**
	 * Runs the appropriate view given the user input.
	 * @param choice The selection that the user made
	 */
	public void handleInput(int choice) {
		switch (choice) {
			case 1:
				File file = selectImage();
				runUploadImageView(file);
				break;
			case 2:
				runGetImageByTitleView();
				break;
			case 3:
				runGetAllImagesView();
				break;
			case 4:
				runDeleteImageByTitleView();
				break;
			case 5:
				System.exit(0);
			default:
				presenter.getInvalidOptionText();
		}
	}

	/**
	 * Runs the view that presents all the menu options to the user.
	 */
	public void run() {
		presenter.getAllMenuOptions();
		String choice = inputScanner.nextLine();
		try {
			handleInput(Integer.parseInt(choice));
		} catch (Exception e) {
			presenter.getInvalidOptionText();
		}
	}

	/**
	 * Runs the view for getting all images in the repo.
	 */
	public void runGetAllImagesView() {
		List<ImageModel> images;
		try {
			images = controller.getAllImages();
			presenter.getAllImagesSuccessText(images);
		} catch (Exception e) {
			presenter.getAllImagesErrorText();
		}
	}

	/**
	 * Runs the view for uploading an image to the repo.
	 * @param file The image file to upload to the repo.
	 */
	public void runUploadImageView(File file) {
		if (file != null) {
			presenter.getImageTitlePrompt();
			String title = inputScanner.nextLine();
			try {
				controller.uploadImage(file, title);
				presenter.getImageUploadSuccessText(title);
			} catch (IOException e) {
				presenter.getImageUploadErrorText();
			}
		} else {
			presenter.getImageUploadErrorText();
		}
	}

	/**
	 * Displays a file chooser dialog for the user to select a file.
	 * @return The file that the user selected.
	 */
	private File selectImage() {
		presenter.getImageUploadPrompt();
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else {
			return null;
		}
	}

	/**
	 * Runs the view for getting an image by title.
	 */
	public void runGetImageByTitleView() {
		ImageModel image;

		presenter.getImageByTitlePrompt();
		String title = inputScanner.nextLine();
		try {
			image = controller.getImageByTitle(title);
			presenter.getImageByTitleSuccessText(image);
		} catch (Exception e) {
			presenter.getImagesByTitleErrorText(title);
		}
	}

	/**
	 * Runs the view for deleting an image by title.
	 */
	public void runDeleteImageByTitleView() {
		presenter.getDeleteImagePrompt();
		String title = inputScanner.nextLine();
		try {
			controller.deleteImageByTitle(title);
			presenter.getDeleteImageByTitleSuccessText(title);
		} catch (Exception e) {
			presenter.getDeleteImageByTitleErrorText(title);
		}
	}
}
