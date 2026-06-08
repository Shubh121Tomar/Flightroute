import java.util.*;

public class Main {

    public static void main(String[] args) {

        /*
        public class Flight {
        public int flightNo;
        public int startCity;
        public int departureTime;
        public int endCity;
        public int arrivalTime;
        public int fare;

        public Flight(int flightNo, int startCity, int departureTime,
                    int endCity, int arrivalTime, int fare) {
            this.flightNo = flightNo;
            this.startCity = startCity;
            this.departureTime = departureTime;
            this.endCity = endCity;
            this.arrivalTime = arrivalTime;
            this.fare = fare;
        }
    

         */

        List<Flight> flights = new ArrayList<>();
        //                     f  s  d   e   a    f 
        flights.add(new Flight(0, 0, 10, 1, 50, 100));
        flights.add(new Flight(1, 1, 80, 2, 120, 100));
        flights.add(new Flight(2, 0, 20, 2, 90, 300));
        flights.add(new Flight(3, 0, 15, 1, 40, 150));
        flights.add(new Flight(4, 1, 70, 2, 110, 120));

        Planner planner = new Planner(flights);
        int startCity = 0;
        int endCity = 2;
        int t1 = 0;
        int t2 = 200;

        // Cheapest Route
        System.out.println("\nCheapest Route:");
        List<Flight> route2 = planner.cheapestRoute(startCity, endCity, t1, t2);
        printRoute(route2);

        // Least Flights + Earliest Arrival Route
        System.out.println("\nLeast Flights, Cheapest Route:");
        List<Flight> route3 = planner.leastFlightsEarliestRoute(startCity, endCity, t1, t2);
        printRoute(route3);
    }

    private static void printRoute(List<Flight> route) {
        if (route.isEmpty()) {
            System.out.println("No valid route found.");
            return;
        }

        int totalCost = 0;
        for (Flight f : route) {
            totalCost += f.fare;
            System.out.println(
                "Flight " + f.flightNo +
                " : " + f.startCity + " -> " + f.endCity +
                " | depart " + f.departureTime +
                " | arrive " + f.arrivalTime +
                " | fare " + f.fare
            );
        }
        System.out.println("Total cost = " + totalCost);
        System.out.println("Total flights = " + route.size());
    }
}
