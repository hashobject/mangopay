(ns mangopay.core
  (:require [mangopay.auth :as auth]
            [clj-http.client :as client]
            [cheshire.core :as json]))


(defn timestamp []
  (.getTime (new java.util.Date)))


(defn api-call-url [host url-path]
  (str host url-path))

(defn post-request
  "Make a generic POST HTTP request"
  [url signature body]
  (try
    (client/post url
      {:accept :json
       :content-type :json
       :body body
       :headers {"X-Leetchi-Signature" signature}})
  (catch Exception e
     (let [exception-info (.getData e)]
     (select-keys
       (into {} (map (fn [[k v]] [(keyword k) v])
         (json/parse-string
             (get-in exception-info [:object :body]))))
             (vector :status :message :code))))))

(defn put-request
  "Make a generic PUT HTTP request"
  [url signature body]
  (try
    (client/put url
      {:accept :json
       :content-type :json
       :body body
       :headers {"X-Leetchi-Signature" signature}})
  (catch Exception e
     (let [exception-info (.getData e)]
     (select-keys
       (into {} (map (fn [[k v]] [(keyword k) v])
         (json/parse-string
             (get-in exception-info [:object :body]))))
             (vector :status :message :code))))))

(defn get-request
  "Make a generic GET HTTP request"
  [url signature]
  (try
    (client/get url
      {:accept :json
       :content-type :json
       :headers {"X-Leetchi-Signature" signature}})
  (catch Exception e
     (let [exception-info (.getData e)]
     (select-keys
       (into {} (map (fn [[k v]] [(keyword k) v])
         (json/parse-string
             (get-in exception-info [:object :body]))))
             (vector :status :message :code))))))



(defn create [route input options]
  (let [json (json/generate-string input)
        ts (timestamp)
        url-path (auth/api-call-path (:partner-id options) route ts)
        url (api-call-url (:host options) url-path)
        signature (auth/signature (:rsa-key-path options) "POST" url-path json)
        resp (post-request url signature json)
        output (json/parse-string (:body resp))]
    output))


(defn modify [route id input options]
  (let [json (json/generate-string input)
        ts (timestamp)
        url-path (auth/api-call-path (:partner-id options) route ts id)
        url (api-call-url (:host options) url-path)
        signature (auth/signature (:rsa-key-path options) "PUT" url-path json)
        resp (put-request url signature json)
        output (json/parse-string (:body resp))]
    (println "resp" resp)
    output))

(defn fetch [route id options]
  (let [ts (timestamp)
        url-path (auth/api-call-path (:partner-id options) route ts id)
        url (api-call-url (:host options) url-path)
        signature (auth/signature (:rsa-key-path options) "GET" url-path)
        resp (get-request url signature)
        output (json/parse-string (:body resp))]
    output))

(comment
  (create "users"
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


  (modify "users"
           337243
          {"FirstName" "Markus"
           "Tag" "custom information from the app"}
          {:partner-id "communist"
           :host "http://api-preprod.leetchi.com"
           :rsa-key-path "/Users/podviaznikov/.ssh/mangopay_rsa"})

  (fetch "users" 337243 {:partner-id "communist"
           :host "http://api-preprod.leetchi.com"
           :rsa-key-path "/Users/podviaznikov/.ssh/mangopay_rsa"}))