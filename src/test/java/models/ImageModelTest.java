package models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageModelTest {
	private static final String IMAGE_URL = "www.coolImage.com";
	private static final String IMAGE_TITLE = "My Cool Image";
	private static final String UPDATED_IMAGE_URL = "www.coolImageNumber2.com";
	private static final String UPDATED_IMAGE_TITLE = "My Cool Image Number 2";
	private static final Instant CREATED_AT = Instant.EPOCH;

	private ImageModel subject;

	@BeforeEach
	public void setup() {
		subject = new ImageModel(IMAGE_URL, IMAGE_TITLE, CREATED_AT);
	}

	@Test
	@DisplayName("Getter returns the correct URL")
	public void correctUrlShouldBeReturned() {
		String actualUrl = subject.getUrl();

		assertEquals(IMAGE_URL, actualUrl);
	}

	@Test
	@DisplayName("Getter returns the correct image title")
	public void correctTitleShouldBeReturned() {
		String actualTitle = subject.getTitle();

		assertEquals(IMAGE_TITLE, actualTitle);
	}

	@Test
	@DisplayName("Setter sets the correct URL")
	public void correctUrlShouldBeSet() {
		subject.setUrl(UPDATED_IMAGE_URL);

		String actualUrl = subject.getUrl();

		assertEquals(UPDATED_IMAGE_URL, actualUrl);
	}

	@Test
	@DisplayName("Setter sets the correct image title")
	public void correctTitleShouldBeSet() {
		subject.setTitle(UPDATED_IMAGE_TITLE);

		String actualTitle = subject.getTitle();

		assertEquals(UPDATED_IMAGE_TITLE, actualTitle);
	}
}