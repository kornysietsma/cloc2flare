(ns cloc2flare.cli
  (:require
    [cloc2flare.cloc :as cloc]
    [clojure.tools.cli :refer [parse-opts]]
    [clojure.string :as string])
  (:gen-class))

(defn usage []
  (->> ["Utility to convert cloc's yaml output into a flare-format nested json file"
        ""
        "Usage: java -jar cloc2flare.jar < input.yml > output.json"
        ""
        "Generate yaml data with 'cloc . --by-file --yaml --quiet > data.yml'"
        " or pipe directly to this app:"
        "`cloc . --by-file --yaml --quiet | java cloc2flare.jar > sample.json'"]
       (string/join \newline)))

(defn exit [status msg]
  (binding [*out* *err*]
    (println msg))
  (System/exit status))

(defn -main [& args]
  (when-not (empty? args)
    (exit 0 (usage)))
  (cloc/convert-yaml-to-flare *in* *out*))
