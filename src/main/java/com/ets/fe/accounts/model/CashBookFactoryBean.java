package com.ets.fe.accounts.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author User
 */
public class CashBookFactoryBean {
 
    private static List<CashBookReport> transactions = new ArrayList<>();

    public static Collection getBeanCollection() {
        return transactions;
    }

    public static void setTransactions(List<CashBookReport> aTransactions) {
        transactions = aTransactions;
    }

}
