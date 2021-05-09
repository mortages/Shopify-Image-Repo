package presenters;

import models.ImageModel;

import java.util.List;

/**
 * Responsible for all the prompts that would appear when a user is in the main menu.
 */
public class MainMenuPresenter {
	public void getAllMenuOptions() {
		System.out.println("\nPlease select one of the following options:\n" +
				"1: Upload an image\n" +
				"2: Query image by title\n" +
				"3: View all images\n" +
				"4: Delete an image\n" +
				"5: Exit");
	}

	public void getInvalidOptionText() {
		System.out.println("Invalid choice");
	}

	public void getImageUploadPrompt() {
		System.out.println("Please select the image you would like to upload");
	}

	public void getImageTitlePrompt() {
		System.out.println("Please enter the title of your image");
	}

	public void getImageUploadSuccessText(String title) {
		System.out.printf("Successfully uploaded %s to your Cloudinary repository!%n", title);
	}

	public void getImageUploadErrorText() {
		System.out.println("Unable to complete upload");
	}

	public void getDeleteImagePrompt() {
		System.out.println("Please enter the title of the image you would like to delete");
	}

	public void getDeleteImageByTitleSuccessText(String title) {
		System.out.printf("Successfully deleted the image titled %s%n", title);
	}

	public void getDeleteImageByTitleErrorText(String title) {
		System.out.printf("Unable to delete the image titled  %s%n", title);
	}

	public void getImageByTitlePrompt() {
		System.out.println("Please enter the title image you wish to query for");
	}

	public void getImageByTitleSuccessText(ImageModel image) {
		if (image == null) {
			System.out.println("No images have the given title");
		}
		else {
			System.out.println(image);
		}
	}

	public void getImagesByTitleErrorText(String title) {
		System.out.printf("Unable to get image titled %s%n", title);
	}

	public void getAllImagesSuccessText(List<ImageModel> images) {
		System.out.printf("You have the following %d images in your repository:%n%n", images.size());
		for (ImageModel image : images) {
			System.out.println(image.toString());
		}
	}

	public void getAllImagesErrorText() {
		System.out.println("Unable to retrieve all images.");
	}
}
