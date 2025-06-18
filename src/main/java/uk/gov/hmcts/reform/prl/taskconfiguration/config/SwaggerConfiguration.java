package uk.gov.hmcts.reform.prl.taskconfiguration.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.prl.taskconfiguration.Application;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("default")
            .packagesToScan(Application.class.getPackageName() + ".controllers")
            .build();
    }

}
