package ro.utcluj.crawler;

import java.util.HashSet;
import java.util.Set;

public class ResultsDto {

	private Set<String> discoveredURL = new HashSet<String>();
	private Set<String> resultURL = new HashSet<String>();

	public ResultsDto() {

	}

	public Set<String> getDiscoveredURL() {
		return this.discoveredURL;
	}

	public void setDiscoveredURL(final Set<String> discoveredURL) {
		this.discoveredURL = discoveredURL;
	}

	public Set<String> getResultURL() {
		return this.resultURL;
	}

	public void setResultURL(final Set<String> resultURL) {
		this.resultURL = resultURL;
	}

}
