import java.util.ArrayList;
import java.util.List;

class WeatherStation {
    private final List<Observer> observers = new ArrayList<>();
    private double temperature;

    public void addObserver(Observer o) { observers.add(o); }
    public void removeObserver(Observer o) { observers.remove(o); }

    public void setTemperature(double temp) {
        this.temperature = temp;
        for (Observer o : observers) o.update(temp);
    }
}
