package it.polito.tdp.newufosightings.db;

import it.polito.tdp.newufosightings.model.State;

public class TestDAO {

	public static void main(String[] args) {

		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		
		State s= new State("AL", "Alabama", "Montgomery", 32.361538, -86.279118, 52419, 4779736, "FL GA TN MS");

		System.out.println(dao.loadAllStates());
		System.out.println(dao.getAllFormeByYear(2014));
		System.out.println(dao.getConnectedWeight("tx", 2014, "circle"));
	}

}
