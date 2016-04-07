package algorithms.Search;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
*  <h1>Searcher</h1>
* 
* defining the functionality of any kind of Searcher
* the class CommonSearcher will implements this interface, and any kind of Searcher 
* will extends CommonSearcher and will implements his own functionality
* 
*/



public interface Searcher {
    // the search method
    public Solution search(Searchable s);
    // get how many nodes were evaluated by the algorithm
    public int getNumberOfNodesEvaluated();

	public void testSearcher(Searcher searcher, Searchable searchable);
}
