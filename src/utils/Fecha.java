package utils;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fecha extends GregorianCalendar implements Serializable {
	private static final long serialVersionUID = -1183259664387840252L;

	public Fecha() {
		super();
	}
	
	public Fecha(int day, int month, int year) {
		super(year, month-1, day);
	}
	
	public Fecha(int day, int month, int year, int hour, int minute, int second) {
		super(year, month-1, day, hour, minute, second);
	}
	
	public String stringFecha() {
		return this.get(Calendar.DAY_OF_MONTH)+"/"+
			   (this.get(Calendar.MONTH)+1)+"/"+
			   this.get(Calendar.YEAR);
	}
	
	public String stringFechaReloj() {
		return stringFecha()+" "+stringReloj();
	}
	
	public String stringReloj() {
		return String.format ("%02d:%02d", 
				this.get(Calendar.HOUR_OF_DAY), 
				this.get(Calendar.MINUTE));
	}
	
	public static void setTime(Fecha fecha, Date date) {
		if (date != null) {
			fecha = new Fecha();
			fecha.setTime(date);
		}
	}

}
