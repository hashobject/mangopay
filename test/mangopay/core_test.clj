(ns mangopay.core-test
  (:use clojure.test
        mangopay.core))


(def api-options
  {:partner-id "communist"
   :host "http://api-preprod.leetchi.com"
   :rsa-key-path "/Users/podviaznikov/.ssh/mangopay_rsa"})

(deftest user-test
  (testing "user"
    (let [create-resp (create "users"
                              {"FirstName" "Mark",
                               "LastName" "Zuckerberg",
                               "Email" "mark@leetchi.com",
                               "Nationality" "FR",
                               "Birthday" 1300186358,
                               "PersonType" :NATURAL_PERSON,
                               "Tag" "INTERNAL_ID"}
                              api-options)]
      (testing "create"
        (is (= "Mark" (get create-resp "FirstName")))
        (is (= "Zuckerberg" (get create-resp "LastName")))
        (is (= "mark@leetchi.com" (get create-resp "Email")))
        (is (= "FR" (get create-resp "Nationality")))
        (is (= 1300186358 (get create-resp "Birthday")))
        (is (= "NATURAL_PERSON" (get create-resp "PersonType")))
        (is (= "INTERNAL_ID" (get create-resp "Tag")))
        ; extra fields returned from MangoPay API
        (is (< 0 (get create-resp "ID")))
        (is (instance? Integer (get create-resp "ID")))
        (is (= false (get create-resp "HasRegisteredMeansOfPayment")))
        (is (= false (get create-resp "IsStrongAuthenticated")))
        (is (= false (get create-resp "CanRegisterMeanOfPayment")))
        (is (= nil (get create-resp "IP")))
        (is (= 0 (get create-resp "PersonalWalletAmount")))
        (is (< 0 (get create-resp "CreationDate")))
        (is (< 0 (get create-resp "UpdateDate")))
        (is (< 0 (count (get create-resp "Password")))))
      (testing "fetch"
        (let [fetch-resp (fetch "users"
                                 (get create-resp "ID")
                                 api-options)]
        (is (= "Mark" (get fetch-resp "FirstName")))
        (is (= "Zuckerberg" (get fetch-resp "LastName")))
        (is (= "mark@leetchi.com" (get fetch-resp "Email")))
        (is (= "FR" (get fetch-resp "Nationality")))
        (is (= 1300186358 (get fetch-resp "Birthday")))
        (is (= "NATURAL_PERSON" (get fetch-resp "PersonType")))
        (is (= "INTERNAL_ID" (get fetch-resp "Tag")))
        ; extra fields returned from MangoPay API
        (is (< 0 (get fetch-resp "ID")))
        (is (instance? Integer (get fetch-resp "ID")))
        (is (= false (get fetch-resp "HasRegisteredMeansOfPayment")))
        (is (= false (get fetch-resp "IsStrongAuthenticated")))
        (is (= false (get fetch-resp "CanRegisterMeanOfPayment")))
        (is (= nil (get fetch-resp "IP")))
        (is (= 0 (get fetch-resp "PersonalWalletAmount")))
        (is (< 0 (get fetch-resp "CreationDate")))
        (is (< 0 (get fetch-resp "UpdateDate")))
        (is (< 0 (count (get fetch-resp "Password"))))))

      (testing "modify"
        (let [modify-resp (modify "users"
                                 (get create-resp "ID")
                                 {"FirstName" "Peter"}
                                 api-options)]
        (is (= "Peter" (get modify-resp "FirstName")))
        (is (= "Zuckerberg" (get modify-resp "LastName")))
        (is (= "mark@leetchi.com" (get modify-resp "Email")))
        (is (= "FR" (get modify-resp "Nationality")))
        (is (= 1300186358 (get modify-resp "Birthday")))
        (is (= "NATURAL_PERSON" (get modify-resp "PersonType")))
        (is (= "INTERNAL_ID" (get modify-resp "Tag")))
        ; extra fields returned from MangoPay API
        (is (< 0 (get modify-resp "ID")))
        (is (instance? Integer (get modify-resp "ID")))
        (is (= false (get modify-resp "HasRegisteredMeansOfPayment")))
        (is (= false (get modify-resp "IsStrongAuthenticated")))
        (is (= false (get modify-resp "CanRegisterMeanOfPayment")))
        (is (= nil (get modify-resp "IP")))
        (is (= 0 (get modify-resp "PersonalWalletAmount")))
        (is (< 0 (get modify-resp "CreationDate")))
        (is (< 0 (get modify-resp "UpdateDate")))
        (is (< (get create-resp "UpdateDate") (get modify-resp "UpdateDate")))
        (is (< 0 (count (get modify-resp "Password"))))))

      (testing "fetch modified"
        (let [fetch-resp (fetch "users"
                                 (get create-resp "ID")
                                 api-options)]
        (is (= "Peter" (get fetch-resp "FirstName")))
        (is (= "Zuckerberg" (get fetch-resp "LastName")))
        (is (= "mark@leetchi.com" (get fetch-resp "Email")))
        (is (= "FR" (get fetch-resp "Nationality")))
        (is (= 1300186358 (get fetch-resp "Birthday")))
        (is (= "NATURAL_PERSON" (get fetch-resp "PersonType")))
        (is (= "INTERNAL_ID" (get fetch-resp "Tag")))
        ; extra fields returned from MangoPay API
        (is (< 0 (get fetch-resp "ID")))
        (is (instance? Integer (get fetch-resp "ID")))
        (is (= false (get fetch-resp "HasRegisteredMeansOfPayment")))
        (is (= false (get fetch-resp "IsStrongAuthenticated")))
        (is (= false (get fetch-resp "CanRegisterMeanOfPayment")))
        (is (= nil (get fetch-resp "IP")))
        (is (= 0 (get fetch-resp "PersonalWalletAmount")))
        (is (< 0 (get fetch-resp "CreationDate")))
        (is (< 0 (get fetch-resp "UpdateDate")))
        (is (< (get create-resp "UpdateDate") (get fetch-resp "UpdateDate")))
        (is (< 0 (count (get fetch-resp "Password"))))))

      )

    ))
