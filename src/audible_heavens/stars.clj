(ns audible_heavens.stars
  (:gen-class)
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

; Let r be the radius of the sphere.  
; (x,y,z) is inside the sphere if and only if x^2 + y^2 + z^2 < r^2.
(defn radius [x y z]
  (let [coords (vector x y z)]
    (reduce + (map #(* % %) coords))))

(def stars-url "http://star-api.herokuapp.com/api/v1/stars")

(defn get-stars-raw [url]
  (-> url (http/get) :body))

(defn get-stars [url]
  (-> (get-stars-raw url) (json/parse-string true)))
