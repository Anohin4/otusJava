package ru.otus.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.banknotes.Banknote;

public class MoneyStorageImpl implements MoneyStorage {

    private final Map<Integer, SpecificBanknoteStorage> storageMap;
    private int currentAvailableMoney;

    public MoneyStorageImpl() {
        this.storageMap = new HashMap<>();
    }

    @Override
    public List<Banknote> withdrawMoney(int denominationValue, int amountOfBanknotes) {
        SpecificBanknoteStorage specificBanknoteStorage = storageMap.get(denominationValue);
        List<Banknote> banknotes = specificBanknoteStorage.withdrawBanknotes(amountOfBanknotes);
        currentAvailableMoney -= denominationValue * banknotes.size();
        if (specificBanknoteStorage.isEmpty()) {
            storageMap.remove(denominationValue);
        }
        return banknotes;
    }

    @Override
    public void depositMoney(int denominationValue, List<Banknote> banknotes) {
        if (!storageMap.containsKey(denominationValue)) {
            storageMap.put(denominationValue, new SpecificBanknoteStorage(denominationValue));
        }
        storageMap.get(denominationValue).addBanknotes(banknotes);
        currentAvailableMoney += denominationValue * banknotes.size();
    }

    @Override
    public List<Integer> getSortedListOfAvailableDenominationsDesc() {
        List<Integer> result = new ArrayList<>(storageMap.keySet());
        Collections.sort(result);
        return result;
    }

    @Override
    public boolean hasThatAmountOfMoney(int summ) {
        return currentAvailableMoney >= summ;
    }
}
