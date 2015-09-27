package pe.area51.reversegeocodingapp;

public class Location {

    private final double latitude;
    private final double longitude;
    private final String address;
    private final String country;

    public Location(double latitude, double longitude, String address, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }
}
