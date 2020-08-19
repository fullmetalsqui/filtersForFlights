package src.com.gridnine.testing;

import java.util.List;

public interface Filters {

    public List<Flight> falseStart(List<Flight> list);
    public List<Flight> wrongSegmentArrivalDate(List<Flight> list);
    public List<Flight> waitingMoreThanTwoHours(List<Flight> list);
}
