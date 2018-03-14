(ns cloc2flare.flare
  (:require [cheshire.core :as cheshire])
  (:import (java.io File Writer Reader)))

(defn- add-to-flare
  "add data to a flare structure when we know the file doesn't already exist"
  [{:keys [children] :as flare} [name & remaining-names] data]
  (let [new-child (if remaining-names
                    (-> {:name name :children []}
                        (add-to-flare remaining-names data))
                    {:name name :data data})]
    (assoc flare :children (conj children new-child))))

(defn- map-merge-overwriting
  [v1 v2]
  (if (and (map? v1) (map? v2))
    (merge-with map-merge-overwriting v1 v2)
    v2))

(defn- merge-flare-data
  "merge new data with an existing flare node"
  [flare data]
  (assoc flare :data (merge-with map-merge-overwriting (:data flare) data)))

(defn- update-in-flare
  "update data in a nested flare structure using strategy of :merge"
  [flare [name & remaining-names :as all-names] data]
  (let [{matches true mismatches false} (group-by #(= name (:name %)) (:children flare))
        mismatches (or mismatches [])]                      ; as nil mismatches causes strangeness
    (if-not matches
      (add-to-flare flare all-names data)
      (let [match (first matches)]
        (assert (empty (rest matches)) (str "multiple children with name " name))
        (if remaining-names
          (assoc flare :children (conj mismatches (update-in-flare match remaining-names data)))
          (assoc flare :children (conj mismatches (merge-flare-data match data))))))))

(def filesep (re-pattern File/separator))

(defn- combinefn [flare {:keys [filename] :as file-data}]
  (let [pathbits (clojure.string/split filename filesep)
        data (dissoc file-data :filename)]
    (update-in-flare flare pathbits data)))

(defn combine "Combine file data into a Flare structure"
  [flare files]
  (reduce combinefn flare files))

(def empty-flare {:name "flare" :children []})

(defn build-flare [cloc-data]
  (combine empty-flare cloc-data))
