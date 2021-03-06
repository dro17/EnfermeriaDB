package application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import application.classes.Evento;

public class EventoBean {
	//Parametros conexion BD
	static final String JDBC_Driver = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/Enfermeria";
	static final String DB_USER = "encarnados";
	static final String DB_PASS = "amss";
	private ResultSet rs = null;
	private Connection c = null;
	private Statement st = null;

	//Constructor
	public EventoBean() {
		try {
			Class.forName(JDBC_Driver);
			c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			st = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Evento" + 
					"(idHuesp varchar(20), " +
					"numEvento int, " +  
					"fecReg Timestamp," + 
					"descEvento varchar(250), " + 
					"nomEmfermero varchar(80), " + 
					"bReceta boolean, " + 
					"dirReceta varchar(200), " + 
					"CONSTRAINT FK_HuespEvento FOREIGN KEY (idHuesp) REFERENCES Huesped(curp) On Delete Cascade, " + 
					"CONSTRAINT PK_Evento PRIMARY KEY (idHuesp, numEvento));";
			st.executeUpdate(sql);
			st.close();
			c.close();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	//Agregar Evento a la BD
	public Evento create(Evento ev) {
		try {
			c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			st = c.createStatement();
			String sql = "INSERT INTO Evento"
					+ "(idHuesp, numEvento, fecReg, descEvento, nomEnfermero, bReceta, dirReceta)"
					+ "VALUES ('" + ev.getIdHuesp()
					+ "', " + ev.getNumEvento()
					+ "', '" + toTimestamp(ev.getFecReg())
					+ "', '" + ev.getDescEvento()
					+ "', '" + ev.getNomEnfermero()
					+ "', '" + ev.hasReceta()
					+ "', '" + ev.getDirReceta() + "');";
			st.executeUpdate(sql);
			st.close();
			c.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return ev;
	}

	//Modificar Evento en la BD
	public Evento update(Evento ev) {
		try {
			c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			st = c.createStatement();
			String sql = "UPDATE Evento SET "
					+ "', fecReg = '" + toTimestamp(ev.getFecReg())
					+ "', descEvento = '" + ev.getDescEvento()
					+ "', nomEnfermero = '" + ev.getNomEnfermero()
					+ "', bReceta = '" + ev.hasReceta()
					+ "', dirReceta = '" + ev.getDirReceta() 
					+ "' WHERE idHuesp = '" + ev.getIdHuesp() + "' AND numEvento = " + ev.getNumEvento() + ";";
			st.executeUpdate(sql);
			st.close();
			c.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ev;
	}

	//Traer Medicamento de la BD
	public Evento retrieve(String idHuesp, int numEvento) {
		Evento ev = new Evento();
		ev.setIdHuesp(idHuesp);
		ev.setNumEvento(numEvento);
		try {
			c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			st = c.createStatement();
			rs = st.executeQuery("SELECT * FROM Evento WHERE idHuesp = '" + idHuesp	
					+ "' AND numEvento = " + numEvento + ";");
			ev.setFecReg(toLocalDateTime(rs.getString("fecReg")));
			ev.setDescEvento(rs.getString("descEvento"));
			ev.setNomEnfermero(rs.getString("nomEnfermero"));
			ev.setDirReceta(rs.getString("dirReceta"));
			rs.close();
			st.close();
			c.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ev;
	}

	//Borrar Evento de la BD
	public void delete(Evento ev) {
		try {
			c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			st = c.createStatement();
			String sql = "DELETE FROM Evento"
					+ " WHERE idHuesp = '" + ev.getIdHuesp() + "' AND numEvento = " + ev.getNumEvento() + ";";
			st.executeUpdate(sql);
			st.close();
			c.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	//Transformaciones entre LocalDateTime y sql Timestamp
	private String toTimestamp(LocalDateTime ldt) {
		return ldt.toString().substring(0, 9) + " " + ldt.toString().substring(11);
	}

	private LocalDateTime toLocalDateTime(String timestamp) {
		return LocalDateTime.parse(timestamp.substring(0, 9) + "T" + timestamp.substring(11));
	}
}
