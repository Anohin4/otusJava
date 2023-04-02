package ru.otus.atm.storage;

import java.util.List;
import ru.otus.atm.banknotes.Banknote;

public interface ISpecificBanknoteStorage {

    void addBanknotes(List<Banknote> banknotes);

    List<Banknote> withdrawBanknotes(int amount);

    boolean isEmpty();
}
