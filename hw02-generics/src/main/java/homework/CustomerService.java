package homework;


import static java.util.Objects.isNull;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    NavigableMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return getImmutableEntry(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getImmutableEntry(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(new Customer(customer), data);
    }
    private Map.Entry<Customer, String> getImmutableEntry(Map.Entry<Customer, String> entry) {
        if (isNull(entry)) {
            return null;
        }
        return Map.entry(new Customer(entry.getKey()), entry.getValue());
    }
}
