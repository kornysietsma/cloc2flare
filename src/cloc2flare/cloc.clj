(ns cloc2flare.cloc
  (:require [cloc2flare.flare :as flare]
            [clj-yaml.core :as yaml]
            [cheshire.core :as cheshire]
            [clojure.walk :refer [keywordize-keys]]
            [clojure.pprint])
  (:import (java.io Reader Writer)))

(defn- normalize "strip ./ from unix-style paths"
  ; note - you could do this with Java 1.7 Path/normalize
  ; but I'd prefer to stay friendly for old Java versions - and this is very simple.
  ; TODO: what happens in Windows??? Should be safe, but might be pointless.
  [path]
  (if (clojure.string/starts-with? path "./")
    (subs path 2)
    path))

(defn convert "convert a parsed cloc yaml structure to key-value pairs"
  [input]
  (for [[name data] input
        :when (not (#{"SUM" "header"} name)) ]
    (merge
      {:filename (normalize name)}
      (keywordize-keys data))))

(defn convert-yaml-to-flare "convert input yaml to output flare data"
  [^Reader in-file ^Writer out-file]
  (as-> in-file x
        (slurp x)
        (yaml/parse-string x false)
        (convert x)
        (flare/build-flare x)
        (cheshire/generate-stream x out-file {:pretty true})
        )
  (.flush out-file))
