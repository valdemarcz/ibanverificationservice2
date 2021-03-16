package task.valdemarc.ibanvalidationtaskservice2.service;

import java.util.HashMap;
import java.util.Map;

;

public class InitializeListOfCountryCodesAndBankCodeLength {

    public static final Map<String, Integer> bankCodeCountryList = new HashMap<>();
    static{
        bankCodeCountryList.put("LT", 5);
        bankCodeCountryList.put("LV", 4);
        bankCodeCountryList.put("EE", 2);
    }
}
