import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
* AUTHOR: Jacob Scott
* FILE: WikiRacer.java
* ASSIGNMENT: Programming Assignment 10
* COURSE: CSc 210; Fall 2020
* PURPOSE: This class finds the fastest link between two Wikipedia pages and utilizes memoization
* and parallelization in order to find the ladder in a timely manner.
*
* USAGE: 
* Pass in a start and end page as the default arguments and run to find the quickest ladder 
* between the two pages.
* 
* ----------- EXAMPLE INPUT -------------
* Fruit Strawberry
* Milkshake Gene
* Emu Stanford_University
* 
*/

public class WikiRacer {

	/*
     * Purpose: The main method which calls the function to find the ladder and then prints it out.
     * 
     * @param: args, The command line arguments used.
     * 
     * @return: None.
     */
	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}

	/*
     * Purpose: This method creates a priority queue and enqueues the start page and then loops through
     * the queue and dequeues and enqueues elements which are sorted based on the number of links in 
     * common with the target page. It then returns the path it found.
     * 
     * @param: start, The Wikipedia page to start from.
     * 
     * @param: end, The destination Wikipedia page.
     * 
     * @return: The ladder between the two pages.
     */
	private static List<String> findWikiLadder(String start, String end) {
		MaxPQ queue = new MaxPQ();
		List<String> startPath = new ArrayList<String>();
		startPath.add(start);
		queue.enqueue(startPath, 0);
		Set<String> visited = new HashSet<>();
		Set<String> endSet = WikiScraper.findWikiLinks(end);
		
		while (!queue.isEmpty()) {
			List<String> currPath = queue.dequeue();
			Set<String> links = WikiScraper.findWikiLinks(currPath.get(currPath.size() - 1));
			visited.add(currPath.get(currPath.size() - 1));
			
			if (links.contains(end)) {
				currPath.add(end);
				return currPath;
			}
			
			links.parallelStream().forEach(link -> {
				WikiScraper.findWikiLinks(link);
				});
			
			for (String link: links) {
				if (!visited.contains(link)) {
					List<String> copyPage = new ArrayList<String>(currPath);
					copyPage.add(link);
					Set<String> intersect = WikiScraper.findWikiLinks(link);
					intersect.retainAll(endSet);
					queue.enqueue(copyPage, intersect.size());
				}
			}
		}
		
		return new ArrayList<String>();
	}

}
