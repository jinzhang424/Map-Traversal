package nz.ac.auckland.se281;

import java.util.List;

/**
 * This class contains all the functions of the map engine and can prints information about
 * countries and information on travel routes.
 */
public class MapEngine {

  private CountryGraph countryGraph = new CountryGraph();

  /** Constructor of the MapEngine class. */
  public MapEngine() {

    loadMap();
  }

  /** This method intializes the map with all the details of countries. */
  private void loadMap() {

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    countryGraph.generateNameToNodeMap(countries);
    countryGraph.generateRiskMap(adjacencies);
  }

  /**
   * This method shows the info (Country name, continent, tax fee of travelling there) of a country
   * based on the user input.
   */
  public void showInfoCountry() {

    CountryNode countryNode;

    MessageCli.INSERT_COUNTRY.printMessage();
    countryNode = getCountryNodeFromUserInput();

    MessageCli.COUNTRY_INFO.printMessage(
        countryNode.getCountry(), countryNode.getContinent(), countryNode.getTaxFee());
  }

  /**
   * This method gets a country node by asking the user for an input (a country's name) and
   * continues to ask the user for input until they enter a valid input.
   *
   * @return the node of a country
   */
  public CountryNode getCountryNodeFromUserInput() {

    CountryNode countryNode = null;
    String countryInput;
    String userInput;

    // Continuously asks for the user's input while their input is invalid
    while (countryNode == null) {

      userInput = Utils.scanner.nextLine();
      countryInput = Utils.capitalizeFirstLetterOfEachWord(userInput);

      try {
        countryNode = countryGraph.getCountryNode(countryInput);
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(countryInput);
      }
    }

    return countryNode;
  }

  /**
   * This method prints the shortest route from one country to another, the continents they will
   * visit and the total tax fee of their travels.
   */
  public void showRoute() {

    CountryNode startingNode;
    CountryNode endingNode;
    List<CountryNode> visited;
    List<CountryNode> shortestRoute;
    Object[] continentsOfShortestRoute;
    int taxFeeOfRoute = 0;
    StringBuilder routeStringBuilder;
    StringBuilder continentsStringBuilder;

    MessageCli.INSERT_SOURCE.printMessage();
    startingNode = getCountryNodeFromUserInput();

    MessageCli.INSERT_DESTINATION.printMessage();
    endingNode = getCountryNodeFromUserInput();

    // Finding the shortestRoute and continents of the shortest route
    visited = countryGraph.findRootToDestinationNodeBFS(startingNode, endingNode);
    shortestRoute = countryGraph.findShortestRoute(visited);
    continentsOfShortestRoute = countryGraph.findContinentsOfRoute(shortestRoute);

    // Printing Route info
    if (shortestRoute.size()
        > 1) { // Checking if the shortestRoute size is 1 (indicates that the start and destination
      // is the same)
      routeStringBuilder = new StringBuilder();
      routeStringBuilder.append("[");
      for (int i = shortestRoute.size() - 1; i >= 0; i--) {
        // Prevents appending a comma and space when appending the last country
        if (i > 0) {
          routeStringBuilder.append(shortestRoute.get(i).getCountry() + ", ");
        } else {
          routeStringBuilder.append(shortestRoute.get(i).getCountry());
        }
        // Getting the tax fee of the route
        if (i < shortestRoute.size() - 1) {
          taxFeeOfRoute += Integer.valueOf(shortestRoute.get(i).getTaxFee());
        }
      }
      routeStringBuilder.append("]");
      MessageCli.ROUTE_INFO.printMessage(routeStringBuilder.toString());
    }

    // Printing Continent info
    continentsStringBuilder = new StringBuilder();
    continentsStringBuilder.append("[");

    for (int j = continentsOfShortestRoute.length - 1; j >= 0; j--) {
      if (j > 0) {
        continentsStringBuilder.append(continentsOfShortestRoute[j] + ", ");
      } else {
        continentsStringBuilder.append(continentsOfShortestRoute[j]);
      }
    }
    continentsStringBuilder.append("]");
    MessageCli.CONTINENT_INFO.printMessage(continentsStringBuilder.toString());

    // Printing tax info
    if (taxFeeOfRoute == 0) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      MessageCli.TAX_INFO.printMessage(String.valueOf(taxFeeOfRoute));
    }
  }
}
