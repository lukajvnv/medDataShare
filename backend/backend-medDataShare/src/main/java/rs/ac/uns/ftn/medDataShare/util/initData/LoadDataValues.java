package rs.ac.uns.ftn.medDataShare.util.initData;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("data-values")
@PropertySource(value = "classpath:data_values.yml", factory = YamlPropertySourceFactory.class)
public class LoadDataValues {


    @Override
    public String toString() {
        return "LoadDataValues{" +
                "values=" + values +
                '}';
    }

    public List<DataValue> getValues() {
        return values;
    }

    public void setValues(List<DataValue> values) {
        this.values = values;
    }

    private List<DataValue> values = new ArrayList<DataValue>();

}
