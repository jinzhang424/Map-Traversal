package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
