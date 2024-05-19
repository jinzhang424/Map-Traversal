package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {

  private Map<String, CountryNode> nameToNodeMap;
  private Map<CountryNode, ArrayList<CountryNode>> riskMap;

  public MapEngine() {

    riskMap = new HashMap<>();
    nameToNodeMap = new HashMap<>();

    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    String[] countryDetails = null; // Stores the details of a country
    String[] adjacentCountry = null; // Stores the country name and name of adjacent countries

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    ArrayList<CountryNode> adjacentCountryNodes = null;

    // Mapping country names to their corresponding nodes
    for (String country : countries) {
      countryDetails = country.split(",");
      nameToNodeMap.put(countryDetails[0], new CountryNode(countryDetails[0], countryDetails[2]));
    }

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

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
