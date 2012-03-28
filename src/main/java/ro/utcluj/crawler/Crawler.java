package ro.utcluj.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class Crawler extends Thread {

	private static Logger LOG = Logger.getLogger(Crawler.class);
	private Set<String> visitedURL = new HashSet<String>();
	private Set<String> remainingURL = new HashSet<String>();
	private Set<String> discoveredURL;
	private int turns = 1;
	private int siteCount = 0;
	private String initialURL;
	long start;
	long end;
	long finalTime;
	private int port;

	public Crawler(final String initialURL, int port) {
		this.initialURL = initialURL;
		this.port = port;
	}

	public void run() {
		remainingURL.add(this.initialURL);
		this.start = System.currentTimeMillis();

		while (turns < 3) {
			LOG.info(this.getName() + " turn " + turns + " is in progress...");

			discoveredURL = new HashSet<String>();

			for (final String urlLocation : remainingURL) {

				final Crawl c = new Crawl(urlLocation, port);

				discoveredURL.addAll(c.getResult().getDiscoveredURL());
				visitedURL.add(urlLocation);

				LOG.info(this.getName() + " total number of sites to be crawled: " + this.siteCount + ". At current step: " + this.turns);
			}

			siteCount = discoveredURL.size();

			remainingURL.removeAll(visitedURL);
			remainingURL.addAll(discoveredURL);
			end = System.currentTimeMillis();
			finalTime = (end - start) / 1000;
			LOG.info(this.getName() + " turn " + turns + " completed in " + finalTime + " seconds.");
			LOG.info(remainingURL.size() + " more sites discovered and ready to be crawled...");
			turns++;
		}

		Crawler.writeToFile(remainingURL);
	}

	public static void writeToFile(final Set<String> sitesSet) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("D:/temp/test.txt"), true));
			for (final String line : sitesSet) {
				bw.write(line);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			LOG.error(e);
		} finally {
		}
	}

}
