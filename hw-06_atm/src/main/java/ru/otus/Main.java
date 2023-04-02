package ru.otus;

import java.util.List;
import java.util.UUID;
import ru.otus.banknotes.Banknote;
import ru.otus.banknotes.DefaultBanknote;
import ru.otus.service.DepositServiceImpl;
import ru.otus.service.WithdrawServiceImpl;
import ru.otus.storage.MoneyStorageImpl;

public class Main {

    public static void main(String[] args) {
        MoneyStorageImpl moneyStorage = new MoneyStorageImpl();
        WithdrawServiceImpl withdrawService = new WithdrawServiceImpl(moneyStorage);
        DepositServiceImpl depositService = new DepositServiceImpl(moneyStorage);
        MyAtmIpml myAtmIpml = new MyAtmIpml(withdrawService, depositService);

        myAtmIpml.depositMoney(
                List.of(new DefaultBanknote(UUID.randomUUID(), 100),
                        new DefaultBanknote(UUID.randomUUID(), 1000),
                        new DefaultBanknote(UUID.randomUUID(), 1000),
                        new DefaultBanknote(UUID.randomUUID(), 1000),
                        new DefaultBanknote(UUID.randomUUID(), 1000),
                        new DefaultBanknote(UUID.randomUUID(), 5000)));

        List<Banknote> banknotes = myAtmIpml.withdrawMoney(7100);
        System.out.println(banknotes);
    }
}