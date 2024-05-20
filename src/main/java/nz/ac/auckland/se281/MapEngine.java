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

    CountryNode countryNode = null;

    MessageCli.INSERT_COUNTRY.printMessage();
    countryNode = getCountryNodeFromUserInput();

    MessageCli.COUNTRY_INFO.printMessage(
        countryNode.getCountry(), countryNode.getContinent(), countryNode.getTaxFee());
  }

  public CountryNode getCountryNodeFromUserInput() {

    CountryNode countryNode = null;
    String countryInput = null;
    String userInput = null;

    while (countryNode == null) {

      userInput = Utils.scanner.nextLine();
      countryInput = Utils.capitalizeFirstLetterOfEachWord(userInput);

      try {
        return countryNode = countryGraph.getCountryNode(countryInput);
      } catch (MapNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(countryInput);
      }
    }

    return countryNode;
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {

    CountryNode startingNode = null;
    CountryNode endingNode = null;
    List<CountryNode> visited = null;
    List<CountryNode> shortestRoute = null;

    MessageCli.INSERT_SOURCE.printMessage();
    startingNode = getCountryNodeFromUserInput();

    MessageCli.INSERT_DESTINATION.printMessage();
    endingNode = getCountryNodeFromUserInput();

    visited = countryGraph.breathFirstTraversal(startingNode, endingNode);
    shortestRoute = countryGraph.FindShortestRoute(visited);

    StringBuilder sb = new StringBuilder();

    sb.append("[");
    for (int i = shortestRoute.size() - 1; i >= 0; i--) {
      if (i > 0) {
        sb.append(shortestRoute.get(i).getCountry() + ", ");
      } else {
        sb.append(shortestRoute.get(i).getCountry());
      }
    }

    sb.append("]");

    MessageCli.ROUTE_INFO.printMessage(sb.toString());
  }
}
