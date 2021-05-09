package services;

import models.ImageModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * An interface for image repository APIs.
 */
public interface ImageRepositoryService {
	void uploadImage(File file, String title) throws IOException;

	void deleteImageByTitle(String title) throws Exception;

	ImageModel getImageByTitle(String title) throws Exception;

	List<ImageModel> getAllImages() throws Exception;
}

