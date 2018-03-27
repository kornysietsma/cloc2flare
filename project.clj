(defproject cloc2flare "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-yaml "0.4.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [cheshire "5.8.0"]]
  :main cloc2flare.cli
  :uberjar-name "cloc2flare.jar"
  :profiles {
             :uberjar {:aot :all}
             :dev     {:dependencies [[midje "1.9.1"]]}
             }
  :plugins [[lein-midje "3.2"]]
  )
