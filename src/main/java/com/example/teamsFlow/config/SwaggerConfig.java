package com.example.teamsFlow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("TeamsFlow API")
                .description("API REST para gerenciamento de equipes ágeis, projetos e tarefas.")
                .version("1.0.0"))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components().addSecuritySchemes("bearerAuth",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            .tags(List.of(
                new Tag().name("Auth").description("Autenticação e registro"),
                new Tag().name("Users").description("Gerenciamento de usuários"),
                new Tag().name("Teams").description("Gerenciamento de equipes"),
                new Tag().name("Members").description("Gerenciamento de membros"),
                new Tag().name("Projects").description("Gerenciamento de projetos"),
                new Tag().name("Sprints").description("Gerenciamento de sprints"),
                new Tag().name("Tasks").description("Gerenciamento de tarefas"),
                new Tag().name("Kanban Boards").description("Boards kanban"),
                new Tag().name("Kanban Columns").description("Colunas dos boards"),
                new Tag().name("Transition Rules").description("Regras de transição"),
                new Tag().name("Leadership Delegations").description("Delegações de liderança"),
                new Tag().name("Audit Logs").description("Logs de auditoria"),
                new Tag().name("History").description("Histórico de transições")
            ));
    }
}
