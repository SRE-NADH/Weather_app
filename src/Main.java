import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class Main {
    private static final String API_BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly";
    private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) throws IOException {

        System.out.println("Hello world!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            printMenu();
            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    getWeatherByDate();
                    break;
                case 2:
                    getWindSpeedByDate();
                    break;
                case 3:
                    getPressureByDate();
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    reader.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    private static void printMenu() {
        System.out.println("\n1. Get weather\n2. Get Wind Speed\n3. Get Pressure\n0. Exit");
        System.out.print("Enter your choice: ");
    }
    private static JSONObject getWeatherData() throws IOException {
        String url = API_BASE_URL + "?q=" + "London"+ "&appid=" + API_KEY;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        return new JSONObject(response.toString());
    }
    private static void getWeatherByDate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the date  (YYYY-MM-DD): ");
        String date = reader.readLine();

        JSONObject weatherData = getWeatherData();

        JSONArray list = weatherData.getJSONArray("list");
        boolean found = false;

        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            if (item.getString("dt_txt").startsWith(date)) {
                JSONObject main = item.getJSONObject("main");
                double temperature = main.getDouble("temp");
                System.out.println("Temperature on " + date + ": " + temperature);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Data not available for the specified date.");
        }
    }

    private static void getWindSpeedByDate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = reader.readLine();
        JSONObject weatherData = getWeatherData();
        JSONArray list = weatherData.getJSONArray("list");
        boolean found =false;

        for(int i=0;i<list.length();i++){
            JSONObject item = list.getJSONObject(i);
            if(item.getString("dt_txt").startsWith(date)){
              JSONObject wind = item.getJSONObject("wind");
                double speed = wind.getDouble("speed");
                System.out.println("Wind speed on " + date + ": " + speed);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Data not available for the specified date.");
        }

    }
    private static void getPressureByDate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = reader.readLine();
        JSONObject weatherData = getWeatherData();
        JSONArray list = weatherData.getJSONArray("list");
        boolean found =false;

        for(int i=0;i<list.length();i++){
            JSONObject item = list.getJSONObject(i);
            if(item.getString("dt_txt").startsWith(date)){
                JSONObject main = item.getJSONObject("main");
                double pressure = main.getDouble("pressure");
                System.out.println("Pressure on " + date + ": " +pressure );
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Data not available for the specified date.");
        }
    }
}