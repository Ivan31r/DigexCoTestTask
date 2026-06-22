package domain;

import exception.WithdrawalException;

import java.util.*;

public class Atm {

    private final Map<Denomination, CashCell> cells = new EnumMap<>(Denomination.class);

    public Atm() {
        for (Denomination denomination : Denomination.values()) {
            cells.put(denomination, new CashCell(denomination));
        }
    }

    public void deposit(Denomination denomination, int count) {
        cells.get(denomination).deposit(count);
    }

    public int getBalance() {
        return cells.values()
                .stream()
                .mapToInt(CashCell::getBalance)
                .sum();
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        Map<Denomination, Integer> result = new EnumMap<>(Denomination.class);
        int remaining = amount;
        List<Denomination> denominations =
                Arrays.stream(Denomination.values())
                        .sorted(Comparator.comparingInt(Denomination::getValue)
                                .reversed())
                        .toList();

        for (Denomination denomination : denominations) {
            CashCell cell = cells.get(denomination);
            int needed = remaining / denomination.getValue();
            int available = cell.getCount();
            int toTake = Math.min(needed, available);

            if (toTake > 0) {
                result.put(denomination, toTake);
                remaining -= toTake * denomination.getValue();
            }
        }

        if (remaining != 0) {
            throw new WithdrawalException(
                    "В данный момент вывод " + amount + " невозможен. " +
                            "Попробуйте позднее");
        }

        result.forEach((denomination, count) ->
                cells.get(denomination).withdraw(count));

        return result;
    }
}
