import Charts.Charts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CD {
    private double reducedPriceOfCD;
    private final int price;
    private int stock;
    private final TopAlbums topAlbums;
    private final LowestCompetitor lowestCompetitor;
    private int soldTotal = 0;
    private int leftInStock;
    private HashMap<String, HashMap<String, List<String>>> cdInfo = new HashMap<>();
    private static HashMap<String, Integer> numberOfCopiesPerCD = new HashMap<>();
    private static HashMap<String, HashMap<Integer,String>> titleReviews = new HashMap<>();

    private boolean searchPhraseFound;

    public CD(int stock, int price, TopAlbums topAlbums, LowestCompetitor lowestCompetitor) {
        this.price = price;
        this.stock = stock;
        this.topAlbums = topAlbums;
        this.lowestCompetitor = lowestCompetitor;

        HashMap<String, List<String>> titleAndCDs = new HashMap<>();

        List<String> artistCDs = Arrays.asList("CD 11", "CD 12", "CD 13");
        titleAndCDs.put("Title 1", artistCDs);
        cdInfo.put("Artist 1", titleAndCDs);

        artistCDs = Arrays.asList("CD 21", "CD 22", "CD 23");
        titleAndCDs.put("Title 2", artistCDs);
        cdInfo.put("Artist 2", titleAndCDs);

        numberOfCopiesPerCD.put("CD 11", 90);
        numberOfCopiesPerCD.put("CD 12", 80);
        numberOfCopiesPerCD.put("CD 13", 70);
        numberOfCopiesPerCD.put("CD 21", 60);
        numberOfCopiesPerCD.put("CD 22", 50);
        numberOfCopiesPerCD.put("CD 23", 40);
        //this.cdInfo.put("Artist 3", "CD 3");
        //this.cdInfo.put("Artist 4", "CD 4");
        //this.cdInfo.put("Artist 5", "CD 5");


    }

    public int getStock() {
        return stock;
    }

    public void buy(String cd, int number, CreditCard card) {
        if(topAlbums.listOfAlbums(cd) < 101){
            double competitorsP = number * lowestCompetitor.competitorPrice(cd);
            reducedPriceOfCD = competitorsP - 1;
        }

        int leftInStock = numberOfCopiesPerCD.get(cd);

        if (number > leftInStock){
            number = leftInStock;
        }

        if (card.pay(number*price)){
            stock = stock - number;
            leftInStock -= number;
            soldTotal += number;
        }

        numberOfCopiesPerCD.replace(cd, leftInStock);
        this.leftInStock = leftInStock;
    }

    public boolean isAvailable() {
        return searchPhraseFound;
    }

    /*
    public void search(String searchByCD, String searchByArtist) {
        List<String> listOfArtistCDs;

        try{
            listOfArtistCDs = cdInfo.get(searchByArtist);
        }catch (Exception e){
            listOfArtistCDs = new ArrayList<>();
        }

        if(!searchByCD.equals("") && !searchByArtist.equals("")){
            listOfArtistCDs = cdInfo.get(searchByArtist);
            searchPhraseFound = listOfArtistCDs.contains(searchByCD);
        }else{
            boolean titleFound;

            if(listOfArtistCDs.isEmpty()){
                titleFound = false;
            }else{
                titleFound = listOfArtistCDs.contains(searchByCD);
            }

            boolean artistFound = cdInfo.containsKey(searchByArtist);

            searchPhraseFound = titleFound || artistFound;
        }

    }*/

    public void addReview(String title, int rating, String review) {
        HashMap<Integer, String> clientReview = new HashMap<>();
        clientReview.put(rating, review);
        titleReviews.put(title,clientReview);
    }


    public boolean getRating(String title, int rating, String review) {
        return titleReviews.get(title).get(rating).equals(review);
    }

    public int trackSoldQuantity() {
        return soldTotal;
    }

    public int getLeftInStock(){
        return leftInStock;
    }

    public void updateCharts(String cd, Charts mockCharts) {
        // get cd -> find Artist and Title -> how many copies left in stock
        //mockCharts.updated();
    }

    public double reducedPrice() {
        return reducedPriceOfCD;
    }
}
