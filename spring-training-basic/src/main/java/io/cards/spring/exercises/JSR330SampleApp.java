package io.cards.spring.exercises;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class JSR330SampleApp {

  public static void main(String[] args) {

  }
}

@Configuration
@ComponentScan
class JSR330SampleConfiguration {

}

class NamedService {

  private NamedDependencyService namedDependencyService;

  @Inject
  public NamedService(NamedDependencyService namedDependencyService) {
    this.namedDependencyService = namedDependencyService;
  }

  public NamedDependencyService getNamedDependencyService() {
    return namedDependencyService;
  }
}

@Named
class NamedDependencyService {

}
