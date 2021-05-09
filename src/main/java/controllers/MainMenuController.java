package controllers;

import models.ImageModel;
import services.ImageRepositoryService;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Responsible for communicating with the image repository service.
 */
public class MainMenuController {
	private final ImageRepositoryService imageRepoService;

	public MainMenuController(ImageRepositoryService imageRepoService) {
		this.imageRepoService = imageRepoService;
	}

	public void uploadImage(File file, String title) throws IOException {
		imageRepoService.uploadImage(file, title);
	}

	public void deleteImageByTitle(String title) throws Exception {
		imageRepoService.deleteImageByTitle(title);
	}

	public ImageModel getImageByTitle(String title) throws Exception {
		return imageRepoService.getImageByTitle(title);
	}

	public List<ImageModel> getAllImages() throws Exception {
		return imageRepoService.getAllImages();
	}
}
