package ru.otus.atm.service;

import java.util.List;
import ru.otus.atm.banknotes.Banknote;

public interface DepositService {

    void depositBanknotes(List<Banknote> banknotes);
}
