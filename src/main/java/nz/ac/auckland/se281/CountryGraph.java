package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CountryGraph {

  private Map<String, CountryNode> nameToNodeMap;
  private Map<CountryNode, ArrayList<CountryNode>> riskMap;

  public CountryGraph() {
    nameToNodeMap = new HashMap<>();
    riskMap = new HashMap<>();
  }

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

  public Map<String, CountryNode> getNameToNodeMap() {
    return nameToNodeMap;
  }

  public Map<CountryNode, ArrayList<CountryNode>> getRiskMap() {
    return riskMap;
  }

  public CountryNode getCountryNode(String countryName) throws MapNotFoundException {

    // Checks if the countryName input is valid
    if (!nameToNodeMap.containsKey(countryName)) {
      throw new MapNotFoundException();
    } else {
      return nameToNodeMap.get(countryName);
    }
  }

  public List<CountryNode> breathFirstTraversal(CountryNode root, CountryNode destination) {

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

  public List<CountryNode> FindShortestRoute(List<CountryNode> visited) {

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

  public Object[] findContinentsOfRoute(List<CountryNode> shortestRoute) {

    Set<String> continents = new LinkedHashSet<>();

    // Adds continents of the nodes in the visited list
    for (CountryNode countryNode : shortestRoute) {
      continents.add(countryNode.getContinent());
    }

    return continents.toArray();
  }
}
