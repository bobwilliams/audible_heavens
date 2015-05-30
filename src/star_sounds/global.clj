(ns star-sounds.global)

(def brand-name (atom "Audible Heavens"))
(def server-port (atom 9000))

(defn update-atom [atom value]
  (if value (reset! atom value)))

(defn initialize-atoms [conf]
  (update-atom brand-name (or (:brand-name conf) "Audible Heavens"))
  (update-atom server-port (or (:server-port conf) 9000)))