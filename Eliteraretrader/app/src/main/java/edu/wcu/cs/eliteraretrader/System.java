package edu.wcu.cs.eliteraretrader;

/**
 * This class will hold relevant information regarding a star system
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class System {

    //star system name
    private String name;
    //commodity that can be found in system
    private String commodity;
    //the station the commodity can be found at
    private String station;
    //the distance to system from system jump point
    private int distance;
    //the max allotment
    private int cap;
    //message to buy at system or not to buy at system
    private String buy;
    //x coordinate of the system
    private double x;
    //y coordinate of the system
    private double y;
    //z coordinate of the system
    private double z;

    /**
     * Constructor for making system Objects
     *
     * @param name star system name
     * @param commodity commodity that can be found in system
     * @param station the station the commodity can be found at
     * @param distance the distance to system from system jump point
     * @param cap the max allotment
     * @param buy message to buy at system or not to buy at system
     * @param x x coordinate of the system
     * @param y y coordinate of the system
     * @param z z coordinate of the system
     */
    public System(String name, String commodity, String station, int distance, int cap, String buy,
                    double x, double y, double z){
        this.name = name;
        this.commodity = commodity;
        this.station = station;
        this.distance = distance;
        this.cap = cap;
        this.buy = buy;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * get the system name
     * @return system name
     */
    public String getName(){
        return name;
    }

    /**
     * get the commodity name
     * @return commodity name
     */
    public String getCommodity(){
        return commodity;
    }

    /**
     * get the station name
     * @return station name
     */
    public String getStation(){
        return station;
    }

    /**
     * get the distance to station
     * @return distance to station
     */
    public int getDistance(){
        return distance;
    }

    /**
     * get the max allotment
     * @return max allotment
     */
    public int getCap(){
        return cap;
    }

    /**
     * get the buy or sell status
     * @return buy or sell status
     */
    public String getBuy(){
        return buy;
    }

    /**
     * get the x coordinate
     * @return x coordinate
     */
    public double getX(){
        return x;
    }

    /**
     * get the y coordinate
     * @return y coordinate
     */
    public double getY(){
        return y;
    }

    /**
     * get the z coordinate
     * @return z coordinate
     */
    public double getZ(){
        return z;
    }

    /**
     * Set the buy field
     * @param buy the new state
     */
    public void setBuy(String buy){
        this.buy = buy;
    }
}
