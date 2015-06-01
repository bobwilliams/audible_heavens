(ns audible_heavens.views.landing
  (:require [hiccup.core :refer :all]
            [audible_heavens.global :as g]
            [audible_heavens.views :as v]))

(defn welcome-view []
  (html
    [:html
      (v/common-head)
      [:body
        (v/nav-bar)
        (v/forkme)
        [:div.container
          (v/breadcrumbs [["home" "/"]])
          (v/jumbotron (str "Welcome to " @g/brand-name "!") "An simple web app that allows you to audibly explore the heavens.")
          [:h4 "This application consumes data provide by the " 
            [:a {:href "http://star-api.herokuapp.com/"} "Star API. "]
            "A big shout out to the folks over at " 
            [:a {:href "https://github.com/HacktheUniverse"} "Hack the Universe"] " for exposing this data."]]
        (v/common-footer)]]))