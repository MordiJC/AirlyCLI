import io.github.mordijc.format.SensorFormatter;
import io.github.mordijc.rest.containers.SensorInfo;
import io.github.mordijc.rest.containers.common.Address;
import io.github.mordijc.rest.containers.common.Location;
import io.github.mordijc.rest.containers.common.Measurement;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class SensorFormatterTest {

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
                99900.9534,
                0.0
        );

        Assert.assertEquals(
                "╔═════╦═════════════════════════╗\n" +
                        "║ 153 ║ ul. Przybyszewskiego 56 ║\n" +
                        "╠═════╝         Kraków          ║\n" +
                        "║ PM 2.5       44 μg/m³    176% ║\n" +
                        "║ PM 10        82 μg/m³    164% ║\n" +
                        "║ Pressure    999 hPa           ║\n" +
                        "║ Temp.         0 °C            ║\n" +
                        "║ Humidity      0 %             ║\n" +
                        "╚═══════════════════════════════╝\n",
                new SensorFormatter().format(sensorInfo, measurement)
        );
    }
}
