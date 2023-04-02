package ru.otus;

import java.util.List;
import ru.otus.banknotes.Banknote;

public interface ATM {

    void depositMoney(List<Banknote> banknotes);
    List<Banknote> withdrawMoney(int amount);

}
