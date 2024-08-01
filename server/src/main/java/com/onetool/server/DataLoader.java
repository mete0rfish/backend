package com.onetool.server;

import com.onetool.server.blueprint.Blueprint;
import com.onetool.server.blueprint.repository.BlueprintRepository;
import com.onetool.server.category.FirstCategory;
import com.onetool.server.category.FirstCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("default")
@Component
public class DataLoader implements CommandLineRunner {

    private final FirstCategoryRepository firstCategoryRepository;

    public DataLoader(FirstCategoryRepository firstCategoryRepository) {
        this.firstCategoryRepository = firstCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        final FirstCategory firstCategory1 = firstCategoryRepository.save(
                FirstCategory.builder()
                        .id(1L)
                        .name("building")
                        .build()
        );

        final FirstCategory firstCategory2 = firstCategoryRepository.save(
                FirstCategory.builder()
                        .id(2L)
                        .name("civil")
                        .build()
        );

        final FirstCategory firstCategory3 = firstCategoryRepository.save(
                FirstCategory.builder()
                        .id(3L)
                        .name("interior")
                        .build()
        );

        final FirstCategory firstCategory4 = firstCategoryRepository.save(
                FirstCategory.builder()
                        .id(4L)
                        .name("machine")
                        .build()
        );

        final FirstCategory firstCategory5 = firstCategoryRepository.save(
                FirstCategory.builder()
                        .id(5L)
                        .name("electric")
                        .build()
        );

        final FirstCategory firstCategory6 = firstCategoryRepository.save(
                FirstCategory.builder()
                        .id(6L)
                        .name("etc")
                        .build()
        );
    }
}