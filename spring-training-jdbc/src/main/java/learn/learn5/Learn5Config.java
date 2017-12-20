package learn.learn5;

import learn.Processor;
import learn.common.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Import(Config.class)
public class Learn5Config {

    @Autowired
    JdbcTemplate template;

    @Bean
    Processor<String, Integer> processor() {
        return ((Processor<String, List<CountPerName>>) this::getCounts)
                .andThen(this::batchInsertBenefits2);
    }

    private Integer batchInsertBenefits(List<CountPerName> countPerNames) {
        List<Object[]> batches = new ArrayList<>();
        for(CountPerName countPerName : countPerNames) {
            batches.add(new Object[]{countPerName.getName(), countPerName.getPercent()});
        }
        template.batchUpdate("INSERT INTO benefits (full_name, percent) VALUES (?, ?)", batches);
        return countPerNames.size();
    }

    private Integer batchInsertBenefits2(List<CountPerName> countPerNames) {
        int[][] result = template.batchUpdate("INSERT INTO benefits (full_name, percent) VALUES (?, ?)", countPerNames, 50, (ps, arg) -> {
            ps.setString(1, arg.getName());
            ps.setDouble(2, arg.getPercent());
        });
        return countPerNames.size();
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
