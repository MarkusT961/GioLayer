package gio;
import java.util.ArrayList;
import java.util.List;
public class LocalizationGioAisle {
	/**
	 * 
	 */
	private History history = new History();
	private int contoutdoor=0;
	/**
	 * Controllo lo storico e predico una nuova posizione.
	 * @param latitudine
	 * @param longitudine
	 * @return
	 */
	
	public double[] getCoordinate(double latitudine, double longitudine) {
		//Analizzo prima la struttura della history
		
		System.out.println("#######################################\n");
		System.out.printf("ANALIZZO %f %f\n ################\n",latitudine,longitudine);
		List<Coordinate> list =history.getCoordinate();
		/*
		 * for(Coordinate cordinate : list)
		 * System.out.printf("%s ",cordinate.toString());
		 * System.out.println("\n ***********\n");
		 */
		
//		if(!UniversityCoordinate.isInUniversity(latitudine,longitudine)) {
//			//CONTROLLO DALLA HISTORY SE E' SEMPRE STATO FUORI
//			if(contoutdoor >= 3) {//Suppongo allora che sia fuori
//				System.out.println("PERSONA FUORI\n");
//				double[] coord = {latitudine,longitudine};
//				history.addinHistory(latitudine, longitudine);
//				return coord;
//			}
//			if(history.isOut()) { //sono stato sempre fuori
//				double[] coord = {latitudine,longitudine};
//				history.addinHistory(latitudine, longitudine);
//				return coord;
//			}
//			contoutdoor++;
//		}
//		else {
//			contoutdoor=0;
//		}
//		
		if(list.size() <= 1) {
			//History non ancora utilizzabile
			double[] coord = {latitudine,longitudine};
			history.addinHistory(latitudine, longitudine,latitudine,longitudine);
			//Se non è ammissibile la posizione che devo fare???
			//per ora metto che lo aggiungo anche se non è ammisssibile
			return coord;
		}
		/**
		 * Prima di fare tutti i casi controllo se c'è stato uno spostamento oppure no
		 * if coordz dei pozzetti allora la riporto nella traiettoia
		 * ricavata dalla storico
		 * 
		 * if coordinata è fuori dall'edificio devo allora controlloare
		 * dallo storico se molto probabilmente sono fuori
		 *
		 * 
		 * else aggiungo la nuova coordinata nello storico.
		 */
		Coordinate cord =history.getLastCoordinate();
		double latreal=cord.getLatitudine(), longreal=cord.getLongitudine();
		if(!UniversityCoordinate.isInAisle(latitudine,longitudine)) {
			//Inizio correzione. Riporto la localizzazione nell'area ammissibile.
			//SCOPRIRE LA TRAIETTORIA
			double degree = degreeEstimate(latitudine,longitudine);
			System.out.printf("ESTIMATE ANGLE: %f\n", degree);
			//System.out.printf("%b\n",UniversityCoordinate.isAmissiblePoint(latitudine, longitudine));
			switch((int)degree) {
			case 0: //NORD
				if(latitudine > cord.getLatitudine()) { //Vedere schema sul block notes
					latreal = latitudine;
				}	
				break;
			case 1: //EST
				if(longitudine > cord.getLongitudine()) {
					longreal = longitudine;
				}
				break;
			case 2: //SUD
				if(latitudine < cord.getLatitudine()) {
					latreal = latitudine;	
				}
				break;
			case 3: //OVEST
				if(longitudine < cord.getLongitudine()) {
					longreal = longitudine;
				}
			}
		//Prima di uscire dal if devo ricontrollare se il punto è ammissibile
			//System.out.printf("coordinate prima della seconda prova %f %f\n", latreal,longreal);
			if(!UniversityCoordinate.isInAisle(latreal, longreal)) {
				//se non è ammissibile
				if(UniversityCoordinate.isInFourQuadr(latreal, longreal)) {
					double[] result = correctionSecond(latitudine,longitudine);
					longreal =result[1];
					latreal = result[0];
				}
				else {
					if(longitudine <= UniversityCoordinate.GRANDAREANORDOVEST[1]) {
						longreal = UniversityCoordinate.GRANDAREANORDOVEST[1]+0.000001;
						latreal=latitudine;
					}
					if(longitudine >= UniversityCoordinate.GRANDAREANORDEST[1]) {
						longreal = UniversityCoordinate.GRANDAREANORDEST[1]-0.000001;
						latreal=latitudine;
					}
				}
				
			}
			history.addinHistory(latreal, longreal,latitudine,longitudine);
			double[] cordinates = {latreal,longreal};
			return cordinates;
		}
		history.addinHistory(latitudine, longitudine,latitudine,longitudine);
		double[] cordinates = {latitudine,longitudine};
		return cordinates;
}
	
	/**
	 * 
	 * @param lat1
	 * @param long1
	 * @param lat2
	 * @param long2
	 * @return angle in gradi 0°N 90°E 180°S 270°O
	 */
	public static double angleFromCoordinate(double lat1, double long1, double lat2, double long2) {
		double dLon = (long2 - long1);
		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)* Math.cos(lat2) * Math.cos(dLon);
		double brng = Math.atan2(y, x);
		brng = Math.toDegrees(brng);
		brng = (brng + 360) % 360;
		//brng = 360 - brng; // count degrees counter-clockwise - remove to make clockwise
		return brng;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public double degreeEstimate(double lat1 , double long1) {
		List<Coordinate> list = history.getCoordinate();
		Coordinate cod =history.getLastCoordinateGoogle();
			double angle =angleFromCoordinate(cod.getLatitudine(),cod.getLongitudine(),lat1,long1);
			System.out.printf("Angle: %f", angle);
			double indice = 0;
			if((angle >= 315 && angle <= 360) || (angle >= 0 && angle < 80)) 
				indice = 0; //NORD
			if(angle >= 80 && angle < 135)
				indice = 1; // EST
			if(angle >= 135 && angle < 225)
				indice = 2; //SUD
			if(angle >= 225 && angle <315)
				indice = 3; //OVEST
			
		return indice;
	}
	double[] correctionSecond(double lat, double longe) {
		//PRIMO QUADRANTE
		if(lat <= UniversityCoordinate.FIRSTGRANDNORDOVEST[0] && lat >= UniversityCoordinate.FIRSTGRANDSUDOVEST[0] && longe >= UniversityCoordinate.FIRSTGRANDNORDOVEST[1] && longe <= UniversityCoordinate.FIRSTGRANDNORDEST[1])
			return calculateDistanceQuad(lat,longe,UniversityCoordinate.FIRSTGRANDNORDOVEST[0],UniversityCoordinate.FIRSTGRANDSUDOVEST[0],UniversityCoordinate.FIRSTGRANDNORDOVEST[1],UniversityCoordinate.FIRSTGRANDNORDEST[1]);
		if(lat <= UniversityCoordinate.SECONDGRANDNORDOVEST[0] && lat >= UniversityCoordinate.SECONDGRANDSUDOVEST[0] && longe >= UniversityCoordinate.SECONDGRANDNORDOVEST[1] && longe <= UniversityCoordinate.SECONDGRANDNORDEST[1])
			return calculateDistanceQuad(lat,longe,UniversityCoordinate.SECONDGRANDNORDOVEST[0],UniversityCoordinate.SECONDGRANDSUDOVEST[0],UniversityCoordinate.SECONDGRANDNORDOVEST[1],UniversityCoordinate.SECONDGRANDNORDEST[1]);
		if(lat <= UniversityCoordinate.THIRDGRANDNORDOVEST[0] && lat >= UniversityCoordinate.THIRDGRANDSUDOVEST[0] && longe >= UniversityCoordinate.THIRDGRANDNORDOVEST[1] && longe <= UniversityCoordinate.THIRDGRANDNORDEST[1])
			return calculateDistanceQuad(lat,longe,UniversityCoordinate.THIRDGRANDNORDOVEST[0],UniversityCoordinate.THIRDGRANDSUDOVEST[0],UniversityCoordinate.THIRDGRANDNORDOVEST[1],UniversityCoordinate.THIRDGRANDNORDEST[1]);
		if(lat <= UniversityCoordinate.FOURGRANDNORDOVEST[0] && lat >= UniversityCoordinate.FOURGRANDSUDOVEST[0] && longe >= UniversityCoordinate.FOURGRANDNORDOVEST[1] && longe <= UniversityCoordinate.FOURGRANDNORDEST[1])
			return calculateDistanceQuad(lat,longe,UniversityCoordinate.FOURGRANDNORDOVEST[0],UniversityCoordinate.FOURGRANDSUDOVEST[0],UniversityCoordinate.FOURGRANDNORDOVEST[1],UniversityCoordinate.FOURGRANDNORDEST[1]);
		return new double[] {lat,longe};
	
	}
	
	
	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			dist = dist*1000;
			return (dist);
		}
	}
	
	//CALCOLO DISTANCE IN UN QUADRANTE
	double[] calculateDistanceQuad(double lat,double longe,double latnord,double latsud,double longovest,double longest ) {
		ArrayList<CorrectionCoordinate> correct= new ArrayList<>();
		double laticorr = latnord-0.00001;
		double distanceA = distance(lat,longe,laticorr,longe);
		correct.add(new CorrectionCoordinate(laticorr,longe,distanceA));
		//SUD
		laticorr = latsud+0.00001;
		double distanceB = distance(lat,longe,laticorr,longe);
		correct.add(new CorrectionCoordinate(laticorr,longe,distanceB));
		//OVEST
		double longcorr = longovest-0.00001;
		double distanceC = distance(lat,longe,lat,longcorr);
		correct.add(new CorrectionCoordinate(lat,longcorr,distanceC));
		//EST
		longcorr = longest-0.00001;
		double distanceD = distance(lat,longe,lat,longcorr);
		correct.add(new CorrectionCoordinate(lat,longcorr,distanceD));
		
		CorrectionCoordinate min = correct.get(0);
		for(CorrectionCoordinate c : correct ) {
			if(c.getDistance()< min.getDistance())
				min = c;
		}
		
		return min.getCoordinate();
	}
	
}
