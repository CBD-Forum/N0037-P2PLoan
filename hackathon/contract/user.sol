pragma solidity ^0.4.9;


contract user {

    struct UserCredit {
    uint256 credit;
    uint256 id;
    }


    mapping (uint256 => UserCredit) public userCredits;


    function setCredit(uint256 key, uint256 _credit, uint256 _id) {
        userCredits[key] = UserCredit(_credit,_id);
    }

    function getCredit(uint256 key) constant returns (uint256 _credit) {
        _credit =  userCredits[key].credit;
    }


}