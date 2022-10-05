import Charts.Charts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestActions {

    @Test
    public void buyingCDWhenPaymentAccepted(){
        CD cd = new CD(100, 10, mock(TopAlbums.class), mock(LowestCompetitor.class));
        CreditCard card = (double price) -> true;
        cd.buy("CD 11", 1, card);

        assertEquals(99, cd.getStock());
    }

    @Test
    public void buyingCDWhenPaymentRejected(){
        CD cd = new CD(100, 10, mock(TopAlbums.class), mock(LowestCompetitor.class));
        CreditCard card = (double price) -> false;
        cd.buy("CD 11", 1, card);

        assertEquals(100, cd.getStock());
    }

    /*
    @Test
    public void insufficientStock(){
        CD cd = new CD(100, 10);
        CreditCard card = (double price) -> true;
        cd.buy("CD 11", 110, card);

        assertEquals(0, cd.getStock());
    }*/

    @Test
    public void cardIsChargedThePayment(){
        CreditCard mockCard = mock(CreditCard.class);
        when(mockCard.pay(anyDouble())).thenReturn(true);
        CD cd = new CD(100, 10, mock(TopAlbums.class), mock(LowestCompetitor.class));
        cd.buy("CD 11", 2, mockCard);
        verify(mockCard).pay(20);
    }

    // Artist 1 -> Title 1 -> (CD 11, CD 12, CD 13)
    // Artist 2 -> Title 2 -> (CD 21, CD 22, CD 23)

    @Test
    public void chartsNotified(){
        CreditCard mockCard = mock(CreditCard.class);
        when(mockCard.pay(anyDouble())).thenReturn(true);

        Charts mockCharts = mock(Charts.class);
        when(mockCharts.updated(anyInt(), anyString(), anyString())).thenReturn(true);

        CD cd = new CD(100, 10, mock(TopAlbums.class), mock(LowestCompetitor.class));
        cd.buy("CD 11", 2, mockCard);
        verify(mockCard).pay(20);

        cd.updateCharts("CD 11", mockCharts);
        verify(mockCharts).updated(88, "Title 1", "Artist 1");
    }

    @Test
    public void topAlbumsDiscount(){
        TopAlbums topAlbums = new TopAlbums() {
            @Override
            public int listOfAlbums(String cd){
                return 50;
            }
        };

        LowestCompetitor lowestCompetitor = new LowestCompetitor() {
            @Override
            public double competitorPrice(String cd){
                return 8;
            }
        };

        CD cd = new CD(100, 10, topAlbums, lowestCompetitor);

        CreditCard card = mock(CreditCard.class);
        when(card.pay(anyDouble())).thenReturn(true);
        cd.buy("CD 11", 2, card);

        assertEquals(15, cd.reducedPrice());
    }

    /*
    @Test
    public void searchingByExistingTitleAndExistingArtist(){
        CD cd = new CD(100, 10);

        cd.search("CD 12", "Artist 2");
        assertEquals(true, cd.isAvailable());
    }

    @Test
    public void searchingByNotExistingTitle(){
        CD cd = new CD(100);

        cd.search("CD 14", "");
        assertEquals(false, cd.isAvailable());
    }

    @Test
    public void searchingByExistingArtist(){
        CD cd = new CD(100);

        cd.search("", "Artist 3");
        assertEquals(false, cd.isAvailable());
    }

    @Test
    public void customersProvidedRating(){
        CD cd = new CD(100);

        cd.addReview("CD 11", 8, "Good CD");
        assertEquals(true, cd.getRating("CD 11", 8, "Good CD"));
    }*/
}
