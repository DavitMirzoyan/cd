public class Warehouse {

    private int numberOfCDs = 100;
    private int numberOfCDsSold;
    private double priceOfCD;

    public Warehouse(int numberOfCDsSold, double priceOfCD) {
        this.numberOfCDsSold = numberOfCDsSold;
        this.priceOfCD = priceOfCD;
    }

    public double totalRemaining() {
        return numberOfCDs - numberOfCDsSold;
    }

    public double totalIncome() {
        return numberOfCDsSold * priceOfCD;
    }
}
