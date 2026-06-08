public class Flight {
    public int flightNo;
    public int startCity;
    public int departureTime;
    public int endCity;
    public int arrivalTime;
    public int fare;
    
    public Flight(int flightNo, int startCity, int departureTime,int endCity, int arrivalTime, int fare) {
        this.flightNo = flightNo;
        this.startCity = startCity;
        this.departureTime = departureTime;
        this.endCity = endCity;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
    }
}
