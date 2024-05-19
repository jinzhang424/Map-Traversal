package nz.ac.auckland.se281;

public class CountryNode {

  private String country = null;
  private String taxFee = null;

  public CountryNode(String country, String taxFee) {
    this.country = country;
    this.taxFee = taxFee;
  }

  public String getCountry() {
    return country;
  }

  public String getTaxFee() {
    return taxFee;
  }
}
