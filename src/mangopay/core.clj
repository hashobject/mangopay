(ns mangopay.core
  (:require [mangopay.auth :as auth]
            [clj-http.client :as client]
            [cheshire.core :as json]))


(defn timestamp []
  (.getTime (new java.util.Date)))


(defn api-call-url [host url-path]
  (str host url-path))



(defn create [route input options]
  (let [json (json/generate-string input)
        ts (timestamp)
        url-path (auth/api-call-path (:partner-id options) route ts)
        url (api-call-url (:host options) url-path)
        signature (auth/signature (:rsa-key-path options) "POST" url-path json)
        resp (client/post url
               {:body json
                :content-type :json
                :headers {
                          "X-Leetchi-Signature" signature}})
        output (json/parse-string (:body resp))]
    output))


(create "users"
        {"FirstName" "Mark",
         "LastName" "Zuckerberg",
         "Email" "mark@leetchi.com",
         "Nationality" "FR",
         "Birthday" 1300186358,
         "PersonType" "NATURAL_PERSON",
         "Tag" "custom information from the app"}
        {:partner-id "communist"
         :host "http://api-preprod.leetchi.com"
         :rsa-key-path "/Users/podviaznikov/.ssh/mangopay_rsa"})


