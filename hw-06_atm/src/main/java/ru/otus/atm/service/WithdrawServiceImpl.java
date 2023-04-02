package ru.otus.atm.service;

import java.util.ArrayList;
import java.util.List;
import ru.otus.atm.banknotes.Banknote;
import ru.otus.atm.exceptions.DoesntHaveRequiredBanknotesException;
import ru.otus.atm.exceptions.DoesntHaveSoMuchMoneyException;
import ru.otus.atm.storage.MoneyStorage;

public class WithdrawServiceImpl implements WithdrawService {

    private final MoneyStorage moneyStorage;

    public WithdrawServiceImpl(MoneyStorage moneyStorage) {
        this.moneyStorage = moneyStorage;
    }

    @Override
    public List<Banknote> withdrawBanknotes(int amount) {
        if (moneyStorage.getCurrentAmountOfMoney() < amount) {
            throw new DoesntHaveSoMuchMoneyException("ATM doesn't have so much money");
        }
        return getMoney(amount);
    }

    private List<Banknote> getMoney(int amount) {
        List<Banknote> result = new ArrayList<>();
        int currentSumm = 0;
        //запрашиваем доступные номиналы в нисходящем порядке
        List<Integer> listOfAvailableDenominations = moneyStorage.getSortedListOfAvailableDenominationsDesc();
        for (Integer denomination : listOfAvailableDenominations) {
            if (denomination > amount) {
                continue;
            }
            //получаем банкноты определенного номинала из хранилища
            int amountOfBanknotes = (amount - currentSumm) / denomination;
            List<Banknote> banknotes = moneyStorage.withdrawMoney(denomination, amountOfBanknotes);
            // увеличиваем набранную сумму денег
            currentSumm += banknotes.size() * denomination;
            result.addAll(banknotes);

            if (currentSumm >= amount) {
                break;
            }
        }
        //может сложиться ситуация, когда мы просто не набрали деньги из-за номинала
        // в этом случае кидаем ошибку
        if (currentSumm != amount) {
            throw new DoesntHaveRequiredBanknotesException("ATM doesn't have so required banknotes.");
        }

        return result;
    }
}
