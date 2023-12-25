package pw.avvero.sljc;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyService {

    @PostConstruct
    public void init() {
        log.debug("Hello from MyService");
    }

}
