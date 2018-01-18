import io.github.mordijc.format.SensorFormatter;
import io.github.mordijc.rest.containers.SensorInfo;
import io.github.mordijc.rest.containers.common.Address;
import io.github.mordijc.rest.containers.common.Location;
import io.github.mordijc.rest.containers.common.Measurement;
import org.junit.Test;

import java.util.Date;

public class SensorFormatterManualTest {

    @Test
    public void formatTest() {
        SensorInfo sensorInfo = new SensorInfo(
                new Address("56", "ul. Przybyszewskiego", "Kraków", "Polska"),
                123,
                new Location(0.0, 0.0),
                "sensor name",
                2,
                "Airly"
                );

        Measurement measurement = new Measurement(
                153,
                0.0,
                new Date(),
                33.2,
                43.945,
                82.124,
                2,
                9990.9534,
                0.0
        );

        System.out.println(SensorFormatter.format(sensorInfo, measurement));
    }
}
