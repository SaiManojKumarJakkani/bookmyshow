package src.main.java.com.bookmyshow.model;

public class Hall {
    private int hallId;
    private int venueId;
    private String hallName;
    private int numSeatsGold;
    private int numSeatsSilver;
    private int numSeatsPlatinum;

    public Hall() {
        this.numSeatsGold = 0;
        this.numSeatsSilver = 0;
        this.numSeatsPlatinum = 0;
        this.hallName="";
    }
    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }
    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getHallName() {
        return hallName;
    }
    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getNumSeatsGold() {
        return numSeatsGold;
    }

    public void setNumSeatsGold(int numSeatsGold) {
        this.numSeatsGold = numSeatsGold;
    }
    public int getNumSeatsSilver() {
        return numSeatsSilver;
    }

    public void setNumSeatsSilver(int numSeatsSilver) {
        this.numSeatsSilver = numSeatsSilver;
    }
    public int getNumSeatsPlatinum() {
        return numSeatsPlatinum;
    }

    public void setNumSeatsPlatinum(int numSeatsPlatinum) {
        this.numSeatsPlatinum = numSeatsPlatinum;
    }
}

