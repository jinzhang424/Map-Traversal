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
    Object[] continentsOfShortestRoute = null;
    int taxFeeOfRoute = 0;

    MessageCli.INSERT_SOURCE.printMessage();
    startingNode = getCountryNodeFromUserInput();

    MessageCli.INSERT_DESTINATION.printMessage();
    endingNode = getCountryNodeFromUserInput();

    visited = countryGraph.breathFirstTraversal(startingNode, endingNode);
    shortestRoute = countryGraph.FindShortestRoute(visited);
    continentsOfShortestRoute = countryGraph.findContinentsOfRoute(shortestRoute);

    // Printing Route info
    if (shortestRoute.size() > 1) {
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      for (int i = shortestRoute.size() - 1; i >= 0; i--) {
        if (i > 0) {
          sb.append(shortestRoute.get(i).getCountry() + ", ");
        } else {
          sb.append(shortestRoute.get(i).getCountry());
        }
        // Getting the tax fee of the route
        if (i < shortestRoute.size() - 1) {
          taxFeeOfRoute += Integer.valueOf(shortestRoute.get(i).getTaxFee());
        }
      }
      sb.append("]");
      MessageCli.ROUTE_INFO.printMessage(sb.toString());
    }

    // Printing Continent info
    StringBuilder continents = new StringBuilder();
    continents.append("[");
    for (int j = continentsOfShortestRoute.length - 1; j >= 0; j--) {
      if (j > 0) {
        continents.append(continentsOfShortestRoute[j] + ", ");
      } else {
        continents.append(continentsOfShortestRoute[j]);
      }
    }
    continents.append("]");
    MessageCli.CONTINENT_INFO.printMessage(continents.toString());

    // Printing tax info
    if (taxFeeOfRoute == 0) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      MessageCli.TAX_INFO.printMessage(String.valueOf(taxFeeOfRoute));
    }
  }
}
