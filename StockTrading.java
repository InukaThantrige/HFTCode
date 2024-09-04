import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockTrading {
    public static void main(String[] args) {
        String csvFile = "resources/StockData.csv";
        String line;
        String csvSplitBy = ",";
        List<Double> prices = new ArrayList<>();
        int smaPeriod = 5;
        double cash = 1000;
        int shares = 0;
        int lineno = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();

            while ((line = br.readLine()) != null) {

                System.out.println("line number: " +lineno++);
                String[] data = line.split(csvSplitBy);
                double price = Double.parseDouble(data[4]);
                prices.add(price);

                if (prices.size() >= smaPeriod) {
                    double sma = calculateSMA(prices, smaPeriod);

                    if (price > sma && cash >= price) {
                        shares++;
                        cash -= price;
                        System.out.println("Bought at $" + price + "--- current Balance:"+ cash);
                    } else if ( shares > 0) {
                        shares--;
                        cash += price;
                        System.out.println("Sold at $" + price + "--- current Balance:"+ cash);
                    }
                }
            }
            System.out.println("Final Shares: "+shares);

            cash += shares * prices.get(prices.size() - 1);
            System.out.println("Avg: $" + String.format("%.3f", cash));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculateSMA(List<Double> prices, int period) {
        double sum = 0;
        for (int i = prices.size() - period; i < prices.size(); i++) {
            sum += prices.get(i);
        }
        System.out.println(sum+"---"+ prices.size());
        return sum / period;
    }
}