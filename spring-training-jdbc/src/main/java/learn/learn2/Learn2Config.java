package learn.learn2;

import learn.Processor;
import learn.common.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
@Import(Config.class)
public class Learn2Config {

    @Autowired
    JdbcTemplate template;

    @Bean
    Processor<String, List<String>> processor() {
        return ((Processor<String, String>) this::forDate)
                .andThen(this::getNames);
    }

    private String forDate(String ignored) {
        return "1/12/2017";
    }

    private List<String> getNames(String date) {
        return template.queryForList("SELECT full_name FROM log_data where entry_date = ?", String.class, date);
    }
}
