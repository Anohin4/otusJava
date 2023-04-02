package ru.otus.atm.banknotes;

import java.util.UUID;

public record DefaultBanknote(UUID id, int denomination) implements Banknote {

    @Override
    public int getDenomination() {
        return denomination;
    }


}
