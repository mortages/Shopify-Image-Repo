package services;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import models.ImageModel;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Implements an image repository service using Cloudinary (https://cloudinary.com/documentation)
 */
public class CloudinaryImageRepository implements ImageRepositoryService {
	private final Cloudinary cloudinary;

	public CloudinaryImageRepository(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	/**
	 * Uploads an image to the Cloudinary server.
	 * @param file The local image file to be uploaded.
	 * @param imageTitle A title for the image.
	 * @throws IOException if the API call fails.
	 */
	public void uploadImage(File file, String imageTitle) throws IOException {
		cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", imageTitle));
	}

	/**
	 * Deletes the image with the given title in the Cloudinary repo. If no image with the given title exists,
	 * an Exception is thrown.
	 * @param title The title of the image to be deleted.
	 * @throws Exception if the API call fails.
	 */
	@Override
	public void deleteImageByTitle(String title) throws Exception {
		ApiResponse response = cloudinary.api().deleteResources(Arrays.asList(title), ObjectUtils.emptyMap());

		if (((Map<Object, Object>) response.get("deleted")).getOrDefault(title, "not_found").equals("not_found")) {
			throw new Exception("No image with the given title");
		}
	}

	/**
	 * Queries the Cloudinary server for the image with the given title and returns an ImageModel if it exists.
	 * If it doesn't exist, an exception is thrown.
	 * @param title THe title of the image to be retrieved.
	 * @throws Exception if the API call fails.
	 */
	@Override
	public ImageModel getImageByTitle(String title) throws Exception {
		ImageModel image = null;
		ApiResponse response = cloudinary.search()
				.expression("public_id=" + title)
				.maxResults(500)
				.execute();

		List<Map<Object, Object>> resources = (List<Map<Object, Object>>) response.get("resources");
		for (Map<Object, Object> resource : resources) {
			if (title.equals(resource.get("public_id"))) {
				String url = (String) resource.get("secure_url");
				Instant createdAt = Instant.parse(((String) resource.get("created_at")).replace("+00:00", "Z"));
				image = new ImageModel(url, title, createdAt);
			}
		}
		return image;
	}

	/**
	 * Queries the Cloudinary server for all images and returns a list of ImageModels.
	 * @throws Exception if the API call fails.
	 */
	public List<ImageModel> getAllImages() throws Exception {
		List<ImageModel> images = new ArrayList<ImageModel>();
		ApiResponse response = cloudinary.api().resources(ObjectUtils.asMap(
				"type", "upload",
				"max_results", "500"));

		List<Map<Object, Object>> resources = (List<Map<Object, Object>>) response.get("resources");
		for (Map<Object, Object> image : resources) {
			String url = (String) image.get("secure_url");
			String title = (String) image.get("public_id");
			Instant createdAt = Instant.parse((String) image.get("created_at"));
			images.add(new ImageModel(url, title, createdAt));
		}

		return images;
	}
}
