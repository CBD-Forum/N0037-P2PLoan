pragma solidity ^0.4.9;


contract LoanInfo {

    struct LoanAmount {
    uint256 amount;
    uint256 id;
    }


    mapping (uint256 => LoanAmount) public loanAmounts;


    function setAmount(uint256 key, uint256 _amount, uint256 _id) {
        loanAmounts[key] = LoanAmount(_amount, _id);
    }

    function getAmount(uint256 key) constant returns (uint256 _amount) {
        _amount = loanAmounts[key].amount;
    }


}