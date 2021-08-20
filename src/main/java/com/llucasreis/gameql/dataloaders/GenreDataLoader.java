package com.llucasreis.gameql.dataloaders;

import com.llucasreis.gameql.domain.entities.Genre;
import com.llucasreis.gameql.repositories.GameGenresRepository;
import com.llucasreis.gameql.services.GameGenresService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "genres")
public class GenreDataLoader implements MappedBatchLoader<Long, List<Genre>> {

    private GameGenresService gameGenresService;

    @Autowired
    public GenreDataLoader(GameGenresService gameGenresService) {
        this.gameGenresService = gameGenresService;
    }

    @Override
    public CompletionStage<Map<Long, List<Genre>>> load(Set<Long> gameIds) {
        return CompletableFuture.supplyAsync(() -> this.gameGenresService.getGenresByGameIds(new ArrayList<>(gameIds)));
    }
}
