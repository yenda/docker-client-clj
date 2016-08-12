(ns docker-client.core-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [clj-http.core]
            [clj-http.fake :refer :all]
            [docker-client.core :refer :all]))

(def test-http-env
  {:docker-host "tcp://testhost:1234"})

(def test-https-env
  {:docker-host "tcp://testhost:1235"
   :docker-cert-path (-> "certs" io/resource io/file str)
   :docker-tls-verify "1"})

(deftest client-use-environment
  (testing "client http env"
    (with-redefs [env test-http-env]
      (let [c (client)]
        (is (-> c :uri :scheme (= "http")))
        (is (-> c :options :keystore nil?)))))
  (testing "client https env"
    (with-redefs [env test-https-env]
      (let [c (client)]
        (is (-> c :uri :scheme (= "https")))
        (is (-> c :options :keystore boolean))
        (is (-> c :options :trust-store boolean))))))

(deftest client-override-environment
  (testing "client override uri"
    (with-redefs [env test-https-env]
      (is (= "http" (-> {:uri "http://anotherhost:1236"}
                        client
                        :uri
                        :scheme)))))
  (testing "client override options"
    (with-redefs [env test-http-env]
      (is (= "abc123" (-> {:options {:keystore-pass "abc123"}}
                          client
                          :options
                          :keystore-pass))))))

(deftest inspect-container-keyword-keys
  (testing "inspect-container"
    (with-fake-routes
      {"http://testhost:1234/containers/12345/json"
       (fn [_] {:status 200
                :body "{\"Id\": \"12345\"}"})}
      (is (= "12345" (-> {:uri "http://testhost:1234"}
                         client
                         (inspect-container "12345")
                         :id))))))
