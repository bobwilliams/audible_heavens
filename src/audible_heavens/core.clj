(ns audible_heavens.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [org.httpkit.server :refer :all]
            [ring.util.response :refer :all]
            [compojure.core :refer [defroutes GET POST]]
            [ring.middleware.reload :as reload]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [cheshire.core :as json]
            [audible_heavens.global :as global]
            [audible_heavens.views.landing :as landing]
            [audible_heavens.views.rawdata :as rawdata]
            [audible_heavens.views.dashboard :as dashboard]
            [audible_heavens.data :as data]))

(def resource-conf (-> "config.json" io/resource))

(defn read-conf [file]
  (json/parse-string (slurp (or file resource-conf)) true))

(defroutes routes
  (GET "/alo" [] "alo guvna")
  (GET "/" [] (landing/index))
  (GET "/rawdata" [] 
    (let [stars (data/get-data data/stars-url)
          s_thresholds (data/get-thresholds stars data/star-attributes)
          exoplanets (data/get-data data/exoplanets-url)
          e_thresholds (data/get-thresholds exoplanets data/exoplanet-attributes)
          galaxies (data/get-data data/galaxies-url)
          g_thresholds (data/get-thresholds galaxies data/galaxy-attributes)
          clusters (data/get-data data/clusters-url)
          c_thresholds (data/get-thresholds clusters data/cluster-attributes)]
      (rawdata/index 
        {:stars {:data stars :thresholds s_thresholds :attributes data/star-attributes}
         :exoplanets {:data exoplanets :thresholds e_thresholds :attributes data/exoplanet-attributes}
         :galaxies {:data galaxies :thresholds g_thresholds :attributes data/galaxy-attributes}
         :clusters {:data clusters :thresholds c_thresholds :attributes data/cluster-attributes}})))
  (GET "/rawstars" [] (data/get-data-raw data/stars-url))
  (GET "/dashboard" [] (dashboard/index))
  (route/resources "/static/"))

(defn app-routes [{mode :mode}]
  (if (= mode "prod")
    (handler/site routes)
    (-> #'routes handler/site reload/wrap-reload)))

(defn -main [& [conf-file]]
  (let [conf (read-conf conf-file)
        app (app-routes conf)]
    (global/initialize-atoms conf)
    (run-server app {:port @global/server-port :join? false})))
