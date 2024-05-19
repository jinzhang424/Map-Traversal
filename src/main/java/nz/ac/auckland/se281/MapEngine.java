package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  private CountryGraph countryGraph = new CountryGraph();

  public MapEngine() {

    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    countryGraph.generateNameToNodeMap(countries);
    countryGraph.generateRiskMap(adjacencies);
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {

    String userInput = null;
    CountryNode countryNode = null;
    String countryInput = null;

    MessageCli.INSERT_COUNTRY.printMessage();

    while (countryNode == null) {

      userInput = Utils.scanner.nextLine();
      countryInput = Utils.capitalizeFirstLetterOfEachWord(userInput);

      try {
        countryNode = countryGraph.getCountryNode(countryInput);
      } catch (MapNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(countryInput);
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
