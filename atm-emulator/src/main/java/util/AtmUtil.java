package util;

import domain.Atm;
import domain.Denomination;

import java.util.Map;

import static domain.Denomination.*;

public class AtmUtil {
    public static void loadMoneyToAtm(Atm atm) {
        atm.deposit(FIVE, 20);
        atm.deposit(TEN, 50);
        atm.deposit(FIFTY, 20);
        atm.deposit(HUNDRED, 30);
        atm.deposit(TWO_HUNDRED, 20);
        atm.deposit(FIVE_HUNDRED, 20);
        atm.deposit(THOUSAND, 10);
        atm.deposit(TWU_THOUSAND, 5);
        atm.deposit(FIVE_THOUSAND, 5);
    }

    public static void tryToWithdrawalMoney(Atm atm, int amount) {
        try {
            System.out.println("\nЗапрос на выдачу: " + amount);
            Map<Denomination, Integer> withdrawn = atm.withdraw(amount);
            printRemovedMoney(withdrawn);
            System.out.println("\nОстаток в ATM: " + atm.getBalance());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void printRemovedMoney(Map<Denomination, Integer> withdrawn) {
        System.out.println("Выдано:");
        withdrawn.forEach((denomination, count) ->
                System.out.println(
                        denomination.getValue()
                                + " x "
                                + count));
    }

}
