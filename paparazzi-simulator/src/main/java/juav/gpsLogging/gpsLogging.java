package juav.gpsLogging;

public class gpsLogging {
	private static double lat, lon, alt;
	
	public static void setPosition(double x, double y, double z){
		if(!(gpsLogging.lat == x && gpsLogging.lon == y && gpsLogging.alt == z)){
		lat = x;
		lon = y;
		alt = z;
		}
	}
}
