(defproject docker-client "0.1.5-SNAPSHOT"
  :description "A Docker client written in Clojure. Supports TLS/HTTPS."
  :url "https://github.com/wkf/docker-client-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :test-paths ["src/test"]
  :source-paths ["src/main"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.bouncycastle/bcpkix-jdk15on "1.54"]
                 [clj-http "3.1.0"]
                 [cheshire "5.6.3"]
                 [bidi "2.0.9"]
                 [camel-snake-kebab "0.4.0"]]
  :plugins [[lein-cloverage "1.0.6"]]
  :deploy-repositories [["releases" :clojars]]
  :profiles {:dev {:source-paths ["src/dev"]
                   :dependencies [[clj-http-fake "1.0.2"]]}})
