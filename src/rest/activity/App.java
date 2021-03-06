package rest.activity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class App {
	private static final URI BASE_URI = URI.create("http://localhost:5901/");

	public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException {
		System.out.println("Starting assignment2 standalone HTTP server...");
		ResourceConfig rc = createApp();
		JdkHttpServerFactory.createHttpServer(BASE_URI, rc);
		System.out.println("Server started on " + BASE_URI + "\n[kill the process to exit]");
	}

	public static ResourceConfig createApp() {
		System.out.println("Starting assignment2 REST services...");
		MyApplicationConfig config = new MyApplicationConfig();
		return config;
	}
}
