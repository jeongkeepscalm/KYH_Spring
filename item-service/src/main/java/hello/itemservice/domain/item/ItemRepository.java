package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // ComponentScan 대상
public class ItemRepository {

    // ConcurrentHashMap, AtomicLong : 실무에서는 멀티쓰레드 처리를 위해
    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long squence = 0L; // static

    public Item save(Item item){
        item.setId(++squence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long id, Item updateParam) {
        Item findItem = findById(id);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }





}
