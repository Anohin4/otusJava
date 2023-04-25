package ru.otus.atm.service;

import java.util.List;

public interface InformationService {

    int getCurrentAmountOfMoney();

    List<Integer> getAvailableDenominations();
}
