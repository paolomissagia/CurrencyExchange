import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class currencyConvertor {
    public static void  main(String[] args) throws IOException {
        Boolean running = true;
        while (running){
            HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

            // Currency codes
            currencyCodes.put(1, "USD");
            currencyCodes.put(2, "EUR");
            currencyCodes.put(3, "JPY");
            currencyCodes.put(4, "GBP");
            currencyCodes.put(5, "AUD");
            currencyCodes.put(6, "CAD");
            currencyCodes.put(7, "CHF");
            currencyCodes.put(8, "CNY");
            currencyCodes.put(9, "SEK");
            currencyCodes.put(10, "NZD");

            // Declaring instance variables
            String fromCode;
            String toCode;
            Integer from;
            Integer to;
            double amount;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to the currency converter!");
            System.out.println("Currency converting FROM?");
            System.out.println("1:USD (US Dollar)\t 2:EUR (Euro)\t 3:Japanese yen (JPY)\t 4:GBP (British Pound)\t 5:AUD (Australian dollar)\t 6:CAD (Canadian Dollar)\t 7:CHF (Swiss Franc)\t 8:CNY (Chinese Renminbi)\t 9:SEK (Swedish Krone)\t 10:NZD (New Zealand Dollar)");
            fromCode = currencyCodes.get(scanner.nextInt());

            System.out.println("Welcome to the currency converter!");
            System.out.println("Currency converting TO?");
            System.out.println("1:USD (US Dollar)\t 2:EUR (Euro)\t 3:Japanese yen (JPY)\t 4:GBP (British Pound)\t 5:AUD (Australian dollar)\t 6:CAD (Canadian Dollar)\t 7:CHF (Swiss Franc)\t 8:CNY (Chinese Renminbi)\t 9:SEK (Swedish Krone)\t 10:NZD (New Zealand Dollar)");
            toCode = currencyCodes.get(scanner.nextInt());

            System.out.println("Amount to be converted?");
            amount = scanner.nextFloat();

            exchangeRateGETRequest(fromCode, toCode, amount);
            System.out.println("Would you like to make another conversion?");
            System.out.println("1:Yes \t Any other key: No");
            if (scanner.nextInt() != 1) {
                 running = false;
            }
        }
        System.out.println("Thank you gor using the currency converter!");
    }

    private static void exchangeRateGETRequest(String fromCode, String toCode, double amount) throws IOException {
        DecimalFormat f = new DecimalFormat("00.00");
        String GET_URL = "https://api.exchangeratesapi.io/latest?base=" + toCode + "&symbols=" + fromCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("rates").getDouble(fromCode);
            System.out.println(f.format(amount) + fromCode + " = " + f.format(amount/exchangeRate) + toCode);
        }
        else {
            System.out.println("Get request failed!");
        }
    }
}
