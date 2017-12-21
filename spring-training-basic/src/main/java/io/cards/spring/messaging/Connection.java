package io.cards.spring.messaging;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class Connection {
    private String host;

    public Connection() {
        this.host = this.hashCode() + ".local";
    }

    public String getHost() {
        return host;
    }
}
