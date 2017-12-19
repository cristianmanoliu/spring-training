package learn.learn3;

import learn.Processor;
import learn.common.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonMap;

@Configuration
@Import(Config.class)
public class Learn3Config {

    @Autowired
    NamedParameterJdbcTemplate template;

    @Bean
    Processor<String, List<String>> processor() {
        return ((Processor<String, List<String>>) this::forDates)
                .andThen(this::getNames);
    }

    private List<String> forDates(String ignored) {
        return Arrays.asList("1/12/2017", "");
    }

    private List<String> getNames(List<String> dates) {
        return template.queryForList("SELECT full_name FROM log_data where entry_date in (:dates)",
                singletonMap("dates", dates), String.class);
    }
}
