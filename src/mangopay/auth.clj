(ns mangopay.auth
  (:require [clojure.data.codec.base64 :as b64]
            [clj-http.client :as client]))


(java.security.Security/addProvider
 (org.bouncycastle.jce.provider.BouncyCastleProvider.))

(defn read-keys [f]
  (-> f
      java.io.FileReader.
      org.bouncycastle.openssl.PEMReader.
      .readObject))


(defn sign [data private-key]
  (let [sig (doto (java.security.Signature/getInstance "SHA1withRSA" "BC")
              (.initSign private-key (java.security.SecureRandom.))
              (.update data))]
    (.sign sig)))


(def rsa-keys (read-keys (java.io.File. "/Users/podviaznikov/.ssh/mangopay_rsa")))


(def signuture (sign (.getBytes "Too Many Secrets") (.getPrivate rsa-keys)))



(defn timestamp []
  (.getTime (new java.util.Date)))


(defn api-call-path [partner-id ts]
  (str "/v1/partner/"
       partner-id
       "/users/?ts="
       ts))

(defn api-call-url [partnet-id ts]
  (str "http://api-preprod.leetchi.com" (api-call-path partnet-id ts)))

(defn url [method partner-id ts]
  (str method
       "|"
       (api-call-path partner-id ts)
       "|{user :{Tag :1}}|"))


(defn test-url [ts]
  (url "POST" "communist" ts))



(defn sign-test-url [ts]
  (sign (.getBytes (test-url ts)) (.getPrivate rsa-keys)))


(defn signature [ts]
  (String. (clojure.data.codec.base64/encode (sign-test-url ts)) "UTF-8"))

(signature (timestamp))


(let [ts (timestamp)
      url (api-call-url "communist" ts)
      signature (signature ts)]
  (client/post url
               {:body "{user :{Tag :1}}"
                :headers {
                          "X-Leetchi-Signature" signature}}))








