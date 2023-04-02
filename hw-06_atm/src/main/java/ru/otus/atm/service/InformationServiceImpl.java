package ru.otus.atm.service;

import java.util.List;
import ru.otus.atm.storage.MoneyStorage;

public class InformationServiceImpl implements InformationService {

    private final MoneyStorage moneyStorage;

    public InformationServiceImpl(MoneyStorage moneyStorage) {
        this.moneyStorage = moneyStorage;
    }

    @Override
    public int getCurrentAmountOfMoney() {
        return moneyStorage.getCurrentAmountOfMoney();
    }

    @Override
    public List<Integer> getAvailableDenominations() {
        return moneyStorage.getSortedListOfAvailableDenominationsDesc();
    }

}
