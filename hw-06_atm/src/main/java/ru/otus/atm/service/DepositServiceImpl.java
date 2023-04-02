package ru.otus.atm.service;

import java.util.List;
import java.util.Map.Entry;
import ru.otus.atm.banknotes.Banknote;
import ru.otus.atm.storage.MoneyStorage;
import ru.otus.atm.utils.Multimap;

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
