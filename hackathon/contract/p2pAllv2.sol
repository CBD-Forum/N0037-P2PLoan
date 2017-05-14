pragma solidity ^0.4.11;

contract P2PLoan {

    string userChainId = "01";
    string userPrivateKey = "94d5276c8f67d3abc31f932d89d1a9518c272abe147087cd02707c000e7b7a57";
    string userPass = "bclab123";
    string userReturnFuc = "userCheck";
    string userAddress = "0x975da261b2b5deda4dee65c71ddb09617f49e001";


    string loanChainId = "10";
    string loanPrivateKey = "94d5276c8f67d3abc31f932d89d1a9518c272abe147087cd02707c000e7b7a57";
    string loanPass = "bclab123";
    string loanReturnFuc = "loanCheck";
    string loanAddress = "0x975da261b2b5deda4dee65c71ddb09617f49e001";


    enum Stages {
    New,
    UserChecked,
    Accepting,
    Paying,
    Repaying,
    Finished
    }

    Stages public stage = Stages.New;

    uint public creationTime = now;

    uint public paidTime;

    uint public repaymentTime;

    modifier atStage(Stages _stage) {
        require(stage == _stage);
        _;
    }


    function currentStage() constant returns (Stages current) {
        current = stage;
    }

    function nextStage() internal {
        stage = Stages(uint(stage) + 1);
    }

    function endStage() internal {
        stage = Stages.Finished;
    }


    modifier timedTransitions() {
        if (stage == Stages.Accepting &&
        now >= creationTime + deadline * 1 days)
        nextStage();
        if (stage == Stages.Paying &&
        now >= paidTime + duration * 1 days)
        nextStage();
        _;
    }

    // This modifier goes to the next stage
    // after the function is done.
    modifier transitionNext()
    {
        _;
        nextStage();
    }

    address public borrower;
    uint public loanGoal;
    uint public amountRaised;
    uint public amountRepay;
    uint public deadline;
    uint public duration;
    uint public interest;
    Lender[] public lenders;

    event TokenTransfer(address backer, uint amount);

    /* data structure to hold information about lenders */
    struct Lender {
    address addr;
    uint amount;
    }

    /*  at initialization, setup the owner */
    function P2PLoan(address _borrower, uint _loanGoal, uint _duration, uint _interest) {
        borrower = _borrower;
        loanGoal = _loanGoal;
        deadline = now + 15 days;
        duration = now + _duration * 1 days;
        interest = _interest;
    }

    function CrossChainUserCheckInfo() constant returns (string, string, string, string, string)
    {
        return (userChainId, userPrivateKey, userPass, userReturnFuc, userAddress);

    }


    function CrossChainLoanCheckInfo() constant returns (string, string, string, string, string)
    {
        return (loanChainId, loanPrivateKey, loanPass, loanReturnFuc, loanAddress);

    }


    function userCheck(uint _score)
    atStage(Stages.New)
    returns (bool)
    {
        if(_score > 500)
        {
            nextStage();
            return true;
        }
        else {
            endStage();
            return false;
        }
    }

    function loanCheck(uint _loanAvail)
    atStage(Stages.UserChecked)
    returns (bool)
    {
        if(_loanAvail <= loanGoal)
        {
            nextStage();
            return true;

        }
        else
        {
            endStage();
            return false;
        }

    }


    /* The function without name is the default function that is called whenever anyone sends funds to a contract */
    function pay(address sender, uint amount)
    payable
    timedTransitions
    atStage(Stages.Accepting)
    {
        lenders[lenders.length++] = Lender({addr: sender, amount: amount});
        amountRaised += amount;
        TokenTransfer(sender, amount);
        if(amountRaised >= loanGoal)
        nextStage();
    }

    /* checks if the goal or time limit has been reached and ends the campaign */
    function checkGoalReached()
    atStage(Stages.Paying)
    {
            borrower.transfer(amountRaised);
            TokenTransfer(borrower, amountRaised);
            paidTime = now;
    }

    function repay(address sender, uint amount)
    payable
    timedTransitions
    atStage(Stages.Repaying)
    {

        if(sender == borrower ) {
            amountRepay += amount;
            if(amountRepay == amountRaised * (1+interest)) {

                for (uint i = 0; i < lenders.length; ++i) {
                    uint lendAmount = lenders[i].amount* (100+interest)/100;
                    lenders[i].addr.transfer(lendAmount);
                    TokenTransfer(lenders[i].addr, lendAmount);
                }

                nextStage();
            }
        }

    }

    function clean()
    atStage(Stages.Finished)
    {

    }
}