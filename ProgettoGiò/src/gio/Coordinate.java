package gio;


public class Coordinate {
		private double latitudine;
		private double longitudine;
		private double temp; //milli secondi
	
		public  Coordinate(double latitudine,double longitudine, double temp) {
			this.latitudine=latitudine;
			this.longitudine=longitudine;
			this.temp=temp;
		}

		public double getLatitudine() {
			return latitudine;
		}


		public double getLongitudine() {
			return longitudine;
		}

	

		public double getTemp() {
			return temp;
		}

		@Override
		public String toString() {
			return "Latitudine: "+String.valueOf(this.latitudine) + " Longitudine: " + String.valueOf(this.longitudine)+ " Temp: "+String.valueOf(this.temp);
		}
		
		
		
		
}
