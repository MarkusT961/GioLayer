package gio;


public class UniversityCoordinate {
	public final static  double[] NORDOVEST = {43.721222, 10.408160};
	public final static double[] NORDEST = {43.721222, 10.408639};
	public final static double[] SUDOVEST = {43.720091, 10.408179};
	public final double[] SUDEST = {43.720091,10.408628};
	
	public final double POZZETTOMISU = 11.80; //metri sia in larghezza che in lunghezza
	
	//FIRST POZZETTO
	public final static double[] FIRSTNORDOVEST= {43.721108,10.408318};
	public final static double[] FIRSTSUDOVEST= {43.721000,10.408319};
	public final static double[] FIRSTNORDEST= {43.721109,10.408474};

	//SECOND POZZETTO
	public final static double[] SECONDNORDOVEST = {43.720822,10.408321};
	public final static double[] SECONDSUDOVEST= {43.720721,10.408316};
	public final static double[] SECONDNORDEST= {43.720821,10.408476};
	
	//THIRD POZZETTO
	public final static double[] THIRDNORDOVEST = {43.720598,10.408322};
	public final static double[] THIRDSUDOVEST= {43.720487,10.408319};
	public final static double[] THIRDNORDEST= {43.720595,10.408474};
	//FOUR POZZETTO
	public final static double[] FOURNORDOVEST = {43.720313,10.408316};
	public final static double[] FOURSUDOVEST= {43.720197,10.408316};
	public final static double[] FOURNORDEST= {43.720314,10.408474};
	
	//FIRST GRANDQUADRATO
	public final static double[] FIRSTGRANDNORDOVEST = {43.721149,10.408280};
	public final static double[] FIRSTGRANDNORDEST = {43.721149,10.408533};
	public final static double[] FIRSTGRANDSUDOVEST = {43.720915,10.408288};
	
	//SECOND GRANDQUADRATO 
	public final static double[] SECONDGRANDNORDOVEST = {43.720873,10.408280};
	public final static double[] SECONDGRANDNORDEST = {43.720873,10.408533};
	public final static double[] SECONDGRANDSUDOVEST = {43.720692,10.408280};
	
	//THIRD GRANDQUADRATO 
	public final static double[] THIRDGRANDNORDOVEST = {43.720668,10.408280};
	public final static double[] THIRDGRANDNORDEST = {43.720668,10.408533};
	public final static double[] THIRDGRANDSUDOVEST = {43.720507,10.408280};
	
	//FOUR GRANDQUADRATO 
	public final static double[] FOURGRANDNORDOVEST = {43.720415,10.408280};
	public final static double[] FOURGRANDNORDEST = {43.720173, 10.408538};
	public final static double[] FOURGRANDSUDOVEST = {43.720182,10.408280};
	
	//GRANDAREA (Comprende i 4 Grandquadrati quindi anche i pozzetti e tutto il corridoio
	public final static double[] GRANDAREANORDOVEST = {43.721152,10.408261};
	public final static double[] GRANDAREANORDEST = {43.721152,10.408540};
	public final static double[] GRANDAREASUDOVEST = {43.720157,10.408261};
  	
	
	static public boolean isAmissiblePoint (double latitudine,double longitudine) {
		//Verifico se il punto è all'interno del dipartimento
		if(isInUniversity(latitudine,longitudine)) {
			if(latitudine <= FIRSTNORDOVEST[0] && latitudine >= FIRSTSUDOVEST[0] && longitudine >= FIRSTNORDOVEST[1] && longitudine <= FIRSTNORDEST[1] )
				return false;
			if(latitudine <= SECONDNORDOVEST[0] && latitudine >= SECONDSUDOVEST[0] && longitudine >= SECONDNORDOVEST[1] && longitudine <= SECONDNORDEST[1] )
				return false;
			if(latitudine <= THIRDNORDOVEST[0] && latitudine >= THIRDSUDOVEST[0] && longitudine >= THIRDNORDOVEST[1] && longitudine <= THIRDNORDEST[1] )
				return false;
			if(latitudine <= FOURNORDOVEST[0] && latitudine >= FOURSUDOVEST[0] && longitudine >= FOURNORDOVEST[1] && longitudine <= FOURNORDEST[1] )
				return false;
			return true;
		}	
		else 
			return false;
		
	}
	
	static public boolean isInUniversity (double latitudine,double longitudine) {
		if(latitudine<= NORDOVEST[0] && latitudine >= SUDOVEST[0] && longitudine >= NORDOVEST[1] && longitudine <= NORDEST[1]) {
			return true;
		}
		else 
			return false;
	}
	
	static public boolean isInFourQuadr(double latitudine,double longitudine) {
		if(latitudine <= FIRSTGRANDNORDOVEST[0] && latitudine >= FIRSTGRANDSUDOVEST[0] && longitudine >= FIRSTGRANDNORDOVEST[1] && longitudine <= FIRSTGRANDNORDEST[1])
			return true;
		if(latitudine <= SECONDGRANDNORDOVEST[0] && latitudine >= SECONDGRANDSUDOVEST[0] && longitudine >= SECONDGRANDNORDOVEST[1] && longitudine <= SECONDGRANDNORDEST[1])
			return true;
		if(latitudine <= THIRDGRANDNORDOVEST[0] && latitudine >= THIRDGRANDSUDOVEST[0] && longitudine >= THIRDGRANDNORDOVEST[1] && longitudine <= THIRDGRANDNORDEST[1])
			return true;
		if(latitudine <= FOURGRANDNORDOVEST[0] && latitudine >= FOURGRANDSUDOVEST[0] && longitudine >= FOURGRANDNORDOVEST[1] && longitudine <= FOURGRANDNORDEST[1])
			return true;
		return false;	
	}
	
	static public boolean isInGrandArea(double latitudine, double longitudine) {
		if(latitudine <= GRANDAREANORDOVEST[0] && latitudine >= GRANDAREASUDOVEST[0] && longitudine >= GRANDAREANORDOVEST[1] && longitudine <= GRANDAREANORDEST[1])
			return true;
		return false;
	}
	
	
	static public boolean isInAisle(double latitudine, double longitudine) {
		if(isInUniversity(latitudine,longitudine))
			//Controllo se non è nella GrandArea
			if(isInGrandArea(latitudine,longitudine)) 
				if(!isInFourQuadr(latitudine,longitudine))
					return true;
		return false;	
	}
	
}
