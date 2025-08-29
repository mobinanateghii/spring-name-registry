package ir.example.demo.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {

    /**
     * Prefer to not use chunkCount because maybe we will have remaining data and result count will be => chunkCount + 1 chunks.
     *
     * @param data      the Collection to be divided into chunks
     * @param chunkSize the maximum number of elements in each chunk
     * @param <T>       the type of elements in Collection
     * @return the chunk of original list
     */
    public static <T> List<List<T>> doChunk(Collection<T> data, int chunkSize) {
        if (chunkSize < 1)
            throw new IllegalArgumentException("chunkSize must be at least 1");

        List<T> dataList = new ArrayList<>(data);

        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i += chunkSize) {
            int toIndex = Math.min(i + chunkSize, dataList.size());
            chunks.add(dataList.subList(i, toIndex));
        }
        return chunks;
    }
}
