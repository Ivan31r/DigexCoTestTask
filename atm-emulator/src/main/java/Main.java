import domain.Atm;

import static util.AtmUtil.loadMoneyToAtm;
import static util.AtmUtil.tryToWithdrawalMoney;

public class Main {
    public static void main(String[] args) {
        Atm atm = new Atm();

        loadMoneyToAtm(atm);
        System.out.println("Баланс: " + atm.getBalance());

        int amount = 2860;
        tryToWithdrawalMoney(atm, amount);

        amount = 12345;
        tryToWithdrawalMoney(atm, amount);

        amount = 45400;
        tryToWithdrawalMoney(atm, amount);

        amount = 3000;
        tryToWithdrawalMoney(atm, amount);
    }


}
