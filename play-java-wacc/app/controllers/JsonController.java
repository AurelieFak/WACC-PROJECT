package controllers;

import com.datastax.driver.core.Row;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.json.simple.JSONObject;
import play.mvc.Result;
import play.twirl.api.Html;
import scala.util.parsing.json.JSONObject$;
import views.html.Bootstrap;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static play.mvc.Results.ok;

/**
 * Created by antonis on 23-10-15.
 */
public class JsonController {

    Gson gson = new Gson();

    public Result Jsonprinter() throws IOException {
        CassandraManager manager = CassandraManager.GetInstance();

        //CityWeather weather = new CityWeather("Thessaloniki");
        List<weather> printList   = new LinkedList<>();
        List <Row> r = manager.SelectCurrent(manager.session);
        for(int i=0;i<r.size();i++) {
            String city = r.get(i).getString("city");
            // Date time = r.get(i).getTimestamp("time");
            Float temperature = r.get(i).getFloat("temperature");
            //Float humidity = r.get(i).getFloat("humidity");
            // Float visibility = r.get(i).getFloat("visibility");
            // Float wind = r.get(i).getFloat("wind");
            //Float rain = r.get(i).getFloat("rain");
            //Float cloud = r.get(i).getFloat("cloud");
            String icon = r.get(i).getString("icon");
            printList.add(new weather(city,temperature,icon));

        }
        return  ok(gson.toJson(printList));
    }
    public Result Htmlprinter() throws IOException {
        CassandraManager manager = CassandraManager.GetInstance();


       // CityWeather weather = new CityWeather("Thessaloniki");
        List<weather> printList   = new LinkedList<>();
        List <Row> r = manager.SelectCurrent(manager.session);
        for(int i=0;i<r.size();i++) {
            String city = r.get(i).getString("city");
            // Date time = r.get(i).getTimestamp("time");
            Float temperature = r.get(i).getFloat("temperature");
            //Float humidity = r.get(i).getFloat("humidity");
            // Float visibility = r.get(i).getFloat("visibility");
            // Float wind = r.get(i).getFloat("wind");
            //Float rain = r.get(i).getFloat("rain");
            //Float cloud = r.get(i).getFloat("cloud");
            String icon = r.get(i).getString("icon");
            printList.add(new weather(city,temperature,icon));

        }

        return  ok(Bootstrap.render("Weather Analysis", printList, new Html("<div>LIST</div>" )));
    }
}




