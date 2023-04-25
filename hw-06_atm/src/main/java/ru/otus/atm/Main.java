package ru.otus.atm;

import java.util.List;
import java.util.UUID;
import ru.otus.atm.banknotes.Banknote;
import ru.otus.atm.banknotes.DefaultBanknote;
import ru.otus.atm.service.DepositService;
import ru.otus.atm.service.DepositServiceImpl;
import ru.otus.atm.service.InformationService;
import ru.otus.atm.service.InformationServiceImpl;
import ru.otus.atm.service.WithdrawService;
import ru.otus.atm.service.WithdrawServiceImpl;
import ru.otus.atm.storage.MoneyStorageImpl;

public class Main {

    public static void main(String[] args) {
        MoneyStorageImpl moneyStorage = new MoneyStorageImpl();
        WithdrawService withdrawService = new WithdrawServiceImpl(moneyStorage);
        DepositService depositService = new DepositServiceImpl(moneyStorage);
        InformationService informationService = new InformationServiceImpl(moneyStorage);
        MyAtmIpml myAtmIpml = new MyAtmIpml(withdrawService, depositService, informationService);

        myAtmIpml.depositMoney(
                List.of(new DefaultBanknote(UUID.randomUUID(), 100),
                        new DefaultBanknote(UUID.randomUUID(), 1000),
                        new DefaultBanknote(UUID.randomUUID(), 1000),
                        new DefaultBanknote(UUID.randomUUID(), 1000),
                        new DefaultBanknote(UUID.randomUUID(), 1000),
                        new DefaultBanknote(UUID.randomUUID(), 5000)));

        //случай когда все ок
        //List<Banknote> banknotes = myAtmIpml.withdrawMoney(7100);
        //случай когда сумма слишком большая
        List<Banknote> banknotes = myAtmIpml.withdrawMoney(19000);
        // случай когда таких банкнот в банкомате нет
        //List<Banknote> banknotes = myAtmIpml.withdrawMoney(500);
        System.out.println(banknotes);
    }
}