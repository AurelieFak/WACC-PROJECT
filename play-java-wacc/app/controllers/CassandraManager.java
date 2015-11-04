package controllers;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Row;



import org.joda.time.DateTime;
import java.lang.String;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by antonis on 12-10-15.
 */
public class CassandraManager {
    public static CassandraManager instance = null;
    private Cluster cluster;
    public Session session;
    public Date datequery;




    private CassandraManager() throws IOException {
        cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .build();
        session = cluster.connect("weather");
        // Creating table if and only if does not exists !!!
       session.execute("CREATE TABLE IF NOT EXISTS weather.weather(city text, time timestamp, temperature float, humidity float, rain float, wind float, cloud float, visibility float, weather text, icon text, PRIMARY KEY (city, time))");
        LinkedList<String> Cities = new LinkedList<>();
        Cities.add(0,"Marseille");
        Cities.add(1,"Thessaloniki");
        Cities.add(2,"Vancouver");
        Cities.add(3,"London");
        Cities.add(4,"Groningen");
        /*Cities.add(5,"Sidney");
        Cities.add(6,"Johannesburg");
        Cities.add(7,"Lima");
        Cities.add(8,"Cairo");
        Cities.add(9,"Caracas");*/

        for(int i =0;i<Cities.size();i++){
            CityWeather weather = new CityWeather(Cities.get(i));
            System.out.println(weather.datetime);

        InsertData(weather.cityName, weather.tempC, weather.humidity, weather.visibility, weather.windSpeedKmph, weather.precipMM, weather.cloudCover, weather.weatherValue, session, weather.datetime, weather.weatherIcon);

         }
    }
    // Inserting the data which we get from the call !!!
    public static void InsertData(String city, float temperature, float humidity, float visibility, float wind, float rain, float cloud, String weather, Session session, Timestamp date, String icon) {

        session.execute("INSERT INTO weather (city, cloud, humidity, rain, temperature, visibility, wind, weather, time, icon) VALUES(?,?,?,?,?,?,?,?,?,?)", city, cloud, humidity, rain, temperature, visibility, wind, weather, date, icon);
    }

    public static CassandraManager GetInstance() throws IOException {
        if (instance == null)
            instance = new CassandraManager();

        return instance;
    }



    // Asking cassandra for the whole table
    public static List <Row> SelectTime(Session session) {
        ResultSet t = session.execute("SELECT * from weather" );
        List<Row> R = t.all();
        for (int i = 0; i < R.size(); i++) {
            System.out.println(R.get(i));

        }
        return (R);


    }
    public static List <Row> SelectCurrent(Session session){
        DateTime Datequery = new DateTime();

        System.out.println(Datequery);
        //Timestamp UntilTime = new Timestamp(Datequery.toDate().getTime());
        Datequery=Datequery.minusMinutes(1);
       // Datequery=Datequery.minusHours(1);
        System.out.println("DATE DATE DATE DATE");
        Timestamp time = new Timestamp(Datequery.toDate().getTime());
        System.out.println(time);
        ResultSet t = session.execute("SELECT city, temperature, icon from weather WHERE time > ? ALLOW FILTERING",time);
        List<Row> R = t.all();
        return (R);

    }
    public static List <Row> MeanTemp (Session session){
        DateTime Datequery = new DateTime();
        Datequery=Datequery.minusDays(5);
        System.out.println(Datequery);
        Timestamp time = new Timestamp(Datequery.toDate().getTime());
        System.out.println("DATE DATE DATE DATE");
        System.out.println(time);
        ResultSet t = session.execute("SELECT city, temperature from weather WHERE time > ? ALLOW FILTERING",time);
        List<Row> R = t.all();
        return (R);
    }
}