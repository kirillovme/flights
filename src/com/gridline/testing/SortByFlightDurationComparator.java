package com.gridline.testing;

import java.util.Comparator;

public class SortByFlightDurationComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight o1, Flight o2) {
        return o2.getFlightDuration() - o1.getFlightDuration();
    }
}
