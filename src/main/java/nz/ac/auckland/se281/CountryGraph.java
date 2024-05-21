package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** This class is used for generating maps and has methods to find the path between 2 nodes. */
public class CountryGraph {

  private Map<String, CountryNode> nameToNodeMap;
  private Map<CountryNode, ArrayList<CountryNode>> riskMap;

  /** Constructor of CountryGraph and intializes the fields, nameToNodeMap and riskMap. */
  public CountryGraph() {
    nameToNodeMap = new HashMap<>();
    riskMap = new HashMap<>();
  }

  /**
   * Creates a HashMap that maps the name of a country as a string to a particular CountryNode.
   *
   * @param countries an arbitrary list of countries where each element has the country's name,
   *     continent and tax fee for entry seperated by commas
   */
  public void generateNameToNodeMap(List<String> countries) {

    String[] countryDetails; // Stores the details of a country

    // Goes through all the countries and creates a node for each country
    for (String country : countries) {

      countryDetails = country.split(",");

      nameToNodeMap.put(
          countryDetails[0],
          new CountryNode(countryDetails[0], countryDetails[1], countryDetails[2]));
    }
  }

  /**
   * Uses a hashmap to map CountryNodes to their adjacent nodes stored as an array list.
   *
   * @param adjacencies List of countries followed by their adjacent countries
   */
  public void generateRiskMap(List<String> adjacencies) {

    String[] adjacentCountry; // Stores the country name and name of adjacent countries
    ArrayList<CountryNode> adjacentCountryNodes;

    // Mapping a node to a list of adjacent nodes
    for (String c : adjacencies) {
      adjacentCountry = c.split(",");
      adjacentCountryNodes = new ArrayList<>();

      for (int i = 1; i < adjacentCountry.length; i++) {
        // Getting the adjacent country's node from nameToNodeMap and adding it to the ArrayList:
        // adjacentCountryNodes
        adjacentCountryNodes.add(nameToNodeMap.get(adjacentCountry[i]));
      }
      riskMap.put(nameToNodeMap.get(adjacentCountry[0]), adjacentCountryNodes);
    }
  }

  /**
   * Getter for the nameToNodeMap HashMap.
   *
   * @return the nameToNodeMap HashMap
   */
  public Map<String, CountryNode> getNameToNodeMap() {
    return nameToNodeMap;
  }

  /**
   * Getter for the riskMap HashMap.
   *
   * @return the riskMap HashMap
   */
  public Map<CountryNode, ArrayList<CountryNode>> getRiskMap() {
    return riskMap;
  }

  /**
   * Get's a country's node based on the string of the country.
   *
   * @param countryName the name of a country as a string
   * @return the CountryNode of a particular country
   * @throws MapNotFoundException throws an exception when the parameter countryName is not found in
   *     the keys of nameToNodeMap
   */
  public CountryNode getCountryNode(String countryName) throws CountryNotFoundException {

    // Checks if the countryName input is valid
    if (!nameToNodeMap.containsKey(countryName)) {
      throw new CountryNotFoundException();
    } else {
      return nameToNodeMap.get(countryName);
    }
  }

  /**
   * Uses the breath first search algorithm to search for the destination CountryNode starting from
   * the root node using adjacency lists of CountryNodes.
   *
   * @param root the CountryNode of which we begin the search
   * @param destination the CountryNode we use to reach
   * @return A list of CountryNodes we've come across while searching for the destination
   *     CountryNode
   */
  public List<CountryNode> findRootToDestinationNodeBFS(CountryNode root, CountryNode destination) {

    List<CountryNode> visited = new ArrayList<>();
    Queue<CountryNode> queue = new LinkedList<>();

    queue.add(root);
    visited.add(root);

    // Keeps looping while the queue isn't empty and the visited list doesn't contain the root node
    while (!queue.isEmpty() && !visited.contains(destination)) {
      CountryNode node = queue.poll(); // retrieves and removes the head element

      // Visitng the adjacent nodes first
      for (CountryNode n : riskMap.get(node)) {

        // If the visited list doesn't contain the current node and doesn't contain the root
        // node
        if (!visited.contains(n) && !visited.contains(destination)) {
          visited.add(n); // Adds the node to the visited list
          queue.add(n); // Adds the node the the queue list
        }
      }
    }
    return visited;
  }

  /**
   * This method finds the shortest path between the first and last CountryNode in the parameter
   * visited using the adjacency list of CountryNodes in the list assuming that the path exists.
   *
   * <p>It does this by designating a currentNode (starting with the bottom CountryNode in the
   * visited list) and scans the visited list from top to bottom and checks if a CountryNode is in
   * the adjacent list of the currentNode. If it is add it to the shortestRoute list change the
   * currentNode to the node we recently added to the list and immediately being searching the
   * adjacent list of the current node. Do this until the first node in the visited list is added
   * tothe shortestRoute list.
   *
   * @param visited a list of CountryNodes that have been visited on the way to finding the last
   *     CountryNodes
   * @return the shortest path from the top node in the visited list to the bottom node in the
   *     visited list in a REVERSED order
   */
  public List<CountryNode> findShortestRoute(List<CountryNode> visited) {

    List<CountryNode> shortestRoute = new ArrayList<>();
    CountryNode currentNode = visited.get(visited.size() - 1);
    CountryNode routeStartNode = visited.get(0);

    shortestRoute.add(currentNode);

    // Keeps looping while the shortestRoute list doesn't contain the routeStartNode
    while (!shortestRoute.contains(routeStartNode)) {

      // Looking through the visited node list until we find one that matches a node in the current
      // node's adjacency list starting from the top of the list
      for (int i = 0; i < visited.size(); i++) {

        // Checks if a node in visited is in the adjacency of the currentNode and if it is add it to
        // the list
        if (riskMap.get(currentNode).contains(visited.get(i))) {
          shortestRoute.add(visited.get(i));
          currentNode = visited.get(i);
          break;
        }
      }
    }

    return shortestRoute;
  }

  /**
   * Finds the continents given a list of CountryNodes using a HashSet to avoid repeating
   * continents. (Typically used for lists that contains the shortest path between 2 CountryNodes)
   *
   * @param shortestRoute a list of CountryNodes (typically the shortest path between 2
   *     CountryNodes)
   * @return an array of the continents in a REVERSED order
   */
  public Object[] findContinentsOfRoute(List<CountryNode> shortestRoute) {

    Set<String> continents = new LinkedHashSet<>();

    // Adds continents of the nodes in the visited list
    for (CountryNode countryNode : shortestRoute) {
      continents.add(countryNode.getContinent());
    }

    return continents.toArray();
  }
}
