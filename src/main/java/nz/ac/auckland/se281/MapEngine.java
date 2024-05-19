package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {

  private Map<String, CountryNode> nameToNodeMap;

  public MapEngine() {

    nameToNodeMap = new HashMap<>();

    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    String[] countryDetails = null; // Stores the details of a country

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    // Mapping country names to their corresponding nodes
    for (String country : countries) {
      countryDetails = country.split(",");
      nameToNodeMap.put(countryDetails[0], new CountryNode(countryDetails[0], countryDetails[2]));
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
