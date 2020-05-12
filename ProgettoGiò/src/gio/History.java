package gio;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class History {
	final double TEMPOMAX = 200000; //arco temporale della history
	final double TIMESTAMP = System.currentTimeMillis();
	 private ArrayList<Coordinate> coordinates= new ArrayList<>();
	 private ArrayList<Coordinate> coordgoogle= new ArrayList<>();
	
	 public boolean addinHistory(double latitudine,double longitudine,double latgoogle,double longgoogle) {
		 //Da implementare il fatto che nello storico non ci sono tutte le localizzazioni
		
		 double temp = System.currentTimeMillis()-TIMESTAMP;
		
		 //Elimino i dati più vecchi ovviamente li trovo in cima all'arraylist
		Iterator<Coordinate> iter =coordinates.iterator();
		/*
		 * while(iter.hasNext()) { Coordinate coordinate=iter.next();
		 * if(coordinate.getTemp()<(temp-TEMPOMAX)) { iter.remove();
		 * System.out.printf("Rimosso %s\n",coordinate.toString()); } else break; }
		 */
		 System.out.printf("HISTORY: add %f %f %f\n",latitudine,longitudine,temp);
		 coordgoogle.add(new Coordinate(latgoogle,longgoogle,temp));
		 return coordinates.add(new Coordinate(latitudine,longitudine,temp));
	 }
	 
	 public boolean deleteinHistory() {
		 return true;
	 }
	 
	 public boolean clearHistory() {
		 return true;
	 }
	 
	 public List<Coordinate> getCoordinate(){
		 return (List<Coordinate>) coordinates.clone();
	 }
	 public Coordinate getLastCoordinate() {
		 return coordinates.get(coordinates.size()-1);
	 }
	 public Coordinate getLastCoordinateGoogle() {
		 return coordinates.get(coordinates.size()-1);
	 }
	 
	 public boolean isOut() {
		for(Coordinate cord : coordinates) {
			if(UniversityCoordinate.isInUniversity(cord.getLatitudine(), cord.getLongitudine())) {
				return false;
			}
		}
		return true;
	 }
	 public boolean isOutOffice() {
			for(Coordinate cord : coordinates) {
				if(UniversityCoordinate.isInAisle(cord.getLatitudine(), cord.getLongitudine())) {
					return false;
				}
			}
			return true;
		 }
}
