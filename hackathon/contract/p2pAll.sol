pragma solidity ^0.4.11;

contract P2PLoan {

    string chainUserId;
    string txUser;
    string methodUser;
    bool syncUser = false;


    string chainLoanId;
    string txLoan;
    string methodLoan;
    bool syncLoan = false;


    struct Funder {
        address addr;
        uint amount;
    }

    struct Loan {
        address borrower;
        uint loanGoal;
        uint loanLength;
        uint loanInterest;
        uint numFunders;
        uint currentAmount;
        mapping (uint => Funder) funders;
    }

    uint numLoans = 0;
    mapping (uint => Loan) Loans;


    function ableToLoan(address borrower ) returns (bool avail) {

    }

    function checkUser(address borrower ) returns (bool avail) {

    }

    function checkCredit(address borrower ) returns (bool avail) {

    }

    function newLoan(address borrower, uint goal, uint length, uint interest) returns (uint loanID) {
        loanID = numLoans++;
        Loans[loanID] = Loan(borrower, goal, length, interest,0,0);
    }

    function lend(uint loanID, address borrower, uint _amount) {
        Loan loan = Loans[loanID];
        loan.funders[loan.numFunders++] = Funder({addr: borrower, amount: _amount});
        loan.currentAmount += _amount;
    }

    function checkLoanLeft(uint loanID) returns (uint availAmount) {
        Loan loan = Loans[loanID];
        if (loan.currentAmount < loan.loanGoal)
            return loan.loanGoal - loan.currentAmount;
        else
            return 0;
    }
}