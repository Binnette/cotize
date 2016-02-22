package net.bons.comptes.cqrs;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.mongo.MongoClient;
import net.bons.comptes.cqrs.command.ContributeProject;
import net.bons.comptes.service.model.Project;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ContributionHandlerTest {

    @Test
    public void computeTest() {
        // given
        ContributionHandler contributionHandler = new ContributionHandler(mock(MongoClient.class), mock(CommandExtractor.class));
        JsonObject entries = new JsonObject()
                .put("name", "Anniversaire de Vanessa")
                .put("author", "Michel Barret")
                .put("description", "C'est l'anniversaire de Vanessa")
                .put("identifier", "e679d4ed-d")
                .put("passAdmin", "e2f901eb-0")
                .put("amount", 0);
        Project project = new Project(entries);
        ContributeProject contribute = new ContributeProject("Michel", "exemple@exemple.com", 100);

        // when
        Project projectResult = contributionHandler.compute(project, contribute);

        // then
        assertThat(projectResult).isNotNull();
        assertThat(projectResult.getContributions().size()).isEqualTo(1);
        assertThat(projectResult.getAmount()).isEqualTo(100);
    }

    @Test
    public void computeTest2() {
        // given
        ContributionHandler contributionHandler = new ContributionHandler(mock(MongoClient.class), mock(CommandExtractor.class));
        String authorDeal = "Michel";
        JsonObject entriesDeals = new JsonObject()
                .put("author", authorDeal)
                .put("amount", 42)
                .put("email", "foo")
                .put("date", System.currentTimeMillis());
        JsonObject entriesProject = new JsonObject()
                .put("name", "Anniversaire de Vanessa")
                .put("author", "Michel Barret")
                .put("description", "C'est l'anniversaire de Vanessa")
                .put("identifier", "e679d4ed-d")
                .put("passAdmin", "e2f901eb-0")
                .put("amount", 42)
                .put("contributions", new JsonArray().add(entriesDeals));
        Project project = new Project(entriesProject);
        ContributeProject contribute = new ContributeProject(authorDeal, "exemple@exemple.com", 100);

        // when
        Project projectResult = contributionHandler.compute(project, contribute);

        // then
        assertThat(projectResult).isNotNull();
        assertThat(projectResult.getContributions().size()).isEqualTo(1);
        assertThat(projectResult.getAmount()).isEqualTo(42);
    }
}