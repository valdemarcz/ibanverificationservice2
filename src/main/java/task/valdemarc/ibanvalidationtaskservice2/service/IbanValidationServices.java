package task.valdemarc.ibanvalidationtaskservice2.service;



import task.valdemarc.ibanvalidationtaskservice2.model.Iban;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IbanValidationServices {

    public Map<String, String> bankNamesInit(String filePath) {
        Map<String, String> listOfSupportedBanks = new HashMap<>();

        try{
            File fileWithBankNames = new File(filePath);
            Scanner myReader = new Scanner(fileWithBankNames);

            while (myReader.hasNextLine()){
                String lineFromFile = myReader.nextLine();
                String[] parts = lineFromFile.split(";");
                listOfSupportedBanks.put(parts[0]+parts[1], parts[2]);
            }
            myReader.close();

        }catch (FileNotFoundException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        return listOfSupportedBanks;

    }


    public String bankNameExtract(String iban){

            Map<String, String> listOfBankNames = bankNamesInit("./src/main/resources/static/banknameslist.txt");

            Iban separatedIban = separateIban(iban);

            String bankName = "";


            if(InitializeListOfCountryCodesAndBankCodeLength.bankCodeCountryList.containsKey(separatedIban.getCountryCode())){
                bankName = listOfBankNames.get(separatedIban.getBban().substring(0,InitializeListOfCountryCodesAndBankCodeLength.bankCodeCountryList.get(separatedIban.getCountryCode()))+separatedIban.getCountryCode());
            }

            return bankName;

    }



    public void bankNameInsert(String filePath){
        try{
            File fileWithIban = new File(filePath);
            FileWriter myWriter = new FileWriter("uploads/" + fileWithIban.getName().substring(0,fileWithIban.getName().lastIndexOf("."))+ "_bank.csv");
            Scanner myReader = new Scanner(fileWithIban);


            while (myReader.hasNextLine()){
                String lineFromFile = myReader.nextLine();
                String resultLine = lineFromFile +";" + bankNameExtract(lineFromFile)+"\n";
                myWriter.write(resultLine);

            }

            myReader.close();
            myWriter.close();
        }catch (FileNotFoundException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }

    }



    public void validateIbanFromFile(String filePath){
        try{
            File fileWithIban = new File(filePath);
            FileWriter myWriter = new FileWriter("uploads/" + fileWithIban.getName().substring(0,fileWithIban.getName().lastIndexOf(".")) + "_valid.csv");
            Scanner myReader = new Scanner(fileWithIban);


            while (myReader.hasNextLine()){
                String lineFromFile = myReader.nextLine();
                String resultLine = lineFromFile +";" + validateIban(separateIban(lineFromFile))+"\n";
                myWriter.write(resultLine);

            }

            myReader.close();
            myWriter.close();
        }catch (FileNotFoundException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public Iban separateIban(String iban){
        String countryCodeSubstring = iban.substring(0,2);
        String checkDigitSubstring = iban.substring(2,4);
        String basicBankAccountNumSubstring = iban.substring(4);

        Iban ibanSeparated = new Iban(countryCodeSubstring, checkDigitSubstring, basicBankAccountNumSubstring, iban.length());

        return ibanSeparated;
    }

    public boolean validateIban(Iban iban){

        if(InitializeValidLenghtOfIBAN.validLengthsAndCountryCodes.containsKey(iban.getCountryCode()) && iban.getLength()==InitializeValidLenghtOfIBAN.validLengthsAndCountryCodes.get(iban.getCountryCode())){

            String replacedIban = iban.getBban() + iban.getCountryCode() + iban.getCheckDigits();
            String transformedIban = "";

            for (int i: replacedIban.toCharArray()){
                if(i>=65 && i <=90){
                    i = i-55;
                    transformedIban += Integer.toString(i);
                }
                else {
                    transformedIban += (char) i;
                }
            }

            BigInteger ibanAsBigInt = new BigInteger(transformedIban);

            int modResult = ibanAsBigInt.mod(new BigInteger("97")).intValue();

            if(modResult == 1) return true;
        }

        return false;
    }
}
