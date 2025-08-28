package ir.example.demo.service;

import ir.example.demo.exception.UniqueNameGenerationException;
import ir.example.demo.model.NameCatalog;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@Service
public class RandomNameGenerator {
    private static final Long MAX_SEQUENTIAL_COUNT = 1_000_000L;
    private final List<NameCatalog> nameCatalogs;
    private final Integer nameCatalogCount;

    public RandomNameGenerator(List<NameCatalog> nameCatalogs) {
        this.nameCatalogs = nameCatalogs;
        this.nameCatalogCount = nameCatalogs.size();
    }

    /**
     * Generates a set of random full names using the provided NameCatalog list.
     * The maximum number of unique combinations is defined by {@code maxUniqCount}
     * combination format is {@code firstName + midName + lastName}.
     *
     * @param count the number of unique full names to generate
     * @return a Set of unique full names
     * @throws UniqueNameGenerationException if count > max possible unique combinations
     */
    public Set<String> generate(Long count) {
        long maxUniqCount = (long) Math.pow(nameCatalogs.size(), 3);
        Set<String> randomFullNames;

        if (maxUniqCount < count)
            throw new UniqueNameGenerationException(String.format("Can't generate %s uniq names! max uniq name's count is: %s", count, maxUniqCount));

        if (count <= MAX_SEQUENTIAL_COUNT) {
            randomFullNames = new HashSet<>();
            this.sequentialGenerate(randomFullNames, count);
        } else {
            randomFullNames = ConcurrentHashMap.newKeySet();
            this.parallelGenerate(randomFullNames, count);
        }

        return randomFullNames;
    }

    /**
     * Generates full names sequentially
     *
     * @param randomFullNames the set to store generated names
     * @param count the target number of names
     */
    private void sequentialGenerate(Set<String> randomFullNames, Long count) {
        Random random = new Random();

        while (randomFullNames.size() < count) {
            String randFirstName = nameCatalogs.get(random.nextInt(nameCatalogCount)).getFirstName();
            String randMidName = nameCatalogs.get(random.nextInt(nameCatalogCount)).getFirstName();
            String randLastName = nameCatalogs.get(random.nextInt(nameCatalogCount)).getLastName();
            randomFullNames.add(String.format("%s %s %s", randFirstName, randMidName, randLastName));
        }
    }


    /**
     * Generates full names in parallel
     *
     * @param randomFullNames the set to store generated names
     * @param count the target number of names
     */
    private void parallelGenerate(Set<String> randomFullNames, Long count) {
        LongStream.range(0, count)
                .parallel()
                .forEach(i -> {
                    String randFirstName = nameCatalogs.get(ThreadLocalRandom.current().nextInt(nameCatalogCount)).getFirstName();
                    String randMidName = nameCatalogs.get(ThreadLocalRandom.current().nextInt(nameCatalogCount)).getFirstName();
                    String randLastName = nameCatalogs.get(ThreadLocalRandom.current().nextInt(nameCatalogCount)).getLastName();
                    randomFullNames.add(String.format("%s %s %s", randFirstName, randMidName, randLastName));
                });

        if(randomFullNames.size() < count)
            this.sequentialGenerate(randomFullNames, count);
    }

}
