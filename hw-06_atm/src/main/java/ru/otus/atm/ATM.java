package ru.otus.atm;

import java.util.List;
import ru.otus.atm.banknotes.Banknote;

public interface ATM {

    void depositMoney(List<Banknote> banknotes);

    List<Banknote> withdrawMoney(int amount);

    int getCurrentAmountOfMoney();

    List<Integer> getAvailableDenominations();

}
