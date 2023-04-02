package ru.otus;

import java.util.List;
import ru.otus.banknotes.Banknote;
import ru.otus.service.DepositService;
import ru.otus.service.WithdrawServiceImpl;

public class MyAtmIpml implements ATM {

    private final WithdrawServiceImpl withdrawService;
    private final DepositService depositService;

    public MyAtmIpml(WithdrawServiceImpl withdrawService, DepositService depositService) {
        this.withdrawService = withdrawService;
        this.depositService = depositService;
    }


    @Override
    public void depositMoney(List<Banknote> banknotes) {
        depositService.depositBanknotes(banknotes);

    }

    @Override
    public List<Banknote> withdrawMoney(int amount) {
        return withdrawService.withdrawBanknotes(amount);
    }
}
