package learn.learn4;

import learn.Processor;
import learn.common.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Configuration
@Import(Config.class)
public class Learn4Config {

    @Autowired
    JdbcTemplate template;

    @Bean
    Processor<String, Integer> processor() {
        return ((Processor<String, List<CountPerName>>) this::getCounts)
                .andThen(this::insertBenefits);
    }

    private Integer insertBenefits(List<CountPerName> countPerNames) {
        int i = 0;
        for(CountPerName countPerName : countPerNames) {
            i += template.update("INSERT INTO benefits (full_name, percent) VALUES (?, ?)", countPerName.getName(), countPerName.getPercent());
        }
        return i;
    }

    private List<CountPerName> getCounts(String s) {
        return template.query("SELECT full_name, COUNT(*) as `day_count` FROM log_data GROUP BY full_name",
                (rs, i) -> new CountPerName(rs.getString("full_name"), rs.getInt("day_count")));

    }

    public static class CountPerName {
        private final String name;
        private final Integer count;

        public CountPerName(String name, Integer count) {
            this.name = name;
            this.count = count;
        }


        public String getName() {
            return name;
        }

        public Double getPercent() {
            return count > 1 ? count * 0.03 : 0;
        }
    }
}
