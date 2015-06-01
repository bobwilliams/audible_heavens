(ns audible_heavens.data
  (:gen-class)
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

; get max value from vector for given key
(defn max-val [k kv] 
  (reduce max (map k kv)))

; get min value from vector for given key
(defn min-val [k kv]
  (reduce min (map k kv)))

; Let r be the radius of the sphere.  
; (x,y,z) is inside the sphere if and only if x^2 + y^2 + z^2 < r^2.
(defn radius [x y z]
  (let [coords (vector x y z)]
    (reduce + (map #(* % %) coords))))

; (value-min)/(max-min) 
(defn normalize [value max min]
  (/ (- value min) (- max min)))

(def stars-url "http://star-api.herokuapp.com/api/v1/stars")
(def exoplanets-url "http://star-api.herokuapp.com/api/v1/exo_planets")
(def local-groups-url "http://star-api.herokuapp.com/api/v1/local_groups")
(def open-cluster-url "http://star-api.herokuapp.com/api/v1/open_cluster")

(defn get-data-raw [url]
  (-> url (http/get) :body))

(defn get-stars [url]
  (-> (get-data-raw url) (json/parse-string true)))

(defn get-exoplanets [url]
  (-> (get-data-raw url) (json/parse-string true)))

(defn get-local-groups [url]
  (-> (get-data-raw url) (json/parse-string true)))

(defn get-open-clusters [url]
  (-> (get-data-raw url) (json/parse-string true)))