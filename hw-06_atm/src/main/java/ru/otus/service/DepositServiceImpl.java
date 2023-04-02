package ru.otus.service;

import java.util.List;
import java.util.Map.Entry;
import ru.otus.banknotes.Banknote;
import ru.otus.storage.MoneyStorage;
import ru.otus.utils.Multimap;

public class DepositServiceImpl implements DepositService {

    private final MoneyStorage moneyStorage;

    public DepositServiceImpl(MoneyStorage moneyStorage) {
        this.moneyStorage = moneyStorage;
    }

    @Override
    public void depositBanknotes(List<Banknote> banknotes) {
        Multimap<Integer, Banknote> mapOfBanknotes = new Multimap<>();
        banknotes.forEach(it -> mapOfBanknotes.put(it.getDenomination(), it));

        for (Entry<Integer, List<Banknote>> denominationEntity : mapOfBanknotes.getEntrySet()) {
            moneyStorage.depositMoney(denominationEntity.getKey(), denominationEntity.getValue());
        }
    }
}
