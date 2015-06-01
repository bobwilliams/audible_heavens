(ns audible_heavens.views.landing
  (:require [hiccup.core :refer :all]
            [audible_heavens.global :as global]
            [audible_heavens.views :as view]))

(defn index []
  (html
    [:html
      (view/common-head)
      [:body
        (view/nav-bar)
        (view/forkme)
        [:div.container
          (view/breadcrumbs [["home" "/"]])
          (view/jumbotron (str "Welcome to " @global/brand-name "!") "An simple web app that allows you to audibly explore the heavens.")
          [:h4 "This application consumes data provide by the " 
            [:a {:href "http://star-api.herokuapp.com/"} "Star API. "]
            "A big shout out to the folks over at " 
            [:a {:href "https://github.com/HacktheUniverse"} "Hack the Universe"] " for exposing this data."]]
        (view/common-footer)]]))