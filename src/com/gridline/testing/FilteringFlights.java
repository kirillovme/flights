package com.gridline.testing;

import java.time.LocalDateTime;
import java.util.List;

public interface FilteringFlights {

    public abstract void filterFlights();

    // Flights with the specific departure date
    public abstract List<Flight> getDepartureDateFlights (LocalDateTime localDateTime);

    // Flights with the specific arrival date
    public abstract List<Flight> getArrivalDateFlights (LocalDateTime localDateTime);

    // Flights with the fixed amount of time in the air
    public abstract List<Flight> getDurationOnBoardFlights (int duration);

    // Flights whole duration
    public abstract List<Flight> getDurationFlights (int duration);

    // Flights with the specific departure date and arrival date
    public abstract List<Flight> getFlights(LocalDateTime departureDate, LocalDateTime arrivingDate);

    // Filter flights duration in descending order
    public abstract void filterDescendingDuration();

    // Filter flights duration in ascending order
    public abstract void filterAscendingDuration();

    // Filter flights departure date in ascending order
    public abstract void filterAscendingDepartureDate();

    // Filter flights departure date in descending order
    public abstract void filterDescendingDepartureDate();

    // Filter flights arrival date in ascending order
    public abstract void filterAscendingArrivingDate();

    // Filter flights arrival date in descending order
    public abstract void filterDescendingArrivalDate();





}
