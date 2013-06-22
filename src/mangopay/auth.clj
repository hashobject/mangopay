(ns mangopay.auth
  (:require [clojure.data.codec.base64 :as b64]
            [clj-http.client :as client]
            [rsa-signer.core :as signer]
            [cheshire.core :as json]))


(defn api-call-path [partner-id ts]
  (str "/v1/partner/"
       partner-id
       "/users/?ts="
       ts))


(defn- string-to-sign
  ([method url-path]
  (str method
       "|"
       url-path
       "|"))
  ([method url-path data]
  (str method
       "|"
       url-path
       "|"
       data
       "|")))

(defn- sign-string
  ([rsa-key-path method url-path]
    (signer/sign
     (string-to-sign method url-path)
     rsa-key-path
     "SHA1withRSA"))
  ([rsa-key-path method url-path data]
    (signer/sign
     (string-to-sign method url-path data)
     rsa-key-path
     "SHA1withRSA")))


(defn signature
  ([rsa-key-path method url-path]
  (String.
   (clojure.data.codec.base64/encode
      (sign-string rsa-key-path method url-path))
   "UTF-8"))
  ([rsa-key-path method url-path data]
  (String.
   (clojure.data.codec.base64/encode
      (sign-string rsa-key-path method url-path data))
   "UTF-8")))







