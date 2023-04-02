package ru.otus.storage;

import java.util.List;
import ru.otus.banknotes.Banknote;

public interface MoneyStorage {

    List<Banknote> withdrawMoney(int denominationValue, int amountOfBanknotes);

    void depositMoney(int denominationValue, List<Banknote> banknotes);

    List<Integer> getSortedListOfAvailableDenominationsDesc();

    boolean hasThatAmountOfMoney(int summ);
}
