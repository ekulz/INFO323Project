package rest.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TransactionList implements Serializable {
    
    private Collection<Transaction> transactions = new HashSet<>();
    
    public TransactionList(Collection newTransactions) {
        this.transactions.addAll(newTransactions);
    }
    
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }
    
    public Collection<Transaction> getTransactions() {
        return transactions;
    }
    
    
    public void setTransactions(List<Transaction> t) {
        this.transactions = t;
    }

    @Override
    public String toString() {
        return "TransactionList{" + "transactions=" + transactions + '}';
    }
}
