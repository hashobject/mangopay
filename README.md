# mangopay

A Clojure library for interaction with [MangoPay REST API](http://www.mangopay.com/overview/).


## Install

```shell
[mangopay "0.1.0-SNAPSHOT"]
```

## Usage

Below is an example how create, fetch and modify user.

All other intereactions with MangoPay can be done in absolutely the same way.

MangoPay API is very consistent. You need just change 1 parameter of each method. So e.x.
instead of `users` just use `wallets` or `withdrawals`.

For details about what properties are required for each entity creation please visit
an official API [documentation](http://www.mangopay.com/api-references/).


```shell
user=> (use 'mangopay.core)
null
user=> (create "users"
        {"FirstName" "Mark",
         "LastName" "Zuckerberg",
         "Email" "mark@leetchi.com",
         "Nationality" "FR",
         "Birthday" 1300186358,
         "PersonType" :NATURAL_PERSON,
         "Tag" "custom information from the app"}
        {:partner-id "communist"
         :host "http://api-preprod.leetchi.com"
         :rsa-key-path "/Users/podviaznikov/.ssh/mangopay_rsa"})
{"LastName" "Zuckerberg", "IP" nil, "CreationDate" 1371931163, "IsStrongAuthenticated" false, "PersonType" "NATURAL_PERSON", "PersonalWalletAmount" 0, "UpdateDate" 1371931163, "CanRegisterMeanOfPayment" false, "Tag" "custom information from the app", "ID" 337252, "FirstName" "Mark", "Password" "XGB5hY9PAWnWh3ZLi6OwKY0t1Fg6bM", "Nationality" "FR", "Email" "mark@leetchi.com", "HasRegisteredMeansOfPayment" false, "Birthday" 1300186358}

user=> (fetch "users" 337243 {:partner-id "communist"
         :host "http://api-preprod.leetchi.com"
         :rsa-key-path "/Users/podviaznikov/.ssh/mangopay_rsa"})
{"LastName" "Zuckerberg", "IP" nil, "CreationDate" 1371931163, "IsStrongAuthenticated" false, "PersonType" "NATURAL_PERSON", "PersonalWalletAmount" 0, "UpdateDate" 1371931163, "CanRegisterMeanOfPayment" false, "Tag" "custom information from the app", "ID" 337252, "FirstName" "Mark", "Password" "XGB5hY9PAWnWh3ZLi6OwKY0t1Fg6bM", "Nationality" "FR", "Email" "mark@leetchi.com", "HasRegisteredMeansOfPayment" false, "Birthday" 1300186358}

user=> (modify "users"
         337243
        {"FirstName" "Markus"
         "Tag" "custom information from the app"}
        {:partner-id "communist"
         :host "http://api-preprod.leetchi.com"
         :rsa-key-path "/Users/podviaznikov/.ssh/mangopay_rsa"})
{"LastName" "Zuckerberg", "IP" nil, "CreationDate" 1371930963, "IsStrongAuthenticated" false, "PersonType" "NATURAL_PERSON", "PersonalWalletAmount" 0, "UpdateDate" 1371931136, "CanRegisterMeanOfPayment" false, "Tag" "custom information from the app", "ID" 337243, "FirstName" "Markus", "Password" "bjA0SDwRphhEinYPv8FFLx6qEP5xPU", "Nationality" "FR", "Email" "mark@leetchi.com", "HasRegisteredMeansOfPayment" false, "Birthday" 1300186358}
```

## Docs


There are 3 methods:

  * (create [route input options])
  * (fetch [route id options])
  * (modify [route id input options])

Options has 3 properties:

  * :partner-id - your id youshould receive from MangoPay on registration
  * :host - API endpoint. http://api-preprod.leetchi.com for testing and https://api.leetchi.com/v1/partner/ for production
  * :rsa-key-path - path to your private RSA key. Correspoding public key should be submitted to MangoPay on registration.


## Contributions

We love contributions. Please submit your pull requests.


## License

Copyright © 2013 HashObject Ltd (team@hashobject.com).

The use and distribution terms for this software are covered by the Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0) which can be found in the file epl-v10.html at the root of this distribution.

By using this software in any fashion, you are agreeing to be bound by the terms of this license.

You must not remove this notice, or any other, from this software.
