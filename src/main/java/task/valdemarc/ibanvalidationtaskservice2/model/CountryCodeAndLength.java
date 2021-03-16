package task.valdemarc.ibanvalidationtaskservice2.model;

public class CountryCodeAndLength {

    private String countryCode;
    private int validLengthForCountry;

    public CountryCodeAndLength(String countryCode, int validLengthForCountry) {
        this.countryCode = countryCode;
        this.validLengthForCountry = validLengthForCountry;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getValidLengthForCountry() {
        return validLengthForCountry;
    }

    public void setValidLengthForCountry(int validLengthForCountry) {
        this.validLengthForCountry = validLengthForCountry;
    }
}
