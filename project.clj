(defproject mangopay "0.1.0-SNAPSHOT"
  :description "A Clojure library for interaction with MangoPay REST API."
  :signing {:gpg-key "Hashobject Ltd <team@hashobject.com>"}
  :url "https://github.com/hashobject/mangopay"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.codec "0.1.0"]
                 [rsa-signer "0.1.0"]
                 [clj-http "0.7.2"]
                 [cheshire "5.0.1"]])
