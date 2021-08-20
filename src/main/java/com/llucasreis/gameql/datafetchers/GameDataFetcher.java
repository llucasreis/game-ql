package com.llucasreis.gameql.datafetchers;

import com.llucasreis.gameql.dataloaders.GenreDataLoader;
import com.llucasreis.gameql.domain.entities.Game;
import com.llucasreis.gameql.domain.entities.Genre;
import com.llucasreis.gameql.domain.entities.Platform;
import com.llucasreis.gameql.repositories.GameRepository;
import com.llucasreis.gameql.repositories.PlatformRepository;
import com.netflix.graphql.dgs.*;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class GameDataFetcher {

    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;

    public GameDataFetcher(GameRepository gameRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.platformRepository = platformRepository;
    }

    // The parentType is "Query", the field name is derived from the method name.
    // its the same as
    // @DgsData(parentType = "Query", field = "games")
    @DgsQuery
    public List<Game> games(@InputArgument String name) {
        if (name == null) {
            return this.gameRepository.findAll();
        }
        return this.gameRepository.findByNameIsContainingIgnoreCase(name);
    }

    // in this case its better to use data loader
    @DgsData(parentType = "Game", field = "platform")
    public Platform platform(DgsDataFetchingEnvironment dfe) {
        Game game = dfe.getSource();
        return this.platformRepository.findById(game.getPlatform().getId()).orElse(null);
    }

    @DgsData(parentType = "Game", field = "genres")
    public CompletableFuture<Genre> genre(DgsDataFetchingEnvironment dfe) {
        DataLoader<Long, Genre> genreDataLoader = dfe.getDataLoader(GenreDataLoader.class);

        Game game = dfe.getSource();

        return genreDataLoader.load(game.getId());
    }
}
