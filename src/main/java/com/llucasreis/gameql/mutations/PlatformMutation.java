package com.llucasreis.gameql.mutations;

import com.llucasreis.gameql.domain.entities.Platform;
import com.llucasreis.gameql.repositories.PlatformRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import java.util.Optional;

@DgsComponent
public class PlatformMutation {

    private final PlatformRepository platformRepository;

    public PlatformMutation(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

//    @DgsData(parentType = "Mutation", field = "addPlatform")
    @DgsMutation
    public Platform addPlatform(@InputArgument("name") String name) {

        Optional<Platform> platformExists = this.platformRepository.findByNameIgnoreCase(name);

        if (platformExists.isPresent()) {
            throw new IllegalArgumentException("Platform already exist");
        }

        return this.platformRepository.save(Platform.builder().name(name).build());
    }
}
