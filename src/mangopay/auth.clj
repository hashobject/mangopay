(ns mangopay.auth
  (:require [clojure.data.codec.base64 :as b64]
            [clj-http.client :as client]
            [rsa-signer.core :as signer]
            [cheshire.core :as json]))



(defn timestamp []
  (.getTime (new java.util.Date)))


(defn api-call-path [partner-id ts]
  (str "/v1/partner/"
       partner-id
       "/users/?ts="
       ts))


(defn api-call-url [partnet-id ts]
  (str "http://api-preprod.leetchi.com" (api-call-path partnet-id ts)))

(defn url [method partner-id ts data]
  (str method
       "|"
       (api-call-path partner-id ts)
       "|"
       data
       "|"))


(defn sign-url [method partner-id ts data]
  (signer/sign (url "POST" "communist" ts data) "/Users/podviaznikov/.ssh/mangopay_rsa" "SHA1withRSA"))


(defn signature [method partner-id ts data]
  (String. (clojure.data.codec.base64/encode (sign-url method partner-id ts data)) "UTF-8"))


(let [data {"FirstName" "Mark",
            "LastName" "Zuckerberg",
            "Email" "mark@leetchi.com",
            "Nationality" "FR",
            "Birthday" 1300186358,
            "PersonType" "NATURAL_PERSON",
            "Tag" "custom information from the app"}
      json (json/generate-string data)
      ts (timestamp)
      url (api-call-url "communist" ts)
      signature (signature "POST" "communist" ts json)
      resp  (client/post url
               {:body json
                :content-type :json
                :headers {
                          "X-Leetchi-Signature" signature}})]
 (json/parse-string (:body resp)))








