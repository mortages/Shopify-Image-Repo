package views;

import controllers.MainMenuController;
import models.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import presenters.MainMenuPresenter;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

class MainMenuViewTest {
	private static final File FILE = new File("pathToMyCoolImage");
	private static final String IMAGE_TITLE = "A cool title";
	private static final String IMAGE_URL = "www.someCoolImage.com";

	private MainMenuView subject;

	@Mock
	private JFileChooser fileChooser;

	@Mock
	private MainMenuController controller;

	@Mock
	private MainMenuPresenter presenter;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Verify that invalid user input is handled correctly.")
	public void handlesInvalidInputWhenChoiceIsOutOfRange() {
		ByteArrayInputStream in = new ByteArrayInputStream("-1".getBytes());
		System.setIn(in);
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser);

		subject.run();

		verify(presenter).getAllMenuOptions();
		verify(presenter).getInvalidOptionText();
		verifyNoMoreInteractions(presenter);
		verifyZeroInteractions(controller);
	}
	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called upon successfully getting all images. ")
	public void getAllImagesShouldSucceed() throws Exception {
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser);
		List<ImageModel> images = Collections.singletonList(new ImageModel(IMAGE_URL, IMAGE_TITLE, Instant.EPOCH));
		when(controller.getAllImages()).thenReturn(images);

		subject.runGetAllImagesView();

		verify(controller).getAllImages();
		verify(presenter).getAllImagesSuccessText(images);
		verifyNoMoreInteractions(controller);
		verifyNoMoreInteractions(presenter);
	}

	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called when an exception is thrown while getting all images. ")
	public void getAllImagesShouldFail() throws Exception {
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser );
		when(controller.getAllImages()).thenThrow(new Exception());

		subject.runGetAllImagesView();

		verify(controller).getAllImages();
		verify(presenter).getAllImagesErrorText();
		verifyNoMoreInteractions(controller);
		verifyNoMoreInteractions(presenter);
	}



	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called upon successfully getting image " +
			"with the given title. ")
	public void getImageByTitleShouldSucceed() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(IMAGE_TITLE.getBytes());
		System.setIn(in);
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser);
		ImageModel image = new ImageModel(IMAGE_URL, IMAGE_TITLE, Instant.EPOCH);
		when(controller.getImageByTitle(IMAGE_TITLE)).thenReturn(image);

		subject.runGetImageByTitleView();

		verify(presenter).getImageByTitlePrompt();
		verify(controller).getImageByTitle(IMAGE_TITLE);
		verify(presenter).getImageByTitleSuccessText(image);
		verifyNoMoreInteractions(controller);
		verifyNoMoreInteractions(presenter);
	}

	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called when an exception is thrown while getting " +
			"an image with a given title ")
	public void getImageByTitleShouldFail() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(IMAGE_TITLE.getBytes());
		System.setIn(in);
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser );
		doThrow(new Exception()).when(controller).getImageByTitle(IMAGE_TITLE);

		subject.runGetImageByTitleView();

		verify(presenter).getImageByTitlePrompt();
		verify(controller).getImageByTitle(IMAGE_TITLE);
		verify(presenter).getImagesByTitleErrorText(IMAGE_TITLE);
		verifyNoMoreInteractions(controller);
		verifyNoMoreInteractions(presenter);
	}

	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called upon successfully deleting an image ")
	public void deleteImageByTitleShouldSucceed() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(IMAGE_TITLE.getBytes());
		System.setIn(in);
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser);

		subject.runDeleteImageByTitleView();

		verify(presenter).getDeleteImagePrompt();
		verify(controller).deleteImageByTitle(IMAGE_TITLE);
		verify(presenter).getDeleteImageByTitleSuccessText(IMAGE_TITLE);
		verifyNoMoreInteractions(controller);
		verifyNoMoreInteractions(presenter);
	}

	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called when an exception is thrown while deleting an image. ")
	public void deleteImageByTitleShouldFail() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(IMAGE_TITLE.getBytes());
		System.setIn(in);
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser );
		doThrow(new IOException()).when(controller).deleteImageByTitle(IMAGE_TITLE);

		subject.runDeleteImageByTitleView();

		verify(presenter).getDeleteImagePrompt();
		verify(controller).deleteImageByTitle(IMAGE_TITLE);
		verify(presenter).getDeleteImageByTitleErrorText(IMAGE_TITLE);
		verifyNoMoreInteractions(controller);
		verifyNoMoreInteractions(presenter);
	}

	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called upon successfully uploading an image.")
	public void uploadImageShouldSucceed() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(IMAGE_TITLE.getBytes());
		System.setIn(in);
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser);

		subject.runUploadImageView(FILE);

		verify(presenter).getImageTitlePrompt();
		verify(controller).uploadImage(FILE, IMAGE_TITLE);
		verify(presenter).getImageUploadSuccessText(IMAGE_TITLE);
		verifyNoMoreInteractions(presenter);
		verifyNoMoreInteractions(controller);
	}

	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called when an exception is thrown while uploading an image. ")
	public void uploadImageShouldFail() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(IMAGE_TITLE.getBytes());
		System.setIn(in);
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser);
		doThrow(new IOException()).when(controller).uploadImage(FILE, IMAGE_TITLE);

		subject.runUploadImageView(FILE);

		verify(presenter).getImageTitlePrompt();
		verify(controller).uploadImage(FILE, IMAGE_TITLE);
		verify(presenter).getImageUploadErrorText();
		verifyNoMoreInteractions(presenter);
		verifyNoMoreInteractions(controller);
	}

	@Test
	@DisplayName("Verify that the correct presenter & controller methods are called when a user doesn't select a file.")
	public void uploadImageShouldFailIfNoFileChosen() {
		subject = new MainMenuView(presenter, controller, new Scanner(System.in), fileChooser);

		subject.runUploadImageView(null);

		verify(presenter).getImageUploadErrorText();
		verifyNoMoreInteractions(presenter);
		verifyZeroInteractions(controller);
	}
}