package com.gridline.testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class FlightsFilter implements FilteringFlights {

    private List<Flight> flights;

    public FlightsFilter (List<Flight> flights) {
        this.flights = flights;
        this.filterFlights();
        System.out.println("Инициализирован фильтер авиаперелётов. Данные очищены от невалидных значений");
        makeMenu();
    }

    private void makeMenu () {
        System.out.println("Доступны следующие команды:");
        System.out.println("1) Вывести рейсы , которые отправляются в определенную дату");
        System.out.println("2) Вывести рейсы, котрые прибывают в определенную дату");
        System.out.println("3) Вывести рейсы с определенным временем на борту");
        System.out.println("4) Вывести рейсы с определенным общим временем полета");
        System.out.println("5) Вывести рейсы с определенной датой прибытия и датой отправления");
        System.out.println("6) Отфильтровать рейсы по длительности (по возрастанию)");
        System.out.println("7) Отфильтровать рейсы по длительности (по убыванию)");
        System.out.println("8) Отфильтровать рейсы по дате отправления (по возрастанию)");
        System.out.println("9) Отфильтровать рейсы по дате отправления (по убыванию)");
        System.out.println("10) Отфильтровать рейсы по дате прибытия (по возрастанию)");
        System.out.println("11) Отфильтровать рейсы по дате прибытия (по убыванию)");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String answer = reader.readLine();
            String questionAnswer = null;
            int duration = 0;
            ArrayList<Flight> flightArrayList = new ArrayList<>();
            switch (answer) {
                case ("1"):
                    System.out.println("Введите дату");
                    questionAnswer = reader.readLine();
                    LocalDateTime localDateTime = LocalDateTime.parse(questionAnswer);
                    flightArrayList = (ArrayList<Flight>) getDepartureDateFlights(localDateTime);
                    for (Flight flight : flightArrayList) {
                        System.out.println(flight);
                    }
                    break;
                case ("2"):
                    System.out.println("Введите дату");
                    questionAnswer = reader.readLine();
                    LocalDateTime localDateTime2 = LocalDateTime.parse(questionAnswer);
                    flightArrayList = (ArrayList<Flight>) getArrivalDateFlights(localDateTime2);
                    for (Flight flight : flightArrayList) {
                        System.out.println(flight);
                    }
                    break;
                case ("3"):
                    System.out.println("Введите желаемое время на борту");
                    questionAnswer = reader.readLine();
                    duration = Integer.parseInt(questionAnswer);
                    flightArrayList = (ArrayList<Flight>) getDurationOnBoardFlights(duration);
                    for (Flight flight : flightArrayList) {
                        System.out.println(flight);
                    }
                    break;
                case ("4"):
                    System.out.println("Введите желаемое общее время перелёта");
                    questionAnswer = reader.readLine();
                    duration = Integer.parseInt(questionAnswer);
                    flightArrayList = (ArrayList<Flight>) getDurationFlights(duration);
                    for (Flight flight : flightArrayList) {
                        System.out.println(flight);
                    }
                    break;
                case ("5"):
                    System.out.println("Введите желаемое время вылета и прибытия через пробел");
                    questionAnswer = reader.readLine();
                    String[] parsedString = questionAnswer.split(" ");
                    LocalDateTime localDateTime5 = LocalDateTime.parse(parsedString[0]);
                    LocalDateTime localDateTime6 = LocalDateTime.parse(parsedString[1]);
                    flightArrayList = (ArrayList<Flight>) getFlights(localDateTime5, localDateTime6);
                    for (Flight flight : flightArrayList) {
                        System.out.println(flight);
                    }
                    break;
                case ("6"):
                    filterAscendingDuration();
                    showFlights();
                    break;
                case ("7"):
                    filterDescendingDuration();
                    showFlights();
                    break;
                case ("8"):
                    filterAscendingDepartureDate();
                    showFlights();
                    break;
                case ("9"):
                    filterDescendingDepartureDate();
                    showFlights();
                    break;
                case ("10"):
                    filterAscendingArrivingDate();
                    showFlights();
                    break;
                case ("11"):
                    filterDescendingArrivalDate();
                    showFlights();
                    break;
                default:
                    System.out.println("Возможно вы ввели неверные данные. Попробуйте перезапустить программу");
                    break;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("Возникла ошибка при вводе данных. Попробуйте перезапустить программу");
        }
    }

    public void showFlights() {
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }

    @Override
    public void filterFlights() {
        excludeInvalidDeparture();
        excludeSegments();
        tooLongOnEarth();
    }

    private void excludeInvalidDeparture() {
        ArrayList<Flight> copyFlights = new ArrayList<>(flights);
        for (Flight flight: flights) {
            if (!flight.getSegments().get(0).getDepartureDate().isBefore(LocalDateTime.now())) {
                copyFlights.add(flight);
            }
        }
        flights = copyFlights;
    }

    private void excludeSegments() {
        ArrayList<Flight> copyFlights = new ArrayList<>(flights);
        boolean isValid = true;
        for (Flight flight : flights) {

            for (Segment segment : flight.getSegments()) {
                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    isValid = false;
                }
            }
            if (isValid) {
                copyFlights.add(flight);
            }
        }
        flights = copyFlights;
    }

    private void tooLongOnEarth() {
        ArrayList<Flight> copyFlights = new ArrayList<>(flights);
        boolean isValid = true;
        int amountOnEarth = 0;
        for (Flight flight : flights) {

            for (int i = 0; i < flight.getSegments().size() - 2; i++) {
                amountOnEarth += ChronoUnit.HOURS.between(flight.getSegments().get(i).getArrivalDate(), flight.getSegments().get(i + 1).getDepartureDate());
            }
            if (amountOnEarth > 2) {
                isValid = false;
            }
            if (isValid) {
                copyFlights.add(flight);
            }

        }
        flights = copyFlights;
    }

    @Override
    public List<Flight> getDepartureDateFlights(LocalDateTime localDateTime) {
        ArrayList<Flight> flightsTemp = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getSegments().get(0).getDepartureDate().equals(localDateTime)) {
                flightsTemp.add(flight);
            }
        }
        return flightsTemp;
    }

    @Override
    public List<Flight> getArrivalDateFlights(LocalDateTime localDateTime) {
        ArrayList<Flight> flightsTemp = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getSegments().get(flight.getSegments().size() - 1).getDepartureDate().equals(localDateTime)) {
                flightsTemp.add(flight);
            }
        }
        return flightsTemp;
    }

    @Override
    public List<Flight> getDurationOnBoardFlights(int duration) {
        ArrayList<Flight> flightsTemp = new ArrayList<>();
        int flightDuration = 0;
        for (Flight flight : flights) {
            for (Segment segment : flight.getSegments()) {
                flightDuration += ChronoUnit.HOURS.between(segment.getArrivalDate(), segment.getDepartureDate());
            }
            if (flightDuration == duration) {
                flightsTemp.add(flight);
            }
            flightDuration = 0;
        }
        return flightsTemp;

    }

    @Override
    public List<Flight> getDurationFlights(int duration) {
        ArrayList<Flight> flightsTemp = new ArrayList<>();
        long flightDuration = 0;
        for (Flight flight : flights) {
           flightDuration = ChronoUnit.HOURS.between(flight.getSegments().get(0).getDepartureDate(),
                   flight.getSegments().get(flight.getSegments().size() - 1).getArrivalDate());
           if (flightDuration == duration) {
               flightsTemp.add(flight);
           }
        }
        return flightsTemp;
    }

    @Override
    public List<Flight> getFlights(LocalDateTime departureDate, LocalDateTime arrivingDate) {
        ArrayList<Flight> flightsTemp = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getSegments().get(0).getDepartureDate().equals(departureDate) &&
                    flight.getSegments().get(flight.getSegments().size() - 1).getArrivalDate().equals(arrivingDate)) {
                flightsTemp.add(flight);
            }
        }
                    return flightsTemp;

    }

    @Override
    public void filterDescendingDuration() {
        SortByFlightDurationComparator sortByFlightDurationComparator = new SortByFlightDurationComparator();
        Collections.sort(flights, sortByFlightDurationComparator);
        Collections.reverse(flights);
    }

    @Override
    public void filterAscendingDuration() {
        SortByFlightDurationComparator sortByFlightDurationComparator = new SortByFlightDurationComparator();
        Collections.sort(flights, sortByFlightDurationComparator);
    }

    @Override
    public void filterAscendingDepartureDate() {
        SortByDepartureDateComparator sortByDepartureDateComparator = new SortByDepartureDateComparator();
        Collections.sort(flights, sortByDepartureDateComparator);
    }

    @Override
    public void filterDescendingDepartureDate() {
        SortByDepartureDateComparator sortByDepartureDateComparator = new SortByDepartureDateComparator();
        Collections.sort(flights, sortByDepartureDateComparator);
        Collections.reverse(flights);
    }

    @Override
    public void filterAscendingArrivingDate() {
        SortByArrivingDateComparator sortByArrivingDateComparator = new SortByArrivingDateComparator();
        Collections.sort(flights, sortByArrivingDateComparator);
    }

    @Override
    public void filterDescendingArrivalDate() {
        SortByArrivingDateComparator sortByArrivingDateComparator = new SortByArrivingDateComparator();
        Collections.sort(flights, sortByArrivingDateComparator);
        Collections.reverse(flights);
    }

}
