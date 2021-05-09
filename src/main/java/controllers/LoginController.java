package controllers;

import services.CloudinaryImageRepository;
import services.ImageRepositoryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * Responsible for initializing the image repository service given the user's credentials.
 */
public class LoginController {
	public ImageRepositoryService login(String cloudName, String apiKey, String apiSecret) {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", cloudName,
				"api_key", apiKey,
				"api_secret", apiSecret));
		return new CloudinaryImageRepository(cloudinary);
	}
}
