package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;
import it.polito.tdp.newufosightings.model.StatiAvvistamenti;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings() {
		String sql = "SELECT * FROM sighting";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public List<State> loadAllStates() {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<String> getAllFormeByYear(Integer year) {
		String sql="SELECT DISTINCT shape \n" + 
				"FROM sighting\n" + 
				"WHERE YEAR(datetime)=? ";
		List<String>result= new ArrayList<String>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				result.add(rs.getString("shape"));
			}
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<String>getAllStateUsId(){
		String sql=" SELECT s.id\n" + 
				"FROM state s";
		List<String>result= new ArrayList<String>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("id").toLowerCase());
			}
			conn.close();
			return result;
		
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<StatiAvvistamenti>getConnectedWeight(String stato1, Integer anno, String shape){
		String sql="SELECT s.state, COUNT(*)\n" + 
				"FROM neighbor n, sighting s\n" + 
				"WHERE n.state2=s.state\n" + 
				"AND n.state1=? \n" + 
				"AND s.shape= ? \n" + 
				"AND YEAR(s.DATETIME)= ? \n" + 
				"GROUP BY s.state";
		
		List<StatiAvvistamenti>result= new ArrayList<StatiAvvistamenti>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, stato1.toLowerCase());
			st.setString(2, shape);
			st.setInt(3, anno);
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				StatiAvvistamenti sa= new StatiAvvistamenti(rs.getString("state"), rs.getInt("COUNT(*)"));
				result.add(sa);
			}
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	/*public List<StatiAvvistamenti>getConnection(State s1, String shape, Integer year){
		String sql="SELECT s2.state AS st, COUNT(DISTINCT s2.id) AS cnt\n" + 
				"FROM sighting s, neighbor n, sighting s2\n" + 
				"WHERE s.state=n.state1\n" + 
				"AND s2.state=n.state2\n" + 
				"AND s.state=?\n" + 
				"AND s.shape= s2.shape\n" + 
				"AND s.shape=?\n" + 
				"AND YEAR(s2.DATETIME)=YEAR(s.DATETIME)\n" + 
				"AND YEAR(s.DATETIME)=?\n" + 
				"GROUP BY s2.state";
		
		List<StatiAvvistamenti>result= new ArrayList<StatiAvvistamenti>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, s1.getId());
			st.setString(2, shape);
			st.setInt(3, year);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				//State statoVicino= idMapStatiUs.get(rs.getString("state"));
				StatiAvvistamenti s= new StatiAvvistamenti(rs.getString("st"), rs.getInt("cnt"));
				
				result.add(s);
			}
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}*/

}
