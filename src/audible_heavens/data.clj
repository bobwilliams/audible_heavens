(ns audible_heavens.data
  (:gen-class)
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

(def stars-url "http://star-api.herokuapp.com/api/v1/stars")
(def exoplanets-url "http://star-api.herokuapp.com/api/v1/exo_planets")
(def galaxies-url "http://star-api.herokuapp.com/api/v1/local_groups")
(def clusters-url "http://star-api.herokuapp.com/api/v1/open_cluster")

(def star-attributes {:distly "Distance" :lum "Luminosity" :colorb_v "Color" :speed "Speed" :absmag "Abs Mag" :appmag "App Mag"})
(def exoplanet-attributes {:distance "Distance" :numplanets "Num Planets" :texture "Texture"})
(def galaxy-attributes {:distly "Distance"})
(def cluster-attributes {:distly "Distance" :diam "Diameter" :logage "Logage" :metal "Metal"})

(defn parse-val [x]
  (if (string? x) (read-string x) x))

; Let r be the radius of the sphere.  
; (x,y,z) is inside the sphere if and only if x^2 + y^2 + z^2 < r^2.
(defn radius [x y z]
  (let [coords (vector x y z)]
    (reduce + (map #(* % %) coords))))

; get max value from vector for given key
(defn max-val [k v] 
  (apply max (map parse-val (map k v))))

; get min value from vector for given key
(defn min-val [k v]
  (apply min (map parse-val (map k v))))

; http://stackoverflow.com/questions/929103/convert-a-number-range-to-another-range-maintaining-ratio
(defn normalize-value [value max min]
  (let [parsed-value (parse-val value)
        parsed-max (parse-val max)
        parsed-min (parse-val min)
        new-max 1000  ;The piano has 88 keys which span the frequency range 27.5 Hz (A0) to 4186 Hz (C8)
        new-min 40 
        old-range (- parsed-max parsed-min)
        new-range (- new-max new-min)]
        (if (= 0 old-range) 0 (+ (/ (* (- parsed-value parsed-min) new-range) old-range) new-min))))

(defn get-data-raw [url]
  (-> url (http/get) :body))

; given a vector of hashmaps find the max and mins for each given galaxy-attribute
(defn get-thresholds [rawdata attribs]
  (reduce #(conj %1 {%2 {:min (min-val %2 rawdata) :max (max-val %2 rawdata)}}) {} (keys attribs)))

(defn get-data [url]
  (-> (get-data-raw url) (json/parse-string true)))