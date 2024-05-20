package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedHashSet;

public class CountryGraph {

  private Map<String, CountryNode> nameToNodeMap;
  private Map<CountryNode, ArrayList<CountryNode>> riskMap;

  public CountryGraph() {
    nameToNodeMap = new HashMap<>();
    riskMap = new HashMap<>();
  }

  public void generateNameToNodeMap(List<String> countries) {

    String[] countryDetails = null; // Stores the details of a country

    for (String country : countries) {
      countryDetails = country.split(",");
      nameToNodeMap.put(
          countryDetails[0],
          new CountryNode(countryDetails[0], countryDetails[1], countryDetails[2]));
    }
  }

  public void generateRiskMap(List<String> adjacencies) {

    String[] adjacentCountry = null; // Stores the country name and name of adjacent countries
    ArrayList<CountryNode> adjacentCountryNodes = null;

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

    while (!queue.isEmpty() && !visited.contains(destination)) {
      CountryNode node = queue.poll(); // retrieves and removes the head element

      // Visitng the adjacent nodes first
      for (CountryNode n : riskMap.get(node)) {

        // If the visited list doesn't contain the node
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

    // While the shortestRoute set doesn't contain the routeStartNode
    while (!shortestRoute.contains(routeStartNode)) {

      // Looking through the visited node list until we find one that matches a node in the current
      // node's adjacency list starting from the top of the list
      for (int i = 0; i < visited.size(); i++) {

        // Checks if a node in visited is in the adjacency of the currentNode we're searching for
        // and add it if it is
        if (riskMap.get(currentNode).contains(visited.get(i))) {
          shortestRoute.add(visited.get(i));
          currentNode = visited.get(i);
          break;
        }
      }
    }

    return shortestRoute;
  }

  public String[] findContinentsOfRoute(List<CountryNode> visited) {

    Set<String> continents = new LinkedHashSet<>();

    for (CountryNode countryNode: visited) {
      continents.add(countryNode.getContinent());
    }

    return (String[]) continents.toArray();
  }
}
