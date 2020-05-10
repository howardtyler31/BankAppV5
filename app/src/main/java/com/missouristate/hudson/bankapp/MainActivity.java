package com.missouristate.hudson.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button btn_Input;
    EditText et_account_num;
    EditText et_Deposit;
    EditText et_Withdraw;
    TextView tv_AccountNumber;
    TextView tv_CustomerName;
    TextView tv_AccountBalance;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Input = findViewById(R.id.btn_Input);
        et_account_num = findViewById(R.id.et_account_num);
        et_Deposit = findViewById(R.id.et_Deposit);
        et_Withdraw = findViewById(R.id.et_Withdraw);
        tv_AccountNumber = findViewById(R.id.tv_AccountNumber);
        tv_CustomerName = findViewById(R.id.tv_CustomerName);
        tv_AccountBalance = findViewById(R.id.tv_AccountBalance);
        dbManager = new DatabaseManager(MainActivity.this);

        dbManager.prePopulateAccounts(100100, "Alex McCullah", 2500.00);
        dbManager.prePopulateAccounts(100200, "Bridget Hudson", 500.00);
        dbManager.prePopulateAccounts(100300, "Greg Stratton", 12.00);
        dbManager.prePopulateAccounts(100400, "Thomas", 5600.00);
        dbManager.prePopulateAccounts(100500, "Tyler Howard", 10000.00);
        dbManager.prePopulateAccounts(100600, "James Bateman", 85000.00);

    }

    public void submitChanges(View view) {

        et_account_num = findViewById(R.id.et_account_num);
        et_Deposit = findViewById(R.id.et_Deposit);
        et_Withdraw = findViewById(R.id.et_Withdraw);
        tv_AccountNumber = findViewById(R.id.tv_AccountNumber);
        tv_CustomerName = findViewById(R.id.tv_CustomerName);
        tv_AccountBalance = findViewById(R.id.tv_AccountBalance);

        String stringAccountNum = et_account_num.getText().toString();
        int accountNo = Integer.parseInt(stringAccountNum);
        String stringDeposit = et_Deposit.getText().toString();
        String stringWithdraw = et_Withdraw.getText().toString();

        String dbName;
        double dbBalance;

        dbManager = new DatabaseManager(MainActivity.this);
        BankAccount oneAccount = dbManager.selectByAcctNo(accountNo);
        dbBalance = oneAccount.getBalance();
        dbName = oneAccount.getName();

        if(et_Deposit.length()!=0){
            double deposit = Double.parseDouble(stringDeposit);
            dbBalance = dbBalance + deposit;
            dbManager.updateBalance(accountNo, dbBalance);

        }
        if (et_Withdraw.length()!=0){
            double withdraw = Double.parseDouble(stringWithdraw);
            dbBalance = dbBalance - withdraw;
            dbManager.updateBalance(accountNo, dbBalance);
        }

        tv_AccountNumber.setText(String.valueOf(accountNo));
        tv_CustomerName.setText(dbName);
        tv_AccountBalance.setText(String.valueOf(dbBalance));

    }
}