package ru.otus.atm.storage;

import java.util.List;
import ru.otus.atm.banknotes.Banknote;

public interface MoneyStorage {

    List<Banknote> withdrawMoney(int denominationValue, int amountOfBanknotes);

    void depositMoney(int denominationValue, List<Banknote> banknotes);

    List<Integer> getSortedListOfAvailableDenominationsDesc();

    int getCurrentAmountOfMoney();
}
