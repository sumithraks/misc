import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Stub: replace the bodies. Do not change the signatures.
public class BankingSystemImpl {

    public boolean createAccount(int timestamp, String accountId) {
        return false;
    }

    public Optional<Integer> deposit(int timestamp, String accountId, int amount) {
        return Optional.empty();
    }

    public Optional<Integer> transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount) {
        return Optional.empty();
    }

    public List<String> topSpenders(int timestamp, int n) {
        return new ArrayList<>();
    }

    public Optional<String> pay(int timestamp, String accountId, int amount) {
        return Optional.empty();
    }

    public Optional<String> getPaymentStatus(int timestamp, String accountId, String payment) {
        return Optional.empty();
    }

    public boolean mergeAccounts(int timestamp, String accountId1, String accountId2) {
        return false;
    }

    public Optional<Integer> getBalance(int timestamp, String accountId, int timeAt) {
        return Optional.empty();
    }
}
