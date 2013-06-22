(ns mangopay.core
  (:require [mangopay.auth :as auth]
            [clj-http.client :as client]
            [cheshire.core :as json]))


(defn timestamp []
  (.getTime (new java.util.Date)))


(defn api-call-url [url-path]
  (str "http://api-preprod.leetchi.com" url-path))

(let [data {"FirstName" "Mark",
            "LastName" "Zuckerberg",
            "Email" "mark@leetchi.com",
            "Nationality" "FR",
            "Birthday" 1300186358,
            "PersonType" "NATURAL_PERSON",
            "Tag" "custom information from the app"}
      json (json/generate-string data)
      ts (timestamp)
      url-path (auth/api-call-path "communist" ts)
      url (api-call-url url-path)
      signature (auth/signature "/Users/podviaznikov/.ssh/mangopay_rsa" "POST" url-path json)
      resp  (client/post url
               {:body json
                :content-type :json
                :headers {
                          "X-Leetchi-Signature" signature}})]
 (json/parse-string (:body resp)))







