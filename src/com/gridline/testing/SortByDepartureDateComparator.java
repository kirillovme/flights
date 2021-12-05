package com.gridline.testing;

import java.util.Comparator;

public class SortByDepartureDateComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight o1, Flight o2) {
        return o1.getSegments().get(0).getDepartureDate().compareTo(o2.getSegments().get(0).getDepartureDate());
    }
}
