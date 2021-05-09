package models;

import java.time.Instant;

/**
 * A data object that represents an image in the repository.
 */
public class ImageModel {
	private String url;
	private String title;
	private final Instant createdAt;


	public ImageModel(String url, String title, Instant createdAt) {
		this.url = url;
		this.title = title;
		this.createdAt = createdAt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return String.format(
				"Title: %s\n" +
				"Url: %s\n" +
				"Created at %s\n",
				title, url, createdAt
		);
	}
}
