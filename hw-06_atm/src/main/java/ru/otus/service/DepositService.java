package ru.otus.service;

import java.util.List;
import ru.otus.banknotes.Banknote;

public interface DepositService {

    void depositBanknotes(List<Banknote> banknotes);
}
