package src.com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Handler handler = new Handler();
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println(handler.falseStart(flights).toString());
        System.out.println(handler.wrongSegmentArrivalDate(flights).toString());
        System.out.println(handler.waitingMoreThanTwoHours(flights).toString());
    }
}
