package com.llucasreis.gameql.unit;

import com.llucasreis.gameql.datafetchers.GameDataFetcher;
import com.llucasreis.gameql.domain.entities.Game;
import com.llucasreis.gameql.domain.entities.Platform;
import com.llucasreis.gameql.repositories.GameRepository;
import com.llucasreis.gameql.repositories.PlatformRepository;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import graphql.ExecutionResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {DgsAutoConfiguration.class, GameDataFetcher.class})
public class GameDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    GameRepository gameRepository;

    @MockBean
    PlatformRepository platformRepository;

    @BeforeEach
    public void init() {

        Platform platform1 = Platform.builder().id(1L).name("Playstation 4").build();
        Platform platform2 = Platform.builder().id(1L).name("Playstation 3").build();

        Game game1 = Game.builder().id(1L).name("Uncharted 2").releaseYear(2009).platform(platform2).build();
        Game game2 = Game.builder().id(2L).name("Uncharted 4").releaseYear(2016).platform(platform1).build();

        List<Game> games = List.of(game1, game2);

        Mockito.when(gameRepository.findAll()).thenReturn(games);
        Mockito.when(gameRepository.findByNameIsContainingIgnoreCase(Mockito.any(String.class))).thenReturn(List.of(game1));
    }

    @Test
    public void shouldReturnListOfGames() {
        List<String> gameNames = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ games { name releaseYear } }",
                "data.games[*].name"
        );

        Assertions.assertThat(gameNames).hasSize(2);
        Assertions.assertThat(gameNames).contains("Uncharted 2");
        Assertions.assertThat(gameNames).contains("Uncharted 4");
    }

    @Test
    public void shouldFilterListOfGamesByName() {
        List<String> gameNames = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ games(name: \"Uncharted 2\") { name releaseYear } }",
                "data.games[*].name"
        );
        Assertions.assertThat(gameNames).hasSize(1);
        Assertions.assertThat(gameNames).contains("Uncharted 2");
        Assertions.assertThat(gameNames).doesNotContain("Uncharted 4");
    }

    @Test
    public void shouldReturnAnError() {
        Mockito.when(gameRepository.findAll()).thenThrow(new RuntimeException("Internal server error"));

        ExecutionResult result = dgsQueryExecutor.execute(
                "{ games { name releaseYear } }");

        Assertions.assertThat(result.getErrors()).isNotEmpty();
    }
}
