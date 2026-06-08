import java.util.*;

public class Planner {

    private final List<List<Flight>> graph;
    private final int fsize;

    public Planner(List<Flight> flights) {
        int maxCity = 0;
        fsize = flights.size();

        for (Flight f : flights)
            maxCity = Math.max(maxCity, Math.max(f.startCity, f.endCity));

        graph = new ArrayList<>();
        for (int i = 0; i <= maxCity; i++)
            graph.add(new ArrayList<>());

        for (Flight f : flights)
            graph.get(f.startCity).add(f);

        for (int i = 0; i < graph.size(); i++) {
            graph.set(i, mergeSort(graph.get(i))); // sort all the flight from startCity based on arrivalTime 
        }

    }

    private List<Flight> mergeSort(List<Flight> s) {
        if (s.size() <= 1) return s;

        int mid = s.size() / 2;
        List<Flight> left = mergeSort(new ArrayList<>(s.subList(0, mid)));
        List<Flight> right = mergeSort(new ArrayList<>(s.subList(mid, s.size())));

        return merge(left, right);
    }

    private List<Flight> merge(List<Flight> a, List<Flight> b) {
        List<Flight> res = new ArrayList<>();
        int i = 0, j = 0;

        while (i < a.size() && j < b.size()) {
            if (a.get(i).arrivalTime < b.get(j).arrivalTime)
                res.add(a.get(i++));
            else
                res.add(b.get(j++));
        }

        while (i < a.size()) res.add(a.get(i++));
        while (j < b.size()) res.add(b.get(j++));

        return res;
    }


    public List<Flight> leastFlightsEarliestRoute(int startCity, int endCity, int t1, int t2) {
        // i want to reach endCity with minflights and minarrivaltime 
        if (startCity == endCity) return new ArrayList<>();
        ArrayQueue<Object[]> queue = new ArrayQueue<>();
        queue.enqueue(new Object[]{0, t1 - 20, null, null}); 
        // no of boarded flights till now , arrivaltime , parentnode , flightfromwhichireachedthisNode
        boolean[] visited = new boolean[fsize]; Object[] finalNode = null;
        int bestFlights = Integer.MAX_VALUE; int bestArrival = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Object[] node = queue.dequeue();

            int flights = (int) node[0]; int arrival = (int) node[1]; Flight last = (Flight) node[3];
            int currCity = (last == null) ? startCity : last.endCity;
            if (currCity == endCity) {
         if (flights < bestFlights || (flights == bestFlights && arrival < bestArrival)) {
                    bestFlights = flights;
                    bestArrival = arrival;
                    finalNode = node;
                }
                continue;
            }
            for (Flight f : graph.get(currCity)) {
                if (!visited[f.flightNo] && f.departureTime >= Math.max(t1, arrival + 20)&& f.arrivalTime <= t2) {
                    visited[f.flightNo] = true;
                    queue.enqueue(new Object[]{flights + 1, f.arrivalTime, node, f});
                }
            }
        }
        return buildPath(finalNode);
    }

    public List<Flight> cheapestRoute(int startCity, int endCity, int t1, int t2) {
        Heap<Object[]> heap = new Heap<>(Comparator.comparingInt(a -> (int) a[0])); // based on cost 
        // Object[] -> cost , city , parent node , last Flight 
        heap.insert(new Object[]{0, startCity, null, null});
        boolean[] visited = new boolean[fsize];
        Object[] finalNode = null;
        while (!heap.isEmpty()) {
            Object[] t = heap.extract();

            int cost = (int) t[0];
            int city = (int) t[1];
            Flight last = (Flight) t[3];

            if (city == endCity) {
                finalNode = t;
                break;
            }
            int currTime = (last == null) ? t1 : Math.max(t1, last.arrivalTime + 20);
            for (Flight f : graph.get(city)) {
                if (!visited[f.flightNo]
                        && f.departureTime >= currTime
                        && f.arrivalTime <= t2) {

                    visited[f.flightNo] = true;
                    heap.insert(new Object[]{cost + f.fare, f.endCity, t, f});
                }
            }
        }
        return buildPath(finalNode);
    }

    private List<Flight> buildPath(Object[] node) {
        // Object[] -> cost , city , parent node , last Flight 
        List<Flight> path = new ArrayList<>();

        while (node != null && node[3] != null) {
            path.add((Flight) node[3]);
            node = (Object[]) node[2];
        }

        Collections.reverse(path);
        return path;
    }
}



