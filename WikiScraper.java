import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* FILE: WikiScraper.java
* ASSIGNMENT: Programming Assignment 10
* COURSE: CSc 210; Fall 2020
* PURPOSE: This class gathers the html of Wikipedia pages from a URL and finds links
* which exist on those pages. Also uses memoization to reduce function calls.
*
* USAGE: 
* Pass in a page title and it will create a URL and grab the html from that web address.
* 
*/
public class WikiScraper {
	
	private static HashMap<String, Set<String>> memo = new HashMap<String, Set<String>>();
			
	/*
     * Purpose: This method keeps track of all the links on a page and first checks
     * if the link passed in has already had its page links gathered. Then it will
     * fetch the html and scrape the links from it.
     * 
     * @param: link, The Wikipedia page to gather links from.
     * 
     * @return: The set of links which exist on the page.
     */
	public static Set<String> findWikiLinks(String link) {
		if (memo.containsKey(link)) {
			return memo.get(link);
		}
		
		String html = fetchHTML(link);
		Set<String> links = scrapeHTML(html);
		memo.put(link, links);
		return links;
	}
	
	/*
     * Purpose: This method gets the URL of a page based on how Wikipedia page addresses 
     * are formatted and then goes to that web page and gathers the html of that page.
     * 
     * @param: link, The Wikipedia page to gather links from.
     * 
     * @return: The html of the web page passed in.
     */
	private static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/*
     * Purpose: This method gets the URL of a page based on how Wikipedia page addresses 
     * are formatted.
     * 
     * @param: link, The Wikipedia page to gather links from.
     * 
     * @return: The link concatenated with the Wikipedia web address format.
     */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
	/*
     * Purpose: This method gets every link on a Wikipedia page based on the html of that
     * web page.
     * 
     * @param: html, The html code of a Wikipedia page.
     * 
     * @return: A set which contains every link found on the web page.
     */
	private static Set<String> scrapeHTML(String html) {
		Set<String> set = new HashSet<String>();

		String[] htmlArray = html.split("\"|\\/");
		for (int i = 0; i < htmlArray.length; i++) {
			if (htmlArray[i].equals("wiki") && !htmlArray[i + 1].contains(":") && !htmlArray[i + 1].contains("#") && !htmlArray[i - 1].contains("org")) {
				set.add(htmlArray[i + 1]);
			}
		}
		return set;
	}
	
}
