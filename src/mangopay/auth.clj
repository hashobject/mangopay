(ns mangopay.auth
  (:require [clojure.data.codec.base64 :as b64]))


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

(defn url [method partner-id timestamp]
  (str method
       "|/v1/partner/"
       partner-id
       "/users/?ts="
       timestamp
       "|{user :{Tag :1}}|"))


(defn test-url []
  (url "POST" "communist" (timestamp)))



(defn sign-test-url []
  (sign (.getBytes (test-url)) (.getPrivate rsa-keys)))


(String. (clojure.data.codec.base64/encode (sign-test-url)) "UTF-8")




