package ru.otus.atm.storage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ru.otus.atm.banknotes.Banknote;

public class SpecificBanknoteStorage implements ISpecificBanknoteStorage {

    private final int banknoteDenomination;
    private final Queue<Banknote> banknoteStorage;

    public SpecificBanknoteStorage(int banknoteDenomination) {
        this.banknoteDenomination = banknoteDenomination;
        this.banknoteStorage = new LinkedList<>();
    }

    @Override
    public void addBanknotes(List<Banknote> banknotes) {
        banknoteStorage.addAll(banknotes);
    }

    @Override
    public List<Banknote> withdrawBanknotes(int amount) {
        List<Banknote> result = new ArrayList<>();
        int i = 0;
        while (i < amount
                && !banknoteStorage.isEmpty()) {
            result.add(banknoteStorage.poll());
            i++;
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return banknoteStorage.isEmpty();
    }
}
