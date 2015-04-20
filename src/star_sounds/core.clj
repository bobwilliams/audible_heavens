(ns star-sounds.core
  (:require [dynne.async-sound :refer :all]
            [cheshire.core :as json]
            [clj-http.client :as http]))

(def stars-url "http://star-api.herokuapp.com/api/v1/stars")

(defn query-stars [url]
  (-> url (http/get) :body (json/parse-string true)))

(defn sound_star [star]
  (let [name  (get star :label)
        lum   (get star :lum)
        msg   (str "STAR: " name " LUMINOSITY: " lum)]
    (println msg)
    (play (sinusoid 1.0 (* 10 lum)))))

(defn -main [& args]
  (let [star_data (query-stars stars-url)
        num_stars (count star_data)
        stars_msg (str "Iterating through " num_stars " stars....\n")]
    (println stars_msg)
    (doseq [star star_data] (sound_star star))))