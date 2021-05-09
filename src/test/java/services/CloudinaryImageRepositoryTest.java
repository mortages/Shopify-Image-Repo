package services;

import com.cloudinary.Search;
import models.ImageModel;
import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CloudinaryImageRepositoryTest {
	private static final File FILE = new File("pathToMyCoolImage");
	private static final String IMAGE_TITLE = "A cool title";
	private static final String IMAGE_URL = "www.someCoolImage.com";

	private CloudinaryImageRepository subject;

	@Mock
	private Cloudinary cloudinary;

	@Mock
	private Uploader uploader;

	@Mock
	private Api api;

	@Mock
	private ApiResponse apiResponse;

	@Mock
	private Search cloudinarySearch;

	@BeforeEach
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		subject = new CloudinaryImageRepository(cloudinary);

		when(cloudinary.uploader()).thenReturn(uploader);
		when(cloudinary.api()).thenReturn(api);

		when(api.resources(ArgumentMatchers.any())).thenReturn(apiResponse);
		when(api.deleteResources(anyIterable(), anyMap())).thenReturn(apiResponse);

		when(cloudinary.search()).thenReturn(cloudinarySearch);
		when(cloudinarySearch.expression(anyString())).thenReturn(cloudinarySearch);
		when(cloudinarySearch.withField(anyString())).thenReturn(cloudinarySearch);
		when(cloudinarySearch.maxResults(anyInt())).thenReturn(cloudinarySearch);
		when(cloudinarySearch.execute()).thenReturn(apiResponse);
	}

	@Test
	@DisplayName("Verifies the the cloudinary API is called with the correct parameters when uploading an image.")
	public void uploadImageShouldSucceed() throws IOException {
		Map expectedOptions = ObjectUtils.asMap("public_id", IMAGE_TITLE);

		subject.uploadImage(FILE, IMAGE_TITLE);

		Mockito.verify(uploader).upload(FILE, expectedOptions);
	}

	@Test
	@DisplayName("Verifies the the cloudinary API is called with the correct parameters when deleting an image.")
	public void deleteImageShouldSucceed() throws Exception {
		when(apiResponse.get("deleted")).thenReturn(ObjectUtils.asMap(IMAGE_TITLE, "found"));

		subject.deleteImageByTitle(IMAGE_TITLE);

		Mockito.verify(api).deleteResources(Arrays.asList(IMAGE_TITLE), ObjectUtils.emptyMap());
	}

	@Test
	@DisplayName("No images are returned if no image has the given title")
	public void getImageByTitleShouldFailIfTitleDoesntExist() throws Exception {
		List<Map<Object, Object>> resources = new ArrayList();
		when(apiResponse.get("resources")).thenReturn(resources);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("secure_url", IMAGE_URL);
		map.put("public_id", IMAGE_TITLE);
		map.put("created_at", Instant.EPOCH.toString());
		resources.add(map);

		ImageModel result = subject.getImageByTitle("thisTitleDoesntExist");

		assertNull(result);
	}

	@Test
	@DisplayName("The image with the given title is returned")
	public void getImageByTitleShouldSucceed() throws Exception {
		List<Map<Object, Object>> resources = new ArrayList();
		when(apiResponse.get("resources")).thenReturn(resources);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("secure_url", IMAGE_URL);
		map.put("public_id", IMAGE_TITLE);
		map.put("created_at", Instant.EPOCH.toString());
		resources.add(map);

		ImageModel result = subject.getImageByTitle(IMAGE_TITLE);

		assertNotNull(result);
	}

	@Test
	@DisplayName("Verifies that the cloudinary API is called with the correct parameters when fetching all images.")
	public void getAllImagesShouldCallTheCorrectMethodsWithCorrectParams() throws Exception {
		List<Map<Object, Object>> resources = new ArrayList();
		when(apiResponse.get("resources")).thenReturn(resources);
		Map expectedOptions = ObjectUtils.asMap(
				"type", "upload",
				"max_results", "500");

		subject.getAllImages();

		Mockito.verify(api).resources(expectedOptions);
	}

	@Test
	@DisplayName("Verifies that the correct ImageModels are created and returned")
	public void getAllImagesShouldSucceed() throws Exception {
		List<Map<Object, Object>> resources = new ArrayList();
		when(apiResponse.get("resources")).thenReturn(resources);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("secure_url", IMAGE_URL);
		map.put("public_id", IMAGE_TITLE);
		map.put("created_at", Instant.EPOCH.toString());
		resources.add(map);

		List<ImageModel> images = subject.getAllImages();

		assertEquals(1, images.size());
		assertEquals(IMAGE_URL, images.get(0).getUrl());
		assertEquals(IMAGE_TITLE, images.get(0).getTitle());
		assertEquals(Instant.EPOCH, images.get(0).getCreatedAt());
	}
}