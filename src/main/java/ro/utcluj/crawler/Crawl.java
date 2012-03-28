package ro.utcluj.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class Crawl {

	private Set<String> visitedURL = new HashSet<String>();
	private String urlString;
	private ResultsDto result = new ResultsDto();
	private static Logger LOG = Logger.getLogger(Crawl.class);
	private int port;

	public Crawl(final String urlString, int port) {
		this.urlString = urlString;
		this.port = port;
		this.run();
	}

	public void run() {
		this.crawlURL(urlString);
	}

	public void crawlURL(final String urlString) {
		try {

			if (visitedURL.contains(urlString)) {
				return;
			}

			visitedURL.add(urlString);

			final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy", port));
			final URL url = new URL(urlString);

			final HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);
			uc.setConnectTimeout(6 * 10 * 1000);
			uc.connect();

			final BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));

			String inputLine;
			final Pattern pattern = Pattern.compile("[a-z:]+://[a-zA-Z0-9]+.[a-zA-Z0-9/.-]+");
			Matcher matcher;

			final Set<String> discoveredURLS = new HashSet<String>();

			while ((inputLine = in.readLine()) != null) {
				matcher = pattern.matcher(inputLine);

				if (matcher.find()) {
					discoveredURLS.add(matcher.group());
				}
			}

			in.close();

			// for (final String urlLocation : discoveredURLS) {
			// System.out.println(urlLocation);
			// }

			this.result.setDiscoveredURL(discoveredURLS);

		} catch (MalformedURLException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	public ResultsDto getResult() {
		return result;
	}

	public void setResult(ResultsDto result) {
		this.result = result;
	}
}
