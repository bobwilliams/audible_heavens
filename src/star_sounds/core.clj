(ns star-sounds.core
  (:gen-class)
  (:require [dynne.async-sound :refer :all]
            [cheshire.core :as json]
            [clj-http.client :as http]))

; Let r be the radius of the sphere.  
; (x,y,z) is inside the sphere if and only if x^2 + y^2 + z^2 < r^2.
(defn radius [x y z]
  (let [coords (vector x y z)]
    (reduce + (map #(* % %) coords))))

; getting the data
(def stars-url "http://star-api.herokuapp.com/api/v1/stars")

(defn query-stars [url]
  (-> url (http/get) :body (json/parse-string true)))

(defn get_stars [raw_data]
  (sort-by :rad 
    (for [star raw_data
        :let [rad (radius (get star :x) (get star :y) (get star :z))
              lum (get star :lum)
              label (get star :label)]]
        (hash-map :label label :lum lum :rad rad))))

; "visualizing" the star via sound
(defn sound_star [star]
  (let [name  (get star :label)
        lum   (get star :lum)
        rad   (get star :rad)
        msg   (str "STAR: " name " DISTANCE: " rad " LUMINOSITY: " lum)]
    (println msg)
    (play (sinusoid 1.0 (* 1000 lum)))
    (Thread/sleep 1000))) ;; hack for now...need to find a synchronous audio library 

; start up
(defn -main [& args]
  (let [raw_data (query-stars stars-url)
        star_data (get_stars raw_data)
        num_stars (count star_data)
        stars_msg (str "Iterating through " num_stars " stars....\n")]
    (println stars_msg)
    (doseq [star star_data] (sound_star star))))