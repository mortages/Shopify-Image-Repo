package presenters;

/**
 * Responsible for all the prompts that would appear when a user is logging into the image repository service.
 */
public class LoginPresenter {
	public void getInitialPrompt() {
		System.out.println("Welcome to Cloudinary's Image Repository!\n\n" +
				"To get started, create a Cloudinary account using the following link: https://cloudinary.com/\n" +
				"You can then find your account-specific configuration credentials here: https://cloudinary.com/console\n" +
				"Once you're ready, hit ENTER");
	}

	public void getCloudNamePrompt() {
		System.out.println("Please enter your Cloud name");
	}

	public void getApiKeyPrompt() {
		System.out.println("Please enter your API Key");
	}

	public void getApiSecretPrompt() {
		System.out.println("Please enter your API Secret");
	}
}
