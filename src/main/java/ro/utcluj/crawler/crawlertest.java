package ro.utcluj.crawler;

public class crawlertest {

	public static void main(final String[] args) {
		Crawler c = new Crawler("http://www.cnn.com", 8080);
		Crawler c1 = new Crawler("http://www.protv.ro", 8080);
		Crawler c2 = new Crawler("http://www.a1.ro", 8080);
		Crawler c3 = new Crawler("http://www.realitatea.net", 8080);
		Crawler c4 = new Crawler("http://en.wikipedia.com", 8080);
		Crawler c5 = new Crawler("http://www.adevarul.ro", 8080);

		c.start();
		c1.start();
		c2.start();
		c3.start();
		c4.start();
		c5.start();
	}
}
