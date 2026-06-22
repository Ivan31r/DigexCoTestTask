package domain;

public class CashCell {
    private final Denomination denomination;
    private int count;

    public CashCell(Denomination denomination) {
        this.denomination = denomination;
    }

    public void deposit(int amount) {
        this.count += amount;
    }

    public int getCount() {
        return count;
    }

    public int getBalance() {
        return denomination.getValue() * count;
    }

    public void withdraw(int amount) {
        if (amount > count) {
            throw new IllegalArgumentException("Not enough money");
        }
        count -= amount;
    }
}
