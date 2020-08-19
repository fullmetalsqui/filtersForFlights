package src.com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Handler implements Filters{
    public int interval(LocalDateTime dep, LocalDateTime arr){
        int segmentInterval = 0;
        if (dep.getHour() >= arr.getHour()){
            segmentInterval = (dep.getHour() - arr.getHour())*60;
            if (dep.getMinute() >= arr.getMinute()){
                segmentInterval += (dep.getMinute() - arr.getMinute());
            }
            else {
                segmentInterval += (60 - arr.getMinute() + dep.getMinute());
            }
        } else {
            segmentInterval = (24 - arr.getHour() + dep.getHour())*60;
            if (dep.getMinute() >= arr.getMinute()){
                segmentInterval += (dep.getMinute() - arr.getMinute());
            }
            else {
                segmentInterval += (60 - arr.getMinute() + dep.getMinute());
            }
        }
        return segmentInterval;
    }


    @Override
    public List<Flight> falseStart(List<Flight> list) {
        List<Flight> flights = new ArrayList<>();
        for (Flight flight : list){
            List<Segment> segments = flight.getSegments();
            if (!segments.get(0).getDepartureDate().isBefore(LocalDateTime.now())){
                flights.add(flight);
            }
        }
        return flights;
    }

    @Override
    public List<Flight> wrongSegmentArrivalDate(List<Flight> list) {
        List<Flight> flights = new ArrayList<>();
        for (Flight flight : list){
            boolean hasWrongSegment = false;
            List<Segment> segments = flight.getSegments();
            for (Segment segment : segments){
                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())){
                    hasWrongSegment = true;
                }
            }
            if (!hasWrongSegment){
                flights.add(flight);
            }
        }
        return flights;
    }

    @Override
    public List<Flight> waitingMoreThanTwoHours(List<Flight> list) {
        List<Flight> flights = new ArrayList<>();
        for (Flight flight : list){
            boolean tooSlow = false;
            int totalInterval = 0;
            List<Segment> segments = flight.getSegments();
            if (segments.size() > 1) {
                for (int i = 1; i < segments.size(); i++){
                    LocalDateTime arrDate = segments.get(i-1).getArrivalDate();
                    LocalDateTime depDate = segments.get(i).getDepartureDate();
                    if (depDate.getDayOfYear() == arrDate.getDayOfYear()){
                        totalInterval += interval(depDate, arrDate);
                    } else if (depDate.getDayOfYear() > arrDate.getDayOfYear()){
                        if ((depDate.getDayOfYear() - arrDate.getDayOfYear()) > 1){
                            tooSlow = true;
                            break;
                        }else {
                            totalInterval += interval(depDate, arrDate);
                        }
                    }
                    if (totalInterval > 120){
                        tooSlow = true;
                        break;
                    }
                }
                if (!tooSlow){
                    flights.add(flight);
                }
            }else {
                flights.add(flight);
            }
        }
        return flights;
    }

}
