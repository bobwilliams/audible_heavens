(defproject audible_heavens "0.1.0-SNAPSHOT"
  :description "An audible exploration of the heavens"
  :url "http://audibleheavens.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [ [org.clojure/clojure "1.6.0"]
                  [org.craigandera/dynne "0.4.1"]
                  [clj-http "1.1.0"]
                  [compojure "1.3.4"]
                  [http-kit "2.1.18"]
                  [hiccup "1.0.5"]
                  [ring "1.4.0-RC1"]
                  [cheshire "5.4.0"]]
  :main audible_heavens.core)
