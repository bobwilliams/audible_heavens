(ns star-sounds.global)

(def brand-name (atom "Star Sounds"))
(def server-port (atom 9000))

(defn update-atom [atom value]
  (if value (reset! atom value)))

(defn initialize-atoms [conf]
  (update-atom brand-name (or (:brand-name conf) "AnyViz"))
  (update-atom server-port (or (:server-port conf) 9000)))