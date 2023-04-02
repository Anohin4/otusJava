package ru.otus.storage;

import java.util.List;
import ru.otus.banknotes.Banknote;

public interface ISpecificBanknoteStorage {

    void addBanknotes(List<Banknote> banknotes);

    List<Banknote> withdrawBanknotes(int amount);

    boolean isEmpty();
}
