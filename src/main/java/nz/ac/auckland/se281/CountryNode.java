package nz.ac.auckland.se281;

public class CountryNode {

  private String country = null;
  private String taxFee = null;
  private String continent = null;

  public CountryNode(String country, String continent, String taxFee) {
    this.country = country;
    this.taxFee = taxFee;
    this.continent = continent;
  }

  public String getCountry() {
    return country;
  }

  public String getTaxFee() {
    return taxFee;
  }

  public String getContinent() {
    return continent;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((country == null) ? 0 : country.hashCode());
    return result;
  }

  @Override
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
