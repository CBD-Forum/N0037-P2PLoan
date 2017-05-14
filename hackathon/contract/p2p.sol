pragma solidity ^0.4.9;


contract p2p {


    struct Lender {
        bool confirmed;
        uint256 money;
        address delegate;
    }

    mapping(address => Lender) lenders;

    address borrower;

    uint8 length;

    uint8 interest;

    address system = 0x975da261b2b5deda4dee65c71ddb09617f49e001;

    uint256 totalMoney;

    uint256 leftMoney;

    bool projectAvail;


        string chainUserId;
        string txUser;
        string methodUser;
        bool syncUser = false;


        string chainLoanId;
        string txLoan;
        string methodLoan;
        bool syncLoan = false;





    function p2p(uint _totalMoney, uint8 _length, uint8 _interest,string _chainUserId,  string _chainLoanId, string _txUser, string _txLoan) {
        borrower = msg.sender;
        length = _length;
        totalMoney = _totalMoney;
        leftMoney = _totalMoney;
        projectAvail = false;
        interest = _interest;

        chainUserId = _chainUserId;
        chainLoanId = _chainLoanId;
        txUser = _txUser;
        txLoan = _txLoan;

    }

    function openProject() {
        if(msg.sender != system ) return;
        projectAvail = true;
    }

    function lend(uint256 _money) {
        Lender lender = lenders[msg.sender];
        if(lender.confirmed || _money >= leftMoney || _money <= 0 || projectAvail == false) return;
        lender.money = _money;
        lender.confirmed = true;
        leftMoney -=  _money;
    }

    function availMoney() constant returns (uint256 _leftMoney) {
       _leftMoney = leftMoney;
    }
}
