package task.valdemarc.ibanvalidationtaskservice2.model;

public class Iban {

    private String countryCode;
    private String checkDigits;
    private String bban;
    private int length;


    public Iban(String countryCode, String checkDigits, String bban, int length) {
        this.countryCode = countryCode;
        this.checkDigits = checkDigits;
        this.bban = bban;
        this.length = length;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCheckDigits() {
        return checkDigits;
    }

    public void setCheckDigits(String checkDigits) {
        this.checkDigits = checkDigits;
    }

    public String getBban() {
        return bban;
    }

    public void setBban(String bban) {
        this.bban = bban;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Iban{" +
                "countryCode='" + countryCode + '\'' +
                ", checkDigits='" + checkDigits + '\'' +
                ", bban='" + bban + '\'' +
                ", length=" + length +
                '}';
    }
}
