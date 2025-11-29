import java.util.*;

public class StockTradingPlatform {

    // -------- Stock class --------
    static class Stock {
        private String symbol;
        private double price;

        public Stock(String symbol, double price) {
            this.symbol = symbol;
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return symbol + " - ₹" + String.format("%.2f", price);
        }
    }

    // -------- Portfolio class --------
    static class Portfolio {
        private Map<String, Integer> holdings;
        private double balance;

        public Portfolio(double startingBalance) {
            this.holdings = new HashMap<>();
            this.balance = startingBalance;
        }

        public void buyStock(Stock stock, int quantity) {
            double cost = stock.getPrice() * quantity;
            if (cost <= balance) {
                balance -= cost;
                holdings.put(stock.getSymbol(),
                        holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
                System.out.println("✅ Bought " + quantity + " shares of " + stock.getSymbol());
            } else {
                System.out.println("❌ Not enough balance!");
            }
        }

        public void sellStock(Stock stock, int quantity) {
            int owned = holdings.getOrDefault(stock.getSymbol(), 0);
            if (owned >= quantity) {
                holdings.put(stock.getSymbol(), owned - quantity);
                balance += stock.getPrice() * quantity;
                System.out.println("✅ Sold " + quantity + " shares of " + stock.getSymbol());
            } else {
                System.out.println("❌ Not enough shares to sell!");
            }
        }

        public void displayPortfolio(Map<String, Stock> market) {
            System.out.println("\n📊 Portfolio Summary:");
            for (String symbol : holdings.keySet()) {
                int qty = holdings.get(symbol);
                double price = market.get(symbol).getPrice();
                System.out.println(symbol + " | Qty: " + qty +
                        " | Value: ₹" + String.format("%.2f", price * qty));
            }
            System.out.println("💰 Balance: ₹" + String.format("%.2f", balance));
        }
    }

    // -------- main program --------
    private static Map<String, Stock> market = new HashMap<>();
    private static Portfolio portfolio;
    private static Random random = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeMarket();

        System.out.println("💹 Welcome to the Java Stock Trading Platform!");
        System.out.print("Enter your starting balance: ₹");
        double start = sc.nextDouble();
        portfolio = new Portfolio(start);

        boolean running = true;
        while (running) {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Update Prices");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int c = sc.nextInt();

            switch (c) {
                case 1 -> showMarket();
                case 2 -> buy(sc);
                case 3 -> sell(sc);
                case 4 -> portfolio.displayPortfolio(market);
                case 5 -> updatePrices();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice!");
            }
        }

        System.out.println("👋 Thank you for using the Stock Trading Platform!");
        sc.close();
    }

    private static void initializeMarket() {
        market.put("TCS", new Stock("TCS", 3400.0));
        market.put("INFY", new Stock("INFY", 1450.0));
        market.put("HDFC", new Stock("HDFC", 1620.0));
        market.put("RELI", new Stock("RELI", 2400.0));
        market.put("WIPRO", new Stock("WIPRO", 580.0));
    }

    private static void showMarket() {
        System.out.println("\n📈 Current Market:");
        for (Stock s : market.values()) {
            System.out.println(s);
        }
    }

    private static void buy(Scanner sc) {
        showMarket();
        System.out.print("Enter stock symbol: ");
        String sym = sc.next().toUpperCase();
        Stock s = market.get(sym);
        if (s == null) {
            System.out.println("❌ Invalid symbol!");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();
        portfolio.buyStock(s, qty);
    }

    private static void sell(Scanner sc) {
        portfolio.displayPortfolio(market);
        System.out.print("Enter stock symbol: ");
        String sym = sc.next().toUpperCase();
        Stock s = market.get(sym);
        if (s == null) {
            System.out.println("❌ Invalid symbol!");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();
        portfolio.sellStock(s, qty);
    }

    private static void updatePrices() {
        for (Stock s : market.values()) {
            double change = (random.nextDouble() - 0.5) * 0.1; // ±5%
            s.setPrice(s.getPrice() * (1 + change));
        }
        System.out.println("📉 Market prices updated!");
    }
}
