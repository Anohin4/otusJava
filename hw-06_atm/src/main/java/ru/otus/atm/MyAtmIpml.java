package ru.otus.atm;

import java.util.List;
import ru.otus.atm.banknotes.Banknote;
import ru.otus.atm.service.DepositService;
import ru.otus.atm.service.InformationService;
import ru.otus.atm.service.WithdrawService;

public class MyAtmIpml implements ATM {

    private final WithdrawService withdrawService;
    private final DepositService depositService;
    private final InformationService informationService;

    public MyAtmIpml(WithdrawService withdrawService, DepositService depositService,
            InformationService informationService) {
        this.withdrawService = withdrawService;
        this.depositService = depositService;
        this.informationService = informationService;
    }


    @Override
    public void depositMoney(List<Banknote> banknotes) {
        depositService.depositBanknotes(banknotes);

    }

    @Override
    public List<Banknote> withdrawMoney(int amount) {
        return withdrawService.withdrawBanknotes(amount);
    }

    @Override
    public int getCurrentAmountOfMoney() {
        return informationService.getCurrentAmountOfMoney();
    }

    @Override
    public List<Integer> getAvailableDenominations() {
        return informationService.getAvailableDenominations();
    }
}
