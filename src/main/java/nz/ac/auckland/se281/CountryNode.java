package nz.ac.auckland.se281;

/**
 * This method is used to create nodes for countries and contains basic information of the country
 * (name, continent and tax fee for entry).
 */
public class CountryNode {

  private String country;
  private String taxFee;
  private String continent;

  /**
   * Constructor of the CountryNode class that intialises the fields, country taxFee, continent.
   *
   * @param country a country's name
   * @param continent the continent of the country
   * @param taxFee the fee that needs to be paid for entering the country
   */
  public CountryNode(String country, String continent, String taxFee) {
    this.country = country;
    this.taxFee = taxFee;
    this.continent = continent;
  }

  /**
   * Getter for the field, country.
   *
   * @return the country's name
   */
  public String getCountry() {
    return country;
  }

  /**
   * Getter for the field, taxFee.
   *
   * @return the tax fee of the country
   */
  public String getTaxFee() {
    return taxFee;
  }

  /**
   * Getter for the continent.
   *
   * @return
   */
  public String getContinent() {
    return continent;
  }

  @Override
  /** Overridng the hashCode method. */
  public int hashCode() {

    final int prime = 31;

    int result = 1;

    result = prime * result + ((country == null) ? 0 : country.hashCode());

    return result;
  }

  @Override
  /** Overriding the equals method to check compare the country's nodes for equality. */
  public boolean equals(Object obj) {

    if (this == obj) return true;

    if (obj == null) return false;

    if (getClass() != obj.getClass()) return false;

    CountryNode other = (CountryNode) obj;

    if (country == null) {
      if (other.country != null) return false;
    } else if (!country.equals(other.country)) return false;
    return true;
  }
}
